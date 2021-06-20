var util = require('../../utils/util.js');
const app = getApp();
Page({
  data:{
    isNullMsg: '',
    roomId:'',
    tenantList:[],
    loginID :'',
    roomAndTenantList:[],
  },
  onLoad: function (options) {
    app.data.roomID = options.roomId;
    this.getData();
  },
  getData :function(){
    var that = this;
    wx.request({
      url: app.data.appUrl + '/tenant/getTenantByRoom?roomID=' + app.data.roomID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        if (res.data.length == 0) {
          console.log("为空");
          that.setData({
            isNullMsg: '1'
          })
        }
        that.setData({
          tenantList: res.data
        });
      }
    })
    wx.request({
      url: app.data.appUrl + '/tenant/getRATByRoom?roomID=' + app.data.roomID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        that.setData({
          roomAndTenantList: res.data
        });
      }
    })
  },
})