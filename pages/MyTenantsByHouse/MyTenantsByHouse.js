var util = require('../../utils/util.js');
const app = getApp();
Page({
  data: {
    houseList: [],
  },
  onLoad: function (options) {
    this.getData();
  },
  getData: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/houseList?loginID=' + app.data.loginID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        that.setData({
          houseList: res.data
        });
      }
    })
  },
})