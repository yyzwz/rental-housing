//houseID
const app = getApp();
Page({
  data: {
    houseID: '',
    newHouseTypeIdList: [],
    newHouseTypeNameList: [],
    newHouseTypeList: [],
    innn: '0',//默认类型下标
    inn: '0',//默认村下标
    newVillageList: [],
    newVillageIdList: [], // new
    newVillageNameList: [],

    // 下拉框数据
    villageMsg: '请选择房子所在村社',
    houseTypeMsg: '请选择房子的类型',
    userAddress: '',

    about:'',
    address:'', 
    name: '',
    pastHouseTypeName:'',
    pastDepartName:'',
  },

  onLoad: function (options) {
    wx.showLoading({});
    console.log(options)
    app.data.houseID = options.addId;
    this.getType();
    this.getvillageList();
    this.getVillageNameList();
    this.getHouseTypeNameList();
    this.getPastData();
    this.getTypeAndVillagesIDList();
    console.log("apphouseid = " + app.data.houseID )
    wx.hideLoading();
  },
  getPastData:function(){
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/getHouseMsg',
      method: 'GET',
      data: {
        houseID: app.data.houseID,
      },
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log("getPastData = " );
        console.log(res);
       that.setData({
         about: res.data[0].houseDesc,
         address: res.data[0].houseAddress,
         name: res.data[0].houseName,
         pastHouseTypeName: res.data[0].houseTypeName,
         pastDepartName: res.data[0].departmentName,
       })
      }
    })
  },
  getvillageList: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/getDepartmentList',
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log(res);
        that.setData({
          newVillageList: res.data,
        })
      }
    })
  },
  getVillageNameList: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/getDepartmentNameList',
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log(res);
        that.setData({
          newVillageNameList: res.data,
        })
        console.log(res.data)
      }
    })
  },
  getType: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/houseTypeList',
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log(res);
        that.setData({
          newHouseTypeList: res.data,
        })
        console.log(that.data.newHouseTypeList);
      }
    })
  },
  getHouseTypeNameList: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/houseTypeNameList',
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log(res);
        that.setData({
          newHouseTypeNameList: res.data,
        })
        console.log(that.data.newHouseTypeNameList);
      }
    })
  },
  // 房屋类型选择触发事件
  typeChange: function (e) {
    let index = e.detail.value
    this.data.innn = index
    console.log("index = " + this.data.houseTypeIndex)
    let array = this.data.newHouseTypeList
    console.log("array = ");
    console.log(array)
    this.setData({
      innn: index,
      houseTypeMsg: array[index].houseTypeName
    })
    console.log(this.data.houseTypeMsg);
  },
  changeCun(e) {
    var that = this;
    let index = e.detail.value
    console.log("index = " + index);
    this.data.indexx = index
    this.data.inn = index;
    console.log(that.data.newVillageList[index].name);
    this.setData({
      inn: index,
      villageMsg: that.data.newVillageList[index].name
    })

  }, 
  upload_info: function () {
    wx.showLoading({});
    var that = this;
      wx.request({
        url: app.data.appUrl + '/house/updeteHouse',
        method: 'GET',
        data: {
          houseID: app.data.houseID,
          typeId: that.data.newHouseTypeIdList[that.data.innn],//房屋类型ID
          typeName: that.data.newHouseTypeNameList[that.data.innn],//房屋类型名称
          address: that.data.address,//房屋地址
          sheQuID: that.data.newVillageIdList[that.data.inn],//管理部门ID
          sheQuName: that.data.newVillageNameList[that.data.inn],//管理部门名称
          name: that.data.name,//房屋名称
          about: that.data.about,//其他补充描述
        },
        header: {
          "Content-Type": "application/x-www-form-urlencoded" // 默认值
        },
        success(res) {
          wx.showModal({
            title: '是否继续修改房屋信息？',
            content: '修改房屋信息成功，如想再次修改，请点击确定；如需要返回主界面，请点击取消',
            success: function (res) {
              if (res.confirm) {
              } else if (res.cancel) {
                wx.navigateBack({
                  delta: 2,
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
  // 定位功能
  mapView: function () {
    var that = this
    wx.chooseLocation({
      success: function (res) {
        console.log(res);
        that.data.userAddress = res.address + res.name + ',latitude=' + res.latitude
          + ",longitude=" + res.longitude;
        console.log("您选择的定位地址 = " + that.data.userAddress);
        that.setData({
          hasLocation: true,
          location: {
            longitude: res.longitude,
            latitude: res.latitude
          },
          detail_info: res.address,
          wd: res.latitude,
          jd: res.longitude
        })
      },
      fail: function () {
      },
      complete: function () {
      }
    })
  },
  name: function(e) {
    this.setData({
      name: e.detail.value
    })
  },
  about: function (e) {
    this.setData({
      about: e.detail.value
    })
  },
  address: function (e) {
    this.setData({
      address: e.detail.value
    })
  },
  getTypeAndVillagesIDList: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/getHouseTypeIdList',
      method: 'GET',
      data: {
      },
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log(res);
        that.setData({
          newHouseTypeIdList: res.data
        })
      }
    })
    wx.request({
      url: app.data.appUrl + '/house/getVillageIdList',
      method: 'GET',
      data: {
      },
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log(res);
        that.setData({
          newVillageIdList: res.data
        })
      }
    })
  },
})