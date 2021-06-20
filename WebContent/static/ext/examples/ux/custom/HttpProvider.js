/**
 * 实现用数据库保存组件状态
 *
*/
// vim: ts=4:sw=4:nu:fdc=2:nospell
/*global Ext, console */
/**
* @class Ext.ux.custom.HttpProvider
* @extends Ext.state.Provider
*
* Buffering state provider that sends and receives state information to/from server
*
* @author    Ing. Jozef Sakáloš
* @copyright (c) 2008, Ing. Jozef Sakáloš
* @version   1.2
* @revision  $Id: Ext.ux.custom.HttpProvider.js 728 2009-06-16 16:31:16Z jozo $
*
* @license Ext.ux.custom.HttpProvider is licensed under the terms of
* the Open Source LGPL 3.0 license.  Commercial use is permitted to the extent
* that the code/component(s) do NOT become part of another Open Source or Commercially
* licensed development library or toolkit without explicit permission.
* 
* <p>License details: <a href="http://www.gnu.org/licenses/lgpl.html"
* target="_blank">http://www.gnu.org/licenses/lgpl.html</a></p>
*
* @forum     24970
* @demo      http://cellactions.extjs.eu
*
* @donate
* <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_blank">
* <input type="hidden" name="cmd" value="_s-xclick">
* <input type="hidden" name="hosted_button_id" value="3430419">
* <input type="image" src="https://www.paypal.com/en_US/i/btn/x-click-butcc-donate.gif" 
* border="0" name="submit" alt="PayPal - The safer, easier way to pay online.">
* <img alt="" border="0" src="https://www.paypal.com/en_US/i/scr/pixel.gif" width="1" height="1">
* </form>
*/
Ext.define('Ext.ux.custom.HttpProvider', {
    extend: 'Ext.state.Provider'

    /**
    * @cfg {Boolean} async
    * Force the request to be syncronous by setting to false.
    * Default is true.
    */
    , async: true
    /**
    * @cfg {Boolean} flushCache 
    * Indicates if the state data should be flushed before loading new records.
    * False indicates that new records returned from a read request will be appended to the cache.
    * Defaults to false.
    */
    , flushCache: false
    // localizable texts
    , saveSuccessText: 'Save Success'
    , saveFailureText: 'Save Failure'
    , readSuccessText: 'Read Success'
    , readFailureText: 'Read Failure'
    , dataErrorText: 'Data Error'

    //private
    , constructor: function (config) {
        this.addEvents(
        /**
        * @event readsuccess
        * Fires after state has been successfully received from server and restored
        * @param {HttpProvider} this
        */
             'readsuccess'
        /**
        * @event readfailure
        * Fires in the case of an error when attempting to read state from server
        * @param {HttpProvider} this
        */
            , 'readfailure'
        /**
        * @event savesuccess
        * Fires after the state has been successfully saved to server
        * @param {HttpProvider} this
        */
            , 'savesuccess'
        /**
        * @event savefailure
        * Fires in the case of an error when attempting to save state to the server
        * @param {HttpProvider} this
        */
            , 'savefailure'
        );

        this.callParent(arguments);

        Ext.apply(this, config, {
            // defaults
            delay: 750 // buffer changes for 750 ms
            , dirty: false
            , started: false
            , autoStart: true
            , autoRead: true
            , user: 'user'
            , id: 1
            , session: 'session'
            , logFailure: false
            , logSuccess: false
            , queue: []
            , url: appBaseUri+'UserPortal'
            , readUrl: appBaseUri+'UserPortal/GetAll'
            , saveUrl: appBaseUri+'UserPortal/Save'
            , method: 'POST'
            , saveBaseParams: {}
            , readBaseParams: {}
            , paramNames: {
                id: 'id'
                , name: 'PageCode'
                , value: 'PortalJson'
                , user: 'user'
                , session: 'session'
                , data: 'data'
            }
        }); // eo apply

        if (this.autoRead) {
            this.readState();
        }

        this.dt = Ext.create('Ext.util.DelayedTask', this.submitState, this);
        if (this.autoStart) {
            this.start();
        }
    }
    /**
    * Sets the passed state variable name to the passed value and queues the change
    * @param {String} name Name of the state variable
    * @param {Mixed} value Value of the state variable
    */
    , set: function (name, value) {
        if (!name) { return; }


        this.queueChange(name, value);
    }
    /**
    * Starts submitting state changes to server
    */
    , start: function () {
        this.dt.delay(this.delay);
        this.started = true;
    }
    /**
    * Stops submitting state changes
    */
    , stop: function () {
        this.dt.cancel();
        this.started = false;
    }
    /**
    * private, queues the state change if the value has changed
    */
    , queueChange: function (name, value) {
        var o = {}
            , i = 0
            , found = false
        // see http://extjs.com/forum/showthread.php?p=344233
            , oldValue = this.state[name]
            , newValue
            , changed;

        for (; i < this.queue.length; i++) {
            if (this.queue[i].name === name) {
                oldValue = this.decodeValue(this.queue[i].value);
            }
        }
        //changed = undefined === oldValue || oldValue !== value;
        //http://www.sencha.com/forum/showthread.php?24970-Buffering-Http-State-Provider&p=581091&viewfull=1#post581091
        changed = undefined === oldValue || this.encodeValue(oldValue) !== this.encodeValue(value);

        if (changed) {
            newValue = this.encodeValue(value);
            o[this.paramNames.name] = name;
            o[this.paramNames.value] = newValue;
            for (i = 0; i < this.queue.length; i++) {
                if (this.queue[i].name === o.name) {
                    this.queue[i] = o;
                    found = true;
                }
            }
            if (false === found) {
                this.queue.push(o);
            }
            this.dirty = true;
        }
        if (this.started) {
            this.start();
        }
        return changed;
    }
    /**
    * private, submits state to server by asynchronous Ajax request
    */
    , submitState: function () {
        if (!this.dirty || Ext.isEmpty(this.queue)) {
            this.dt.delay(this.delay);
            return;
        }
        this.dt.cancel();

        var o = {
            url: this.saveUrl || this.url
            , method: this.method
            , scope: this
            , success: this.onSaveSuccess
            , failure: this.onSaveFailure
            //,queue:Ext.ux.util.clone(this.queue)
            , queueCopy: Ext.Array.clone(this.queue) //don't use 'queue', conflicts with ext-basex QueueManager 
            , params: {}
        };

        var params = Ext.apply({}, this.saveBaseParams);
        params[this.paramNames.id] = this.id;
        params[this.paramNames.user] = this.user;
        params[this.paramNames.session] = this.session;
        params[this.paramNames.data] = Ext.encode(o.queueCopy);

        Ext.apply(o.params, params);

        // be optimistic
        this.dirty = false;

        Ext.Ajax.request(o);
    }
    /**
    * Clears the state variable
    * @param {String} name Name of the variable to clear
    */
    , clear: function (name) {
        this.set(name, undefined);
    }
    /**
    * private, save success callback
    */
    , onSaveSuccess: function (response, options) {
        var o = {};
        try { o = Ext.decode(response.responseText); }
        catch (e) {
            if (true === this.logFailure) {
                this.log(this.saveFailureText, e, response);
            }
            this.dirty = true;
            return;
        }
        if (!o) return;
        if (true !== o.success) {
            if (true === this.logFailure) {
                this.log(this.saveFailureText, o, response);
            }
            this.dirty = true;
        }
        else {
            Ext.each(options.queueCopy, function (item) {
                if (!item) {
                    return;
                }
                var name = item[this.paramNames.name];
                var value = this.decodeValue(item[this.paramNames.value]);

                if (undefined === value || null === value) {
                    Ext.ux.custom.HttpProvider.superclass.clear.call(this, name);
                }
                else {
                    // parent sets value and fires event
                    Ext.ux.custom.HttpProvider.superclass.set.call(this, name, value);
                }
            }, this);
            if (false === this.dirty) {
                this.queue = [];
            }
            else {
                var i, j, found;
                for (i = 0; i < options.queueCopy.length; i++) {
                    found = false;
                    for (j = 0; j < this.queue.length; j++) {
                        if (options.queueCopy[i].name === this.queue[j].name) {
                            found = true;
                            break;
                        }
                    }
                    if (true === found && this.encodeValue(options.queueCopy[i].value) === this.encodeValue(this.queue[j].value)) {
                        Ext.Array.remove(this.queue, this.queue[j]);
                    }
                }
            }
            if (true === this.logSuccess) {
                this.log(this.saveSuccessText, o, response);
            }
            this.fireEvent('savesuccess', this);
        }
    }
    /**
    * private, save failure callback
    */
    , onSaveFailure: function (response, options) {
        if (true === this.logFailure) {
            this.log(this.saveFailureText, response);
        }
        this.dirty = true;
        this.fireEvent('savefailure', this);
    }
    /**
    * private, read state callback
    */
    , onReadFailure: function (response, options) {
        if (true === this.logFailure) {
            this.log(this.readFailureText, response);
        }
        this.fireEvent('readfailure', this);

    }
    /**
    * private, read success callback
    */
    , onReadSuccess: function (response, options) {
        var data = {};
        try { data = Ext.decode(response.responseText); }
        catch (e) {
            if (true === this.logFailure) {
                this.log(this.readFailureText, e, response);
            }
            return;
        }

        //flush cache if not appending
        if (this.flushCache) {
            this.state = {};
        }
        Ext.each(data, function (item) {
            this.state[item[this.paramNames.name]] = this.decodeValue(item[this.paramNames.value]);
        }, this);
        this.queue = [];
        this.dirty = false;
        if (true === this.logSuccess) {
            this.log(this.readSuccessText, data, response);
        }
        this.fireEvent('readsuccess', this);
    }
    /**
    * Reads saved state from server by sending asynchronous Ajax request and processing the response
    */
    , readState: function () {
        var o = {
            url: this.readUrl || this.url
            , method: this.method
            , scope: this
            , success: this.onReadSuccess
            , failure: this.onReadFailure
            , params: {}
            , async: this.async
        };

        var params = Ext.apply({}, this.readBaseParams);
        params[this.paramNames.id] = this.id;
        params[this.paramNames.user] = this.user;
        params[this.paramNames.session] = this.session;

        Ext.apply(o.params, params);
        Ext.Ajax.request(o);
    }
    /**
    * private, logs errors or successes
    */
    , log: function () {
        if (console) {
            console.log.apply(console, arguments);
        }
    }

});
