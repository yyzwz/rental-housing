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

import com.forestry.model.sys.param.RoomToTenantParameter;
import com.google.common.base.Objects;

/**
 * @author 齐鸣鸣
 * @房屋类型实体类
 */
@Entity
@Table(name = "room_tenant")
@DynamicInsert(true) 
@DynamicUpdate(true) 
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoomToTenant extends RoomToTenantParameter {

	private static final long serialVersionUID = 6683458492945661067L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "roomId", length = 50)
	private Long roomId; // 房间名称
	@Column(name = "roomName", length = 200)
	private String roomName; // 房间ID
	@Column(name = "tenantId", length = 200)
	private Long tenantId; // 租客ID
	@Column(name = "tenantName", length = 200)
	private String tenantName; // 租客名
	@Column(name = "descInfo", length = 200)
	private String descInfo; // 描述
	@Column(name = "startDate", length = 200)
	private Date startDate; // 开始日期
	@Column(name = "endDate", length = 200)
	private Date endDate; //结束日期
	

	@Column(name = "checkOpion", length = 200)
	private String checkOpion; // 租客名
	@Column(name = "checkDate", length = 200)
	private Date checkDate; // 审核日期
	@Column(name = "checkerId", length = 200)
	private Long checkerId; // 审核者ID
	@Column(name = "checkerName", length = 200)
	private String checkerName; //审核姓名

	@Column(name = "isHistory", length = 20)
	private String isHistory; // 租客名
	
	
	
	public String getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(String isHistory) {
		this.isHistory = isHistory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
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
		final RoomToTenant other = (RoomToTenant) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.roomId, other.tenantId) && Objects.equal(this.roomId, other.tenantId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.roomId,this.tenantId);
	}

}
