/**
 * 郑为中
 * 
 * 登入界面的JS文件
 */
var WXBizDataCrypt = require('../../utils/RdWXBizDataCrypt.js');
var AppId = 'wx0f27b4c920436956'
var AppSecret = '48913edcb1b58f214765eb5581ecacf1'
const app = getApp();
Page({

  data: {
    // 一键登入模块
    tel: '',
    code: '',
    sessionkey: '',
    openid: '',

    loginID :'',
    loginNAME:'',
    name: '', //房东姓名
    phone: '', //房东手机号码
    idcard: '' ,//房东身份证号码
    desc:'',//房东的描述信息
    userAddress:'',

    code:'',//房东注册表单填写的账号
    password: '',//房东注册表单填写的密码

    logincode: '',//房东登入表单中填写的账号
    loginpassword: '',//房东登入表单中填写的密码
    userAddress:'',//注册地址 手动输入

    currentTab: 0,
    tabCont: [{ "title": "快捷登入", "index": "0" },{ "title": "登入", "index": "1" }, { "title": "注册", "index": "2" }],
    islogin:'',
    loading:0, // 0未加载  1 正在加载
  },
  onLoad: function (options) {
    this.getLogin();
    this.setData({
      islogin:app.data.loginID
    })
    
  },
  getPhoneNumber(e) {
    wx.showLoading({});
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
        wx.request({
          url: app.data.appUrl + '/login/telLogin?tel='+ that.data.tel, 
          method: 'GET',
          header: {
            'content-type': 'application/json'
          },
          success: function (res) {
            console.log("一键登入的返回值 = ");
            console.log(res);
            if(res.data == "none"){
              wx.showToast({
                title: '请先注册',
              })
            }
            else if(res.data[0] == 'admin'){
              wx.navigateTo({
                url: '../../pages/Admin/Admin?departname=' + res.data[1],
              })
            }
            else if (res.data[0] == 'user'){
              app.data.loginID = res.data[1];
              app.data.loginCode = res.data[2];
              app.data.loginTel = res.data[3];
              app.data.loginName = res.data[4];
              wx.navigateBack({
                delta : 2,
              })
            }
            
          },
          complete:function(){
            wx.hideLoading();
          }
        })
      }
    })
  },
  registerFirst(e) {
    var that = this;
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
        that.registerSecond();
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
          // url: 'https://api.weixin.qq.com/sns/jscode2session?appid=wx0f27b4c920436956&secret=48913edcb1b58f214765eb5581ecacf1&js_code=' + that.data.code + '&grant_type=authorization_code',//测试版请求
          url: 'https://changjienongye.cn/docs/getOpenId.jsp?code=' + that.data.code,
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

  GetCurrentTab: function (e) {
    var that = this;
    this.setData({
      currentTab: e.detail.current
    });
    console.log("分页面1中tab滑动后的值 = "+this.data.currentTab);
  },
  // 切换 登入 注册
  swithNav: function (e) {
    var that = this;
    that.setData({
      currentTab: e.target.dataset.current
    });

  },
 
  returnmain:function(){
    var that = this;
    console.log('您点击了登入页面的退出按钮');
    app.data.loginID = '';
    wx.navigateBack({
      delta: 2,
    })
  },

  //  注册
  registerSecond: function () {
    var that = this;
    var code_reg = /^[a-zA-Z0-9_]{4,20}$/;
    var password_reg = /^[a-zA-Z0-9_]{6,30}$/;
    var idcard_reg = /^[0-9xX]{18}$/;
    if (that.data.idcard == '' || idcard_reg.test(that.data.idcard) == false ){
      wx.showToast({
        title: '身份证错误',
      })
    }
    else if (that.data.code == '' || code_reg.test(that.data.code) == false ){
      wx.showToast({
        title : '账号不规范',
      })
    }
    else if (that.data.password == '' || password_reg.test(that.data.password) == false) {
      wx.showToast({
        title: '密码不规范',
      })
    }
    else if (that.data.name == '') {
      wx.showToast({
        title: '姓名为空',
      })
    }
    else{
      wx.showLoading({});
      wx.request({
        // 提交给这个网址，并传相关参数
        url: app.data.appUrl + '/login/register',
        data: {
          code: that.data.code,//登入账号
          password: that.data.password,//登入密码
          name: that.data.name,//姓名
          phone: that.data.tel,//电话号码
          idcard: that.data.idcard,//身份证号码
          desc: that.data.desc,//描述信息
          address: that.data.userAddress,
        },
        method: 'GET',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          console.log(res)
          if (res.data[0] == "exist") {
            wx.showToast({
              title: '账号已注册',
              icon: 'none',
              duration: 1000,
              mask: true
            })
          }
          else {
            app.data.loginID = res.data[0];
            app.data.loginCode = res.data[1];
            app.data.loginTel = res.data[2];
            app.data.loginName = res.data[3];
            wx.navigateBack({
              delta: 2,
            })
          }

        },
        fail: function (res) {
          console.log("注册请求失败");
        },
        complete:function(){
          wx.hideLoading();
        }
      })
    }
  },

  //  登入
  loginTo: function () {
    var that = this;
    var code_reg = /^[a-zA-Z0-9_]{4,20}$/;
    var password_reg = /^[a-zA-Z0-9_]{6,30}$/;
    var idcard_reg = /^[0-9]{18}$/;
    
    if (that.data.logincode == '') {
      wx.showToast({
        title: '账号不能空',
      })
    }
    else if (that.data.loginpassword == '') {
      wx.showToast({
        title: '密码不能空',
      })
    }
    else{
      wx.showLoading({});
      that.data.loading = 1;
      console.log(that.data.loading);
      // setTimeout(function () {  
      //   console.log("测试延迟函数");
      // }, 3000);
      wx.request({
        url: app.data.appUrl + '/login/login',
        data: {
          logincode: that.data.logincode,
          loginpassword: that.data.loginpassword
        },
        method: 'GET',
        header: {
          'content-type': 'application/json'
        },
        success: function (res) {
          console.log(res);
          // wx.hideLoading();
          that.data.loading = 0;
          if (res.data[0] == "no") {
            wx.showToast({
              title: '密码错误',
              icon: 'none',
              duration: 1000,
              mask: true
            })
          }
          else {
            app.data.loginID = res.data[0];
            app.data.loginCode = res.data[1];
            app.data.loginTel = res.data[2];
            app.data.loginName = res.data[3];
            wx.navigateBack({
              delta: 2,
            })
          }
        },
        fail: function (res) {
          console.log("--------登入账号提交失败 fail--------");
        },
        complete:function(){
          wx.hideLoading();
        }
      })
    }
  },

  // 登入账号 , 提供双向数据绑定功能，下同
  code: function (e) {
    this.setData({
      code: e.detail.value
    })
  },

  // 姓名
  name: function (e) {
    this.setData({
      name: e.detail.value
    })
  },

  // 手机号码
  phone: function (e) {
    this.setData({
      phone: e.detail.value
    })
  },

  //身份证号码
  idcard: function (e) {
    this.setData({
      idcard: e.detail.value
    })
  },

  //密码
  password: function (e) {
    this.setData({
      password: e.detail.value
    })
  },

  //登入账号
  logincode: function (e) {
    this.setData({
      logincode: e.detail.value
    })
  },

  //登入密码
  loginpassword: function (e) {
    this.setData({
      loginpassword: e.detail.value
    })
  },
  //描述信息
  desc: function (e) {
    this.setData({
      desc: e.detail.value
    })
  },
  //地址
  userAddress: function (e) {
    this.setData({
      userAddress: e.detail.value
    })
  },
  
  
})