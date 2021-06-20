var util = require('../../utils/util.js');
const app = getApp();
Page({
  data: {
    checkTenantsList: [],
    isNullMsg: '',
  },
  onLoad: function (options) {
    this.getData();
  },
  getData: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/tenant/checkTenants?departname=' + app.data.departname,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        if (res.data.length == 0) {
          that.setData({
            isNullMsg: 1,
          })
        }
        that.setData({
          checkTenantsList: res.data
        });
      }
    })
  },
})