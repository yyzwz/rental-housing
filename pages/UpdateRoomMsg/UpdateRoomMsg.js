//houseID
const app = getApp();
Page({
  data: {
    roomid:'',
    name:'',
    area:'',
    about:'',
  },

  onLoad: function (options) {
    wx.showLoading({});
    console.log(options.roomid);
    this.setData({
      roomid: options.roomid
    })
    this.getData();
    wx.hideLoading();
  },
  getData:function(){
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/getRoomMsg',
      method: 'GET',
      data: {
        roomid: that.data.roomid,
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded" // 默认值
      },
      success(res) {
       console.log(res);
       that.setData({
         name: res.data[0].roomName,
         area: res.data[0].roomArea,
         about: res.data[0].roomDesc,
       })
      }
    })
  },
  upload_info: function () {
    wx.showLoading({});
    var that = this;
      wx.request({
        url: app.data.appUrl + '/house/updeteRoom',
        method: 'GET',
        data: {
          roomid:that.data.roomid,
          name:that.data.name,
          area:that.data.area,
          about:that.data.about,
        },
        header: {
          "Content-Type": "application/x-www-form-urlencoded" // 默认值
        },
        success(res) {
          wx.showModal({
            title: '是否继续修改房间信息？',
            content: '修改房间信息成功，如想再次修改，请点击确定；如需要返回主界面，请点击取消',
            success: function (res) {
              if (res.confirm) {
              } else if (res.cancel) {
                wx.navigateBack({
                  delta : 4
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

  name: function(e) {
    this.setData({
      name: e.detail.value
    })
  },
  area: function (e) {
    this.setData({
      area: e.detail.value
    })
  },
  about: function (e) {
    this.setData({
      about: e.detail.value
    })
  },
  
})