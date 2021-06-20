const app = getApp()
Page({
  data: {
    // 头像
    image: "../../statis/img/userlogo.jpg",
    inf: '',
    // 确认唯一身份的信息
    loginID: '',  //zwz
    nameMsg: '', //郑为中
  },
  onShow :function(){
    console.log("调用onshow方法 从appid获得房东id = " + app.data.loginID );
    this.setData({
      loginID: app.data.loginID,
      nameMsg: app.data.loginName
    })
  },
  onLoad: function (options) {
    if(this.data.inf != ""){
      this.setData({
        image: userinfos.avatarUrl
      })
    }
  },
  // 获取基础授权信息 如昵称 头像
  btn_sub: function (res) {
    var that = this;
    console.log(res.detail);
    var userinfos = res.detail.userInfo;
    that.data.inf = res.detail.userInfo;
    if (userinfos != undefined) {
      this.setData({
        image: userinfos.avatarUrl
      })
    } 
    else {
      this.setData({
        image: "../../statis/img/userlogo.jpg"
      })
    }
  },
  // 点击登入按钮
  login: function(){
    if (this.data.loginID == ''){
      wx.navigateTo({
        url: '../msg/msg'
      }) 
    }
    else{
      wx.navigateTo({
        url: '../login/login'
      })
    }
  },
  // 跳转到第一个分页面
  intent01: function () {
    if (app.data.loginID == "") {
      wx.showToast({
        title: '请您先登入！',
        duration: 2000
      })
    }
    else {
      wx.navigateTo({
        url: '../../pages/AddHouse/AddHouse'
      })
    }
  },
// 跳转到第二个分页面
  intent02: function () {
    if (this.data.loginID == "") {
      wx.showToast({
        title: '请您先登入！',
        duration: 2000
      })
    }
    else {
      wx.navigateTo({
        url: '../../pages/SetTenantHouse/SetTenantHouse'
      })
    }
  },
  // 跳转到第三个分页面
  intent03: function () {
    if (this.data.loginID == "") {
      wx.showToast({
        title: '请您先登入！',
        duration: 2000
      })
    }
    else {
      wx.navigateTo({
        url: '../../pages/MyHouses/MyHouses'
      })
    }
  },
  // 跳转到第四个分页面
  intent04: function () { 
    if (this.data.loginID == "") {
      wx.showToast({
        title: '请您先登入！',
        duration: 2000
      })
    }
    else {
      wx.navigateTo({
        url: '../../pages/MyTenantsByHouse/MyTenantsByHouse'
      })
    }
  },
})