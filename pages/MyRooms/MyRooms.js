var util = require('../../utils/util.js');
const app = getApp();
Page({
  data:{
    haveRoomImg:'0',
    haveTwoWeiMa:'0',
    haveHouseImg: '0',
    isNullMsg: '',
    houseID:'',
    roomList:[],
    loginID :'',
    token: '',
    twoWeiMaImage: '',
    pagehouseid:'',
    //房东数据
    houseownername:'',
    houseownertel:'',
    houseowneridcard:'',
    houseowneraddress:'',
    // 定位信息
    jingdu:'29.253901',
    weidu:'121.719823'
  },
  onLoad: function (options) {
    wx.showLoading({});
    app.data.houseID = options.houseId;
    this.data.houseID = app.data.houseID;
    this.getData();
    //this.getTo();
    wx.hideLoading();
  },
  getData :function(){
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/getRoomListByHouseId?HouseID=' + app.data.houseID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        if (res.data.length == 0) {
          console.log("为空");
          that.setData({
            isNullMsg: '1',
          })
        }
        that.setData({
          roomList: res.data,
          pagehouseid : app.data.houseID,
        });
        
        console.log("app.data.houseID = " + app.data.houseID);
      }
    })
    wx.request({
      url: app.data.appUrl + '/houseWoner/findByHouseId?houseid=' + app.data.houseID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        that.setData({
          houseownername:res.data.houseOwnerName,
          houseownertel:res.data.houseOwnerTel,
          houseowneridcard:res.data.houseOwnerIdentify,
          houseowneraddress:res.data.houseOwnerAddress,
        });
        
        console.log("app.data.houseID = " + app.data.houseID);
      }
    })
    wx.request({
      url: app.data.appUrl + '/house/fingHouseDingWei?houseid=' + app.data.houseID,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        that.setData({
          jingdu:res.data[0],
          weidu:res.data[1]
        });
        console.log("that.data.jingdu = " + that.data.jingdu);
      }
    })
  },
  getTo: function () {
    var that = this;
    wx.request({
      url: 'https://www.changjienongye.cn/docs/getToken.jsp',
      //url: 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx0f27b4c920436956&secret=48913edcb1b58f214765eb5581ecacf1',
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          token: res.data.access_token
        })
        console.log("token = " + that.data.token)
      },
    })
  },
  // 查看房屋照片
  getHouseImg: function() {
    var that = this;
    that.setData({
      haveHouseImg:'1',
    })
  },
  //查看房间照片
  getRoomImg: function() {
    var that = this;
    that.setData({
      haveRoomImg:'1',
    })
  },
  getTwoMa: function () {
    var that = this;
    console.log("获取到的 token = " + that.data.token)
    console.log("housid = " + that.data.pagehouseid);
    that.setData({
      haveTwoWeiMa:'1',
    })
    // wx.request({
    //   url: 'https://www.changjienongye.cn/docs/getImg.jsp?access_token='+ that.data.token,
    //   //url: 'https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=' + that.data.token,
    //   data: {
    //     scene: app.data.houseID,
    //     page: "pages/TwoWeiMaLogin/TwoWeiMaLogin" 
    //   },
    //   header: {
    //     'content-type': 'application/json'
    //   },
    //   method: "POST",
    //   responseType: 'arraybuffer',
    //   success(res) {
    //     console.log(res)
    //     var image = wx.arrayBufferToBase64(res.data);
    //     that.setData({
    //       haveTwoWeiMa:'1',
    //       twoWeiMaImage: image
    //     })
    //   },
    //   fail(e) {
    //     console.log(e)
    //   }
    // })
  },
    // 地图定位功能
    mapView: function () {
      var that = this
      wx.chooseLocation({
        success: function (res) {
          console.log(res);
          that.data.userAddress = res.address + res.name + ',latitude=' + res.latitude
            + ",longitude=" + res.longitude;
          console.log("您选择的定位地址 = " + that.data.userAddress);
          wx.request({
            url: 'https://changjienongye.cn/forestry/house/updeteDingWei?houseID=' + that.data.houseID + '&dingwei=' + that.data.userAddress,
            method: 'GET',
            header: {
              'content-type': 'application/json'
            },
            success: function (res) {
              that.setData({
                token: res.data.access_token
              })
              console.log("token = " + that.data.token)
            },
          })
        },
      })
    }
})