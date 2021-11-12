package com.forestry.model.sys;

import java.math.BigInteger;
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
 * @author 郑为中
 * @房主类型查询实体类
 */

public class HouseOwnerQuery extends HouseOwnerParameter {

	private static final long serialVersionUID = 6683458492945661067L;

	private BigInteger id;
	private BigInteger houseOwnerRole;
	private String houseOwnerCode; // 登录账号
	private String houseOwnerPassWord; // 登录密码
	private String houseOwnerName; // 姓名
	private String houseOwnerIdentify; // 身份证号
	private String houseOwnerTel; // 电话
	private String houseOwnerDesc; // 描述信息
	private String houseOwnerAddress; // 描述信息

	private Date houseOwnerLastLoginTime; //最后一次登录
	private Date registDate; // 注册时间	
	private String checkOpion; // 审核内容
	private Date checkDate; // 审核日期
	private BigInteger checkerId; // 审核者ID
	private String checkerName; //审核姓名
	private String houseOwnerImage; // 房主照片
	private String houseOwnerTwoDimensionalCode; // 房主二维码图片
	

	public String getHouseOwnerCode() {
		return houseOwnerCode;
	}



	public BigInteger getId() {
		return id;
	}



	public void setId(BigInteger id) {
		this.id = id;
	}



	public BigInteger getHouseOwnerRole() {
		return houseOwnerRole;
	}



	public void setHouseOwnerRole(BigInteger houseOwnerRole) {
		this.houseOwnerRole = houseOwnerRole;
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


	public BigInteger getCheckerId() {
		return checkerId;
	}



	public void setCheckerId(BigInteger checkerId) {
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



	public HouseOwnerQuery() {

	}



	

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final HouseOwnerQuery other = (HouseOwnerQuery) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.houseOwnerIdentify, other.houseOwnerIdentify) && Objects.equal(this.houseOwnerName, other.houseOwnerName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.houseOwnerName, this.houseOwnerIdentify);
	}

}
