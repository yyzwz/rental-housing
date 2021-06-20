var util = require('../../utils/util.js');
const app = getApp();
Page({
  data: {
    checkHouseList: [],
    houseID: '',
  },
  onLoad: function (options) {
    this.setData({
      houseID: options.houseID,
    })
    this.checkPass();
  },
  checkPass: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/houseWoner/checkHousesPass?houseID=' + that.data.houseID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        wx.showToast({
          title: '已通过',
        })
        wx.navigateBack({
          delta:2
        })
      }
    })
  },
})