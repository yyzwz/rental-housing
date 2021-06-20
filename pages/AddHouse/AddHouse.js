// 郑为中
// 分页面1的JS文件
const app = getApp();
Page({
  data: {
    //房屋类型部分
    newHouseTypeIdList: [],
    newHouseTypeNameList:[],
    newHouseTypeList:[],
    innn:'0',
    houseTypeMsg: '请选择房子的类型', 
    // 村庄类型部分
    newVillageList:[],
    newVillageIdList: [], // new
    newVillageNameList: [],
    villageMsg: '请选择房子所在村社',
    inn: '0',
    // 界面
    height: 40,
    primarySize: 'mini',
    // 表单
    loginID:'',
    name: '',//房屋名称
    address: '', //详细地址
    about: '', //其他描述说明
    indexx:'',//社区数组下标
    images: [] ,//存放图片的数组
    userAddress:'',
    userRole: '1',
    insertHouseID:'',
  },
  onLoad: function (options) {
    this.data.loginID = app.data.loginCode;
    this.getType();
    this.getvillageList();
    this.getVillageNameList();
    this.getHouseTypeNameList();
    this.getTypeAndVillagesIDList();
  },
  addHouse: function () {
    var images_list = [];
    var that = this;
    console.log(that.data);
    if(that.data.address == ''){
      wx.showToast({
        title: '地址为空',
      })
    }
    else if(that.data.name == ''){
      wx.showToast({
        title: '房名为空',
      })
    }
    else if (that.data.userAddress == ''){
      wx.showToast({
        title: '未选定位',
      })
    }else{
      // 如果没有图片
      if (that.data.images.length == 0) {
        wx.showLoading({});
        wx.request({
          url: app.data.appUrl + '/house/addHouse?g=api&m=banana&a=upload_info',
          method: 'POST',
          data: {
            typeId: that.data.newHouseTypeIdList[that.data.innn],//房屋类型ID
            typeName: that.data.newHouseTypeNameList[that.data.innn],//房屋类型名称
            address: that.data.address,//房屋地址
            sheQuID: that.data.newVillageIdList[that.data.inn],//管理部门ID
            sheQuName: that.data.newVillageNameList[that.data.inn],//管理部门名称
            name: that.data.name,//房屋名称
            about: that.data.about,//其他补充描述
            dingWei: that.data.userAddress,//房屋地图信息
            photo: '',//房屋照片
            loginID: app.data.loginID,
            loginName: app.data.loginName,
            imageExist: "0",
          },
          header: {
            "Content-Type": "application/x-www-form-urlencoded" // 默认值
          },
          success(res) {
            if (res.data.code == 0) {
              console.log(res.data.msg)
            }
            else if (res.data.code == 1) {
              wx.showModal({
                title: '是否继续添加房屋？',
                content: '新增房屋成功，如想再次添加房屋，请点击确定；如需要返回主界面，请点击取消',
                success: function (res) {
                  if (res.confirm) {
                    console.log('用户点击确定');
                    that.setData({
                      address: '',
                      name: '',
                      about: '',
                      images: [],
                    })
                  } else if (res.cancel) {
                    console.log('用户点击取消');
                    wx.navigateBack({
                      delta: 1,
                    })
                  }
                }
              })
            }
            else {
              console.log(res.data.msg);
            }
          },
          complete:function(){
            wx.hideLoading();
          }  
        })
      }
      else {
        wx.showLoading({});   
        for (var i = 0; i < that.data.images.length; i++) {
          wx.uploadFile({
            url: app.data.appUrl + '/house/addHouse?g=api&m=banana&a=upload_info',
            filePath: that.data.images[i],
            name: 'photo',
            formData: {
              typeId: that.data.newHouseTypeIdList[that.data.innn],//房屋类型ID
              typeName: that.data.newHouseTypeNameList[that.data.innn],//房屋类型名称
              address: that.data.address,//房屋地址
              sheQuID: that.data.newVillageIdList[that.data.inn],//管理部门ID
              sheQuName: that.data.newVillageNameList[that.data.inn],//管理部门名称
              name: that.data.name,//房屋名称
              about: that.data.about,//其他补充描述
              dingWei: that.data.userAddress,//房屋地图信息
              loginID: app.data.loginID,
              loginName: app.data.loginName,
              imageExist: "1",
            },
            success: function (res) {
              console.log("图片 = " + that.data.images)
              console.log(res);
              var houseID = res.data;
              wx.uploadFile({
                url: app.data.appUrl + '/file/uploadHouseImages?g=api&m=banana&a=upload_info',
                filePath: that.data.images[0],
                method: 'POST',
                name: 'file',
                header: { "Content-Type": "application/x-www-form-urlencoded" },
                formData: {
                  houseID: houseID
                },
                success: function (res) {
                  console.log(res);
                  wx.showModal({
                    title: '是否继续添加房屋？',
                    content: '新增房屋成功，如想再次添加房屋，请点击确定；如需要返回主界面，请点击取消',
                    success: function (res) {
                      if (res.confirm) {
                        console.log('用户点击确定');
                        that.setData({
                          address: '',
                          name: '',
                          about: '',
                          images: [],
                        })
                      } else if (res.cancel) {
                        console.log('用户点击取消');
                        wx.navigateBack({
                          delta: 1,
                        })
                      }
                    }
                  })
                },
                fail: function (res) {
                  console.log(res);
                },
              })
            },
            fail: function (error) {
            },
            complete:function(){
              wx.hideLoading();
            }    
          })
        }
      }
    }
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
    })
  },
  // 选择照片
  chooseImage: function (e) {
    wx.chooseImage({
      sizeType: ['original', 'compressed'], //可选择原图或压缩后的图片
      sourceType: ['album', 'camera'], //可选择性开放访问相册、相机
      success: res => {
        if (this.data.images.length <= 0) {
          const images = this.data.images.concat(res.tempFilePaths)
          this.setData({
            images: images
          })
        } else {
          wx.showToast({
            title: '最多只能选择一张照片',
            icon: 'none',
            duration: 2000,
            mask: true
          })
        }
      }
    })
  },
  // 移除图片
  removeImage(e) {
    const idx = e.target.dataset.idx;
    console.log(e.target.dataset.idx);
    this.data.images.splice(idx, 1);
    var del_image = this.data.images;
    this.setData({
      images: del_image
    })
  },
  // 预览图片
  handleImagePreview(e) {
    const idx = e.target.dataset.idx
    const images = this.data.images
    wx.previewImage({
      current: images[idx], //当前预览的图片
      urls: images, //所有要预览的图片
    })
  },
  // 改变村社下拉框 选项 触发函数
  changeCun(e) {
    var that = this;
    let index = e.detail.value
    console.log("index = " + index);
    this.data.indexx = index
    this.data.inn = index;
    console.log(that.data.newVillageList[index].name);
    this.setData({
      inn:index,
      villageMsg: that.data.newVillageList[index].name
    })

  },
  // 房屋类型选择触发事件
  typeChange: function (e) {
    let index = e.detail.value
    this.data.innn = index
    console.log("index = " + this.data.houseTypeIndex)
    let array = this.data.newHouseTypeList
    console.log("array = " );
    console.log(array)
    this.setData({
      innn:index,
      houseTypeMsg: array[index].houseTypeName
    })
    console.log(this.data.houseTypeMsg);
  },
  // 详细地址双向数据绑定
  address: function (e) {
    this.setData({
      address: e.detail.value
    })
  },
  // 房屋名称双向数据绑定
  name: function (e) {
    this.setData({
      name: e.detail.value
    })
  },
  // 其他说明
  about: function (e) {
    this.setData({
      about: e.detail.value
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
  getvillageList: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/getDepartmentList',
      method: 'GET',
      data: {
      },
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
      data: {

      },
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log(res);
        that.setData({
          newVillageNameList: res.data,
        })
        console.log("NameList = ");
        console.log(res.data)
      }
    })
  },
  getType: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/houseTypeList',
      method: 'GET',
      data: {

      },
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log(res);
        that.setData({
          newHouseTypeList: res.data,
        })
        console.log("函数内 newHouseTypeList = ");
        console.log(that.data.newHouseTypeList);
      }
    })
  },
  getHouseTypeNameList: function () {
    var that = this;
    wx.request({
      url: app.data.appUrl + '/house/houseTypeNameList',
      method: 'GET',
      data: {

      },
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        console.log(res);
        that.setData({
          newHouseTypeNameList: res.data,
        })
        console.log("函数内 newHouseTypeNameList = ");
        console.log(that.data.newHouseTypeNameList);
      }
    })
  },
})