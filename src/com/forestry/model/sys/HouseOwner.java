package com.forestry.model.sys;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.forestry.model.sys.param.HouseOwnerParameter;
import com.google.common.base.Objects;

/**
 * @author 齐鸣鸣
 * @房屋类型实体类
 */
@Entity
@Table(name = "houseowner")
@DynamicInsert(true) 
@DynamicUpdate(true) 
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HouseOwner extends HouseOwnerParameter {

	private static final long serialVersionUID = 6683458492945661067L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "houseOwnerCode", length = 50)
	private String houseOwnerCode; // 登录账号
	@Column(name = "houseOwnerPassWord", length = 200)
	private String houseOwnerPassWord; // 登录密码
	@Column(name = "houseOwnerName", length = 200)
	private String houseOwnerName; // 姓名
	@Column(name = "houseOwnerIdentify", length = 200)
	private String houseOwnerIdentify; // 身份证号
	@Column(name = "houseOwnerTel", length = 200)
	private String houseOwnerTel; // 电话
	@Column(name = "houseOwnerDesc", length = 200)
	private String houseOwnerDesc; // 描述信息
	@Column(name = "houseOwnerAddress", length = 200)
	private String houseOwnerAddress; // 描述信息

	@Column(name = "houseOwnerLastLoginTime", length = 200)
	private Date houseOwnerLastLoginTime; //最后一次登录
	
	@Column(name = "registDate", length = 200)
	private Date registDate; // 注册时间	
	
	@Column(name = "checkOpion", length = 200)
	private String checkOpion; // 审核内容
	@Column(name = "checkDate", length = 200)
	private Date checkDate; // 审核日期
	@Column(name = "checkerId", length = 200)
	private Long checkerId; // 审核者ID
	@Column(name = "checkerName", length = 200)
	private String checkerName; //审核姓名
	
	@Column(name = "houseOwnerImage", length = 200)
	private String houseOwnerImage; // 房主照片
	@Column(name = "houseOwnerTwoDimensionalCode", length = 200)
	private String houseOwnerTwoDimensionalCode; // 房主二维码图片
	

	public String getHouseOwnerCode() {
		return houseOwnerCode;
	}



	public void setHouseOwnerCode(String houseOwnerCode) {
		this.houseOwnerCode = houseOwnerCode;
	}



	public String getHouseOwnerPassWord() {
		return houseOwnerPassWord;
	}



	public void setHouseOwnerPassWord(String houseOwnerPassWord) {
		this.houseOwnerPassWord = houseOwnerPassWord;
	}



	public String getHouseOwnerName() {
		return houseOwnerName;
	}



	public void setHouseOwnerName(String houseOwnerName) {
		this.houseOwnerName = houseOwnerName;
	}



	public String getHouseOwnerIdentify() {
		return houseOwnerIdentify;
	}



	public void setHouseOwnerIdentify(String houseOwnerIdentify) {
		this.houseOwnerIdentify = houseOwnerIdentify;
	}



	public String getHouseOwnerTel() {
		return houseOwnerTel;
	}



	public void setHouseOwnerTel(String houseOwnerTel) {
		this.houseOwnerTel = houseOwnerTel;
	}



	public String getHouseOwnerDesc() {
		return houseOwnerDesc;
	}



	public void setHouseOwnerDesc(String houseOwnerDesc) {
		this.houseOwnerDesc = houseOwnerDesc;
	}



	


	public Date getHouseOwnerLastLoginTime() {
		return houseOwnerLastLoginTime;
	}



	public void setHouseOwnerLastLoginTime(Date houseOwnerLastLoginTime) {
		this.houseOwnerLastLoginTime = houseOwnerLastLoginTime;
	}



	public Date getRegistDate() {
		return registDate;
	}



	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}







	public String getCheckOpion() {
		return checkOpion;
	}



	public void setCheckOpion(String checkOpion) {
		this.checkOpion = checkOpion;
	}



	public Date getCheckDate() {
		return checkDate;
	}



	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}



	public Long getCheckerId() {
		return checkerId;
	}



	public void setCheckerId(Long checkerId) {
		this.checkerId = checkerId;
	}



	public String getCheckerName() {
		return checkerName;
	}



	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}



	public String getHouseOwnerImage() {
		return houseOwnerImage;
	}



	public void setHouseOwnerImage(String houseOwnerImage) {
		this.houseOwnerImage = houseOwnerImage;
	}



	public String getHouseOwnerTwoDimensionalCode() {
		return houseOwnerTwoDimensionalCode;
	}



	public void setHouseOwnerTwoDimensionalCode(String houseOwnerTwoDimensionalCode) {
		this.houseOwnerTwoDimensionalCode = houseOwnerTwoDimensionalCode;
	}



	public String getHouseOwnerAddress() {
		return houseOwnerAddress;
	}



	public void setHouseOwnerAddress(String houseOwnerAddress) {
		this.houseOwnerAddress = houseOwnerAddress;
	}



	public HouseOwner() {

	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final HouseOwner other = (HouseOwner) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.houseOwnerIdentify, other.houseOwnerIdentify) && Objects.equal(this.houseOwnerName, other.houseOwnerName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.houseOwnerName, this.houseOwnerIdentify);
	}

}
