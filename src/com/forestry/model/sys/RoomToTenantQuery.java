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

import com.forestry.model.sys.param.RoomToTenantParameter;
import com.google.common.base.Objects;

/**
 * @author 齐鸣鸣
 * @房屋类型实体类
 */

public class RoomToTenantQuery extends RoomToTenantParameter {

	private static final long serialVersionUID = 6683458492945661067L;

	private BigInteger id;
	private BigInteger roomId; // 房间名称
	private String roomName; // 房间ID
	private BigInteger tenantId; // 租客ID
	private String tenantName; // 租客名
	private String descInfo; // 描述
	private Date startDate; // 开始日期
	private Date endDate; //结束日期
	private String isHistory; // 租客名
	private String checkOpion; // 租客名
	private Date checkDate; // 审核日期
	private BigInteger checkerId; // 审核者ID
	private String checkerName; //审核姓名

	private String departmentName; //区域名
	private String houseName; //房屋名
	private String tenantIdentify; //房屋名
	private String tenantTel; //区域名
	private String tenantFromShen; //房屋名
	private String tenantFromShi; //房屋名private String departmentName; //区域名
	private String tenantFromXian; //房屋名
	private String tenantWorkOrganization; //房屋名
	
	
	
	public String getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(String isHistory) {
		this.isHistory = isHistory;
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

	public void setTenantFromXian(String tenantFromXian) {
		this.tenantFromXian = tenantFromXian;
	}

	public String getTenantWorkOrganization() {
		return tenantWorkOrganization;
	}

	public void setTenantWorkOrganization(String tenantWorkOrganization) {
		this.tenantWorkOrganization = tenantWorkOrganization;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	public BigInteger getRoomId() {
		return roomId;
	}

	public void setRoomId(BigInteger roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public BigInteger getTenantId() {
		return tenantId;
	}

	public void setTenantId(BigInteger tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}





	public String getDescInfo() {
		return descInfo;
	}

	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RoomToTenantQuery other = (RoomToTenantQuery) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.roomId, other.tenantId) && Objects.equal(this.roomId, other.tenantId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.roomId,this.tenantId);
	}

}
