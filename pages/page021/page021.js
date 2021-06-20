const app = getApp();
Page({

  data: {
    houseID: '',

  },

  onLoad: function (options) {
    console.log("page021界面获取到的houseID = " + options.houseID);
    this.setData({
      houseID:options.houseID,
    })
  },

  upload_info:function(){
    var that = this;
    wx.request({
      // 提交给这个网址，并传相关参数
      url: app.data.appUrl + '/house/updeteRoom',
      data: {
        roomId: that.data.roomId,
        roomName: that.data.roomName,
        roomArea: that.data.roomArea,
        roomDesc: that.data.roomDesc,
      },
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        var loginID = res.data[0];
        console.log(res);
        wx.showToast({
          title: '提交成功',
          duration: 2000
        })
        wx.navigateTo({
          url: '../../pages/page03/page03?loginID=' + res.data[0],
        })
      },
      fail: function (res) {
        console.log("--------登入fail--------");
      }
    })
  },

  roomId: function (e) {
    this.setData({
      roomId: e.detail.value
    })
  },
  roomName: function (e) {
    this.setData({
      roomName: e.detail.value
    })
  },
  roomArea: function (e) {
    this.setData({
      roomArea: e.detail.value
    })
  },
  roomDesc: function (e) {
    this.setData({
      roomDesc: e.detail.value
    })
  },

})