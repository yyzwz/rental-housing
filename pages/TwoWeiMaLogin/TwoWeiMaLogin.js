/**
 * 郑为中
 * 
 * 二维码登入界面的JS文件
 */
const app = getApp();
var WXBizDataCrypt = require('../../utils/RdWXBizDataCrypt.js');
var AppId = 'wx0f27b4c920436956'
var AppSecret = '48913edcb1b58f214765eb5581ecacf1'
Page({

  data: {
    houseID:'',
    tel:'',

    code: '',
    sessionkey: '',
    openid: '',
    a:'',
  },
 
  onLoad: function (q) {
    var that = this;
    console.log("q == ");
    console.log(q);
    // 传递的参数需要转码
    that.setData({
      a:decodeURIComponent(q.q)
    })
    var num;
    num = that.data.a.split('=');


    console.log(num);
    this.setData({
      houseID: num[1],
      // houseID: 13,
    })
    this.getLogin();
  },
  getPhoneNumber(e) {
    var that = this;
    console.log(e.detail.errMsg)
    console.log(e.detail.iv)
    console.log(e.detail.encryptedData)
    var pc = new WXBizDataCrypt(AppId, this.data.sessionkey)
    wx.getUserInfo({
      success: function (res) {
        var data = pc.decryptData(e.detail.encryptedData, e.detail.iv)
        console.log('解密后 data: ', data)
        console.log('手机号码: ', data.phoneNumber)
        that.setData({
          tel: data.phoneNumber,
        })
        that.upload_info();
      }
    })
  },

  getLogin: function () {
    var that = this;
    wx.login({
      success: function (res) {
        console.log(res);
        that.setData({
          code: res.code,
        })
        wx.request({
          url: 'https://changjienongye.cn/docs/getOpenId.jsp?code=' + that.data.code,
          // url: 'https://api.weixin.qq.com/sns/jscode2session?appid=wx0f27b4c920436956&secret=48913edcb1b58f214765eb5581ecacf1&js_code=' + that.data.code + '&grant_type=authorization_code',
          method: 'POST',
          header: {
            'content-type': 'application/json'
          },
          success: function (res) {
            console.log(res);
            that.setData({
              sessionkey: res.data.session_key,
              openid: res.data.openid,
            })
          }
        })
      }
    })
  },
  //  登入 事件 
  upload_info: function () {
    var that = this;
      wx.request({
        url: app.data.appUrl + '/houseWoner/twoWeiMaLogin',
        data: {
          houseID: that.data.houseID,
          tel:that.data.tel,
        },
        method: 'GET',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          console.log(res);
          if(res.data == true){
            wx.navigateTo({
              url: '../../pages/MyRooms/MyRooms?houseId=' + that.data.houseID,
            })
          }
          else if(res.data == false){
            wx.showToast({
              title: '无权查看',
              duration: 2000,
            })
          }
          else{
            wx.showToast({
              title: '未知错误',
              duration: 2000,
            })
          }
        
            
          
        },
        fail: function (res) {
          console.log("--------登入账号提交失败 fail--------");
        }
      })
  },
})