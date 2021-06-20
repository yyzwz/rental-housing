// 郑为中
const app = getApp()
Page({

  data: {
    flag:'0',
  },

  // 页面加载时，获取其他页面传递过来的LoginID值
  onLoad: function (options) {
    
  },
  radioChange: function (e) {
    console.log(e.detail.value);
    this.data.flag = e.detail.value;
  },
  next : function(){
    if(this.data.flag == 0){
      wx.showToast({
        title: '请阅读通知',
      })
    }
    else{
      wx.navigateTo({
        url: '../login/login',
      })
    }
  }
})