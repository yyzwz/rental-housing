var util = require('../../utils/util.js');
const app = getApp();
Page({
  data:{
    isNullMsg: '',
    houseList:[],
    loginID :'',
    checkMsg:'',
    depardname:'',
  },
  onLoad: function (options) {
    wx.showLoading({});
    var that = this;
    console.log(options.sys);
    console.log(options.depardname);
    if(options.sys!= null){
      that.setData({
        depardname : options.depardname
      })
      console.log("赋值后的depardname = " + that.data.depardname);
      that.getAdminData();
    }
    else{
      that.data.loginID = app.data.loginCode;
      that.getData();
      console.log(that.data.str);
    }
    wx.hideLoading();
  },
  getAdminData: function(){ // admin
    var that = this;
    console.log("管理员所在的村 = " + that.data.depardname);
    wx.request({
      url: app.data.appUrl + '/house/adminHouseList?departName=' + that.data.depardname,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        that.setData({
          houseList: res.data
        });
        console.log("length = " + that.data.houseList.length);
      }
    }) 
  },
  getData :function(){
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/myHouseList?houseOwnerId=' + app.data.loginID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        that.setData({
          houseList: res.data
        });
        console.log("length = " + that.data.houseList.length);
      }
    }) 
  },
})