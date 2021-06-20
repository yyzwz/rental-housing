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
    startDate: "2020-01-21",
    endDate: '2050-12-31',
    selectStartDate: '2020-04-01',
    selectEndDate: '2020-06-30',
    //省市区联动下拉框
    customItem: [],
    detailed: '需要重新选择户籍地址!!!',
    sheng: '',
    shi: '',
    qu: '',
    //省市区联动下拉框

    startTime:'',
    endTime:'',
    tenantId:'',
  },
 
  onLoad: function(options) {
    wx.showLoading({});
    this.setData({
      tenantId: options.tenantId,
    })
    this.getTenantData();
    console.log(app.data)
    wx.hideLoading();
  },
  startChange: function (e) {
    this.setData({
      selectStartDate: e.detail.value
    })
    console.log("选择的开始日期 = " + this.data.selectStartDate);
  },
  endChange: function (e) {
    this.setData({
      selectEndDate: e.detail.value
    })
    console.log("选择的开始日期 = " + this.data.selectEndDate);
  },
  getTenantData(){
    var that = this;
    wx.request({
      url: app.data.appUrl + '/tenant/getTenantData1',
      data: {
        tenantId: that.data.tenantId,
      },
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        that.setData({
          name: res.data[0].tenantName,
          idcard: res.data[0].tenantIdentify,
          tel: res.data[0].tenantTel,
          work: res.data[0].tenantWorkOrganization,
        })
      },
      fail: function (res) {
        console.log("--------fail--------");
      }
    })

    wx.request({
      url: app.data.appUrl + '/tenant/getTenantData2',
      data: {
        tenantId: that.data.tenantId,
        roomID: app.data.roomID,
      },
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res);
        that.setData({
          selectStartDate: res.data[0].startDateWithString,
          endTime: res.data[0].endTime,
          desc: res.data[0].descInfo,
          selectEndDate: res.data[0].endDateWithString,
        })

      },
      fail: function (res) {
        console.log("--------fail--------");
      }
    })
  },
  upload_info: function() {
    wx.showLoading({});
    var that = this;
    console.log("提交之前的roomid = " + that.data.roomID);
    wx.request({
      url: app.data.appUrl + '/tenant/updateTenant',
      data: {
        tenantId: that.data.tenantId,
        name: that.data.name, //姓名
        idcard: that.data.idcard, //身份证号码
        tel: that.data.tel, //电话
        sheng: that.data.sheng,//省
        shi: that.data.shi,//市
        qu: that.data.qu,//区
        work: that.data.work,//工作单位
        desc:that.data.desc,
        roomID: app.data.roomID,
        startTime: that.data.selectStartDate,
        endTime: that.data.selectEndDate,
      },
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {  
        console.log(res);
        if (res.data == "notExist"){
          wx.showToast({
            title: '操作失败!',
            duration: 2000
          })
        }
        else{
          wx.showModal({
            title: '是否返回主界面?',
            content: '修改租户信息成功，请选择是否返回主界面',
            success: function (res) {
              if (res.confirm) {
                wx.navigateBack({
                  delta:4
                })
              }
              else if (res.cancel) {
              }
            }
          })
        }
        
      },
      fail: function (res) {
        console.log("--------fail--------");
      },
      complete:function(){
        wx.hideLoading();
      }
    })
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
  selectStartDate: function (e) {
    this.setData({
      selectStartDate: e.detail.value
    })
  },
  // 结尾时间
  selectEndDate: function (e) {
    this.setData({
      selectEndDate: e.detail.value
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
})