// 郑为中
// 我的房屋 页面JS文件
const app = getApp();
var util = require('../../utils/util.js');


Page({
  data:{
    isNullMsg: '',
    houseID:'',
    roomList:[],
    loginID :''
  },

  // 获取其他页面传过来的loginID值
  onLoad: function (options) {
    this.setData({
      houseID: options.id,
    })
    console.log("租客房间选择界面获取到的房屋ID = " + this.data.houseID);
    this.getData();
  },

  // 从指定url中过去数据  赋值给str
  getData :function(){
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/getRoomListByHouseId?HouseID=' + that.data.houseID,
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
          roomList: res.data
        });
      }
    })
    
  },
})