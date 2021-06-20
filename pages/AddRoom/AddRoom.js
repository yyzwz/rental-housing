const app = getApp();
Page({
  data: {
    roomName: '',
    roomSize: '',
    roomDesc: '',
    roomImage: '',
    houseID: '',
    houseName: '',
    images: [],//存放图片的数组
  },
  onLoad: function (options) {
  },
  upload_info: function () {
    var that = this;
    var size_reg = /^[0-9]{1,20}$/;
    if (that.data.roomName == '') {
      wx.showToast({
        title: '房名为空',
      })
    }
    else if (that.data.roomSize == '' || size_reg.test(that.data.roomSize) == false) {
      wx.showToast({
        title: '大小错误',
      })
    }
    else{
      if (that.data.images.length == 0) {
        wx.showLoading({});
        wx.request({
          url: app.data.appUrl + '/house/setRoom?g=api&m=banana&a=upload_info',
          method: 'POST',
          data: {
            roomName: that.data.roomName,
            roomSize: that.data.roomSize,
            roomDesc: that.data.roomDesc,
            houseID: app.data.houseID,
            imageExist: "0",
          },
          header: {
            "Content-Type": "application/x-www-form-urlencoded" // 默认值
          },
          success(res) {
            wx.showModal({
              title: '是否继续添加?',
              content: '添加房间信息成功，请选择是否继续添加',
              success: function (res) {
                if (res.confirm) {
                  that.setData({
                    roomName: '',
                    roomDesc: '',
                    roomSize: '',
                  })
                }
                else if (res.cancel) {
                  wx.navigateBack({
                    delta: 3
                  })
                }
              }
            })
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
            url: app.data.appUrl + '/house/setRoom?g=api&m=banana&a=upload_info',
            filePath: that.data.images[i],
            name: 'photo',
            formData: {
              roomName: that.data.roomName,
              roomSize: that.data.roomSize,
              roomDesc: that.data.roomDesc,
              houseID: app.data.houseID,
              imageExist: "1",
            },
            success: function (res) {
              console.log("图片 = " + that.data.images)
              var roomImage = res.data
              wx.uploadFile({
                url: app.data.appUrl + '/file/uploadRoomImages?g=api&m=banana&a=upload_info',
                filePath: that.data.images[0],
                method: 'POST',
                name: 'file',
                header: { "Content-Type": "application/x-www-form-urlencoded" },
                formData: {
                  roomImage: roomImage
                },
              })
              wx.showModal({
                title: '是否继续添加?',
                content: '添加房间信息成功，请选择是否继续添加',
                success: function (res) {
                  if (res.confirm) {
                    that.setData({
                      roomName: '',
                      roomDesc: '',
                      roomSize: '',
                      images: [],
                    })
                  }
                  else if (res.cancel) {
                    wx.navigateBack({
                      delta: 3
                    })
                  }
                }
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

  //房间名称
  roomName: function (e) {
    this.setData({
      roomName: e.detail.value
    })
  },

  //房间面积
  roomSize: function (e) {
    this.setData({
      roomSize: e.detail.value
    })
  },

  //房间照片
  roomDesc: function (e) {
    this.setData({
      roomDesc: e.detail.value
    })
  },

  //房屋ID
  houseID: function (e) {
    this.setData({
      houseID: e.detail.value
    })
  },

  //房屋名称
  houseName: function (e) {
    this.setData({
      houseName: e.detail.value
    })
  },

  //添加图片
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