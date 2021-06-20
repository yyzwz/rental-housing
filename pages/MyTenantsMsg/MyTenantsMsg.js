// 郑为中
// 我的房屋 页面JS文件

var util = require('../../utils/util.js');
const app = getApp();

Page({
  data:{
    roomId:'',
    tenantList:[],
    loginID :'',
    roomAndTenantList:[],
    isNullMsg:'',
  },

  // 获取其他页面传过来的loginID值
  onLoad: function (options) {
    this.setData({
      roomId: options.roomId,
    })
    this.getData();
  },


  getData :function(){
    var that = this;
    wx.request({
      url: app.data.appUrl + '/tenant/getTenantByRoomNotHistory?roomID=' + that.data.roomId,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      data: {

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
      url: app.data.appUrl + '/tenant/getRATByRoomNotHistory?roomID=' + that.data.roomId,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      data: {

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