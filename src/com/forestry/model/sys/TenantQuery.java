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

import com.forestry.model.sys.param.TenantParameter;
import com.google.common.base.Objects;

/**
 * @author 齐鸣鸣
 * @房屋类型实体类
 */

public class TenantQuery extends TenantParameter {

	private static final long serialVersionUID = 6683458492945661067L;

	private BigInteger id;

	private String tenantName; //姓名

	private String tenantIdentify; //身份证号

	private String tenantTel; //电话

	private String tenantFromShen; //来源省

	private String tenantFromShi; //来源市

	private String tenantFromXian; //来源县

	private String tenantWorkOrganization; // 工作单位

	private String tenantDesc; // 描述信息

	private String TenantImage; // 照片

	private String checkOpion; // 注册日期

	private Date checkDate; // 审核日期

	private BigInteger checkerId; // 审核者ID

	private String checkerName; //审核姓名

	private String tenantCode; //审核姓名
	
    private String houseOwnerName;//房东姓名
    private BigInteger houseOwnerId;//房东ID
    private BigInteger houseId;//房ID
    private String houseImage; //审核姓名
    
    private BigInteger countNum;
	public TenantQuery() {

	}

	

	public BigInteger getCountNum() {
		return countNum;
	}



	public void setCountNum(BigInteger countNum) {
		this.countNum = countNum;
	}



	public String getHouseImage() {
		return houseImage;
	}



	public void setHouseImage(String houseImage) {
		this.houseImage = houseImage;
	}



	public BigInteger getHouseId() {
		return houseId;
	}



	public void setHouseId(BigInteger houseId) {
		this.houseId = houseId;
	}



	public String getHouseOwnerName() {
		return houseOwnerName;
	}



	public void setHouseOwnerName(String houseOwnerName) {
		this.houseOwnerName = houseOwnerName;
	}



	public BigInteger getHouseOwnerId() {
		return houseOwnerId;
	}



	public void setHouseOwnerId(BigInteger houseOwnerId) {
		this.houseOwnerId = houseOwnerId;
	}



	public String getTenantCode() {
		return tenantCode;
	}



	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}



	public void setTenantFromXian(String tenantFromXian) {
		this.tenantFromXian = tenantFromXian;
	}



	

	

	public String getTenantDesc() {
		return tenantDesc;
	}



	public void setTenantDesc(String tenantDesc) {
		this.tenantDesc = tenantDesc;
	}



	public String getTenantName() {
		return tenantName;
	}



	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}



	public String getTenantIdentify() {
		return tenantIdentify;
	}



	public void setTenantIdentify(String tenantIdentify) {
		this.tenantIdentify = tenantIdentify;
	}



	public String getTenantTel() {
		return tenantTel;
	}



	public void setTenantTel(String tenantTel) {
		this.tenantTel = tenantTel;
	}



	public String getTenantFromShen() {
		return tenantFromShen;
	}



	public void setTenantFromShen(String tenantFromShen) {
		this.tenantFromShen = tenantFromShen;
	}



	public String getTenantFromShi() {
		return tenantFromShi;
	}



	public void setTenantFromShi(String tenantFromShi) {
		this.tenantFromShi = tenantFromShi;
	}



	public String getTenantFromXian() {
		return tenantFromXian;
	}



	public void setTenantFromxXian(String tenantFromXian) {
		this.tenantFromXian = tenantFromXian;
	}



	public String getTenantWorkOrganization() {
		return tenantWorkOrganization;
	}



	public void setTenantWorkOrganization(String tenantWorkOrganization) {
		this.tenantWorkOrganization = tenantWorkOrganization;
	}



	public String getTenantImage() {
		return TenantImage;
	}



	public void setTenantImage(String tenantImage) {
		TenantImage = tenantImage;
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



	



	public BigInteger getId() {
		return id;
	}



	public void setId(BigInteger id) {
		this.id = id;
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



	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TenantQuery other = (TenantQuery) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.tenantIdentify, other.tenantIdentify) && Objects.equal(this.tenantName, other.tenantName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.tenantName, this.tenantIdentify);
	}

}
