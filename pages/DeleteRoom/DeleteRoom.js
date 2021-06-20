var util = require('../../utils/util.js');
const app = getApp();
Page({
  data: {
    roomid: '',
    msg:'',
    loginid:'',
  },
  onLoad: function (options) {
    this.setData({
      roomid: options.roomid,
    })
    this.deleteRoom();
  },
  returnMain:function(){
    wx.navigateBack({
      delta:3
    })
  },
  deleteRoom: function () {
    wx.showLoading({});
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/deleteRoom?roomid=' + that.data.roomid,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      data: {

      },
      success: function (res) {
        if (res.data[0] == "cannot") {
          that.setData({
            msg: "删除失败，请先删除关联租客"
          })
          wx.showModal({
            title: '温馨提示',
            content: '删除房间失败，请先删除该房间的租客。是否返回到主页？',
            success: function (res) {
              if (res.confirm) {
                wx.navigateBack({
                  delta :3
                })
              } else if (res.cancel) {
              }
            }
          })
        }
        else{
          that.setData({
            msg: "该房间信息已经删除！"
          })
          wx.showModal({
            title: '是否留在此界面？',
            content: '删除房间信息成功，您是否想留在此界面?',
            success: function (res) {
              if (res.confirm) {
              } 
              else if (res.cancel) {
                wx.navigateBack({
                  delta: 3
                })
              }
            }
          })
        }
      },
      complete:function(){
        wx.hideLoading();
      }
    })
  },
})