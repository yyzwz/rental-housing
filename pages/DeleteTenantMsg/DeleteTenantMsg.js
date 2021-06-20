var util = require('../../utils/util.js');
const app = getApp();
Page({
  data: {
    roomId: '',
    tenantId: '',
    loginID:'',
  },
  onLoad: function (options) {
    this.setData({
      roomId: app.data.roomId,
      tenantId: options.tenantId,
    })
    this.deleteTenant();
  },
  returnMian:function(){
    wx.navigateBack({
      delta: 4
    })
  },
  deleteTenant: function () {
    wx.showLoading({});
    var that = this;
    wx.request({
      url: app.data.appUrl + '/tenant/deleteTenant?roomID=' + app.data.roomID + '&tenantID=' + that.data.tenantId,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        wx.showModal({
          title: '是否留在此页面',
          content: '操作成功，如想留在此界面，请点击确定；如需要返回主界面，请点击取消',
          success: function (res) {
            if (res.confirm) {
            } else if (res.cancel) {
              wx.navigateBack({
                delta:4
              })
            }
          }
        })
      },
      complete:function(){
        wx.hideLoading();
      }
    })
  },
})