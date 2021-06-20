// 郑为中
// 子页面2的JS文件  
const app = getApp();
Page({

  data: {
    
    // 样式属性
    height: 40,
    primarySize: 'mini', //按钮的尺寸为小尺寸
 
    //  表单信息
    name: '', //姓名
    tel: '', //手机号
    idcard: '', //身份证号码
    city:'',//籍贯
    work: '',//工作地址
    desc:'',
    roomID:'',


    // 日期联动选择框数据
    startDate:"2020-01-21",
    endDate:'2050-12-31',
    selectStartDate:'2020-03-23',
    selectEndDate: '2020-05-23',
    //省市区联动下拉框
    customItem: [],
    detailed: '请选择户籍地址',
    sheng: '',
    shi: '',
    qu: '',
    //省市区联动下拉框

    startTime:'',
    endTime:'',
    images: [],//存放图片的数组
  },
 
  onLoad: function(options) {
    this.setData({
      roomID: options.roomId
    })
    app.data.roomID = options.roomId;
  },
  startChange:function(e){
    this.setData({
      selectStartDate:e.detail.value
    })
    console.log("选择的开始日期 = " + this.data.selectStartDate);
  },
  endChange: function (e) {
    this.setData({
      selectEndDate: e.detail.value
    })
    console.log("选择的开始日期 = " + this.data.selectEndDate);
  },

  upload_info: function() {
    console.log(this.data);
    var images_list = [];
    var idcard_reg = /^[0-9xX]{18}$/;
    var tel_reg = /^1[0-9]{10}$/;
    var that = this;
    if(that.data.name == ''){
      wx.showToast({
        title: '姓名为空',
      })
    }
    else if (that.data.idcard == '' || idcard_reg.test(that.data.idcard)==false) {
      wx.showToast({
        title: '身份证错误',
      })
    }
    else if (that.data.tel == '' || tel_reg.test(that.data.tel)==false) {
      wx.showToast({
        title: '电话错误',
      })
    }
    else if(that.data.work == ''){
      wx.showToast({
        title: '单位为空',
      })
    }
    else{
      if (that.data.images.length == 0) {
        wx.request({
          url: app.data.appUrl + '/tenant/setTenant',
          data: {
            name: that.data.name, //姓名
            idcard: that.data.idcard, //身份证号码
            tel: that.data.tel, //电话
            sheng: that.data.sheng,//省
            shi: that.data.shi,//市
            qu: that.data.qu,//区
            work: that.data.work,//工作单位
            desc: that.data.desc,
            roomIdIndex: app.data.roomID,
            startTime: that.data.selectStartDate,
            endTime: that.data.selectEndDate,
            imageExist: "0",
          },
          method: 'GET',
          header: {
            'content-type': 'application/json'
          },
          success: function (res) {
            wx.showModal({
              title: '是否继续添加租客？',
              content: '新增租户成功，如想再次添加租客，请点击确定；如需要返回主界面，请点击取消',
              success: function (res) {
                if (res.confirm) {
                  that.setData({
                    name: '',
                    idcard: '',
                    tel: '',
                    work: '',
                    desc: '',
                  })
                } else if (res.cancel) {
                  wx.navigateBack({
                    delta: 3
                  })
                }
              }
            })

          },
          fail: function (res) {
            console.log("--------fail--------");
          }
        })
      }
      else {
        wx.request({
          url: app.data.appUrl + '/tenant/setTenant',
          data: {
            name: that.data.name, //姓名
            idcard: that.data.idcard, //身份证号码
            tel: that.data.tel, //电话
            sheng: that.data.sheng,//省
            shi: that.data.shi,//市
            qu: that.data.qu,//区
            work: that.data.work,//工作单位
            desc: that.data.desc,
            roomIdIndex: app.data.roomID,
            startTime: that.data.selectStartDate,
            endTime: that.data.selectEndDate,
            imageExist: "1",
          },
          method: 'GET',
          header: {
            'content-type': 'application/json'
          },
          success: function (res) {
            console.log(res);
            var tenantID = res.data[0];
            wx.uploadFile({
              url: app.data.appUrl + '/file/uploadTenantImages?g=api&m=banana&a=upload_info',
              filePath: that.data.images[0],
              method: 'POST',
              name: 'file',
              header: { "Content-Type": "application/x-www-form-urlencoded" },
              formData: {
                tenantID: tenantID
              },
              success: function (res) {
                console.log("图片 = " + that.data.images)
              },
              fail: function (error) {
              }
            })
            wx.showModal({
              title: '是否继续添加租客？',
              content: '新增租户成功，如想再次添加租客，请点击确定；如需要返回主界面，请点击取消',
              success: function (res) {
                if (res.confirm) {
                  that.setData({
                    name: '',
                    idcard: '',
                    tel: '',
                    work: '',
                    desc: '',
                  })
                } else if (res.cancel) {
                  wx.navigateBack({
                    delta: 3
                  })
                }
              }
            })

          },
          fail: function (res) {
            console.log("--------fail--------");
          }
        })
      }
    }
  },

  // 姓名
  name: function(e) {
    this.setData({
      name: e.detail.value
    })
  },

  // 电话
  tel: function (e) {
    this.setData({
      tel: e.detail.value
    })
  },

  // 身份证
  idcard: function (e) {
    this.setData({
      idcard: e.detail.value
    })
  },

  // 户籍
  city: function (e) {
    this.setData({
      city: e.detail.value
    })
  },

  // 工作单位
  work: function (e) {
    this.setData({
      work: e.detail.value
    })
  },
  // 开始时间
  startTime: function (e) {
    this.setData({
      startTime: e.detail.value
    })
  },
  // 结尾时间
  endTime: function (e) {
    this.setData({
      endTime: e.detail.value
    })
  },

  //描述信息
  desc: function (e) {
    this.setData({
      desc: e.detail.value
    })
  },

  // 省市区三级联动函数
  bindRegionChange: function (e) {
    var that = this
    //为了让选择框有个默认值，    
    that.setData({
      clas: ''
    })　　　//下拉框所选择的值
    console.log('picker发送选择改变，携带值为', e.detail.value)

    this.setData({
      //拼的字符串传后台
      detailed: e.detail.value[0] + " " + e.detail.value[1] + " " + e.detail.value[2],
      //下拉框选中的值
      region: e.detail.value
    })
    this.setData({
      "AddSite.area": e.detail.value[0] + " " + e.detail.value[1] + " " + e.detail.value[2]
    })
    that.data.sheng = e.detail.value[0];
    that.data.shi = e.detail.value[1];
    that.data.qu = e.detail.value[2];
    console.log(that.data.sheng + that.data.shi + that.data.qu);
  },

  chooseImage: function (e) {
    wx.chooseImage({
      sizeType: ['original', 'compressed'], //可选择原图或压缩后的图片
      sourceType: ['album', 'camera'], //可选择性开放访问相册、相机
      success: res => {
        if (this.data.images.length <= 0) {
          const images = this.data.images.concat(res.tempFilePaths)
          // 限制最多只能留下1张照片
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
})