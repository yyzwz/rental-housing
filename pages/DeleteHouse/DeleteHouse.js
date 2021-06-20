var util = require('../../utils/util.js');
const app = getApp();
Page({
  data: {
    houseID: '',
    msg:'',
    loginID :'',
  },
  onLoad: function (options) {
    app.data.houseID = options.houseID;
    this.deleteHouse();
  },
  returnMain:function(){
    var that = this;
    wx.navigateBack({
      delta:2
    })
  },
  deleteHouse: function () {
    wx.showLoading({});
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/deleteHouse?houseID=' + app.data.houseID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data[0] == "existRooms") {
          wx.showToast({
            title: '有下属房屋',
          })
          that.setData({
            msg: '请先删除该房屋的下属房间再试，谢谢！',
          })
        }
        else if (res.data[0] == "ok"){
          wx.showToast({
            title: '删除成功',
          })
          that.setData({
            msg: '房屋信息删除成功',
          })
        }
      },
      complete:function(){
        wx.hideLoading();
      }
    })
  },
})