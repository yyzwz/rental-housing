// 郑为中
// 我的房屋 页面JS文件

var util = require('../../utils/util.js');
const app = getApp();

Page({
  data: {
    checkHouseList: [],
    ratID: '',
    departname:'',
  },

  onLoad: function (options) {
    this.setData({
      ratID: options.ratID,
      departname: app.data.departname,
    })
    this.checkPass();

  },
  checkPass: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/houseWoner/checkTenantNotPass?ratID=' + that.data.ratID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      data: {

      },
      success: function (res) {
        console.log(res.data);
        wx.showToast({
          title: '已不通过',
          duration:500,
        })
        wx.navigateBack({
          delta: 2
        })
      }
    })
  },
})