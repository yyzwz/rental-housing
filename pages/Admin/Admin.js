const app = getApp();
Page({
  data: {
    departname:'',
  },
  onLoad: function (options) {
    app.data.departname = options.departname;
    this.data.departname = options.departname;
    console.log(this.data.departname)
  },
  intent01: function () {
      wx.navigateTo({
        url: '../../pages/CheckHouse/CheckHouse'
      })
  },
  intent02: function () {
    wx.navigateTo({
      url: '../../pages/CheckTenant/CheckTenant'
    })
  },
  intent03: function () {
    var that = this;
    wx.navigateTo({
      url: '../../pages/MyHouses/MyHouses?sys=admin&depardname=' + that.data.departname,
    })
  },

})