package app.com.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import app.com.model.param.AppTenantAndHouseAndRoomParameter;
import app.com.model.param.AppUserParameter;

/**
 * @author 郑为中
 * @租客房屋房间关系实体类
 */
@Entity
@Table(name = "room_tenant")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppTenantAndHouseAndRoom extends AppTenantAndHouseAndRoomParameter{
	
	private static final long serialVersionUID = 7034294844559970211L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "roomId", length = 20)
	private Long roomId; // 房间ID
	
	@Column(name = "roomName", length = 255)
	private String RoomName; // 房间名称
	
	@Column(name = "tenantId", length = 20)
	private Long TenantId; // 租客ID
	
	@Column(name = "tenantName", length = 255)
	private String TenantName; // 租客姓名
	
	@Column(name = "descInfo", length = 255)
	private String Desc; // 描述信息
	
	@Column(name = "startDate", length = 20)
	private String StartDate; // 开始入住时间
	
	@Column(name = "endDate", length = 20)
	private String EndDate; // 结束入住时间
	
	
	@Column(name = "checkOpion", length = 50)
	private String checkOpion; // 描述信息
	
	@Column(name = "checkerId", length = 20)
	private Long checkerId; // 开始入住时间
	
	@Column(name = "checkerName", length = 50)
	private String checkerName; // 结束入住时间
	
	@Column(name = "checkDate", length = 20)
	private Date checkDate; // 开始入住时间
	
	@Column(name = "isHistory", length = 25)
	private String isHistory; // 结束入住时间
	
	

	public String getCheckOpion() {
		return checkOpion;
	}

	public void setCheckOpion(String checkOpion) {
		this.checkOpion = checkOpion;
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

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

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

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return RoomName;
	}

	public void setRoomName(String roomName) {
		RoomName = roomName;
	}

	public Long getTenantId() {
		return TenantId;
	}

	public void setTenantId(Long tenantId) {
		TenantId = tenantId;
	}

	public String getTenantName() {
		return TenantName;
	}

	public void setTenantName(String tenantName) {
		TenantName = tenantName;
	}

	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Desc == null) ? 0 : Desc.hashCode());
		result = prime * result + ((EndDate == null) ? 0 : EndDate.hashCode());
		result = prime * result + ((RoomName == null) ? 0 : RoomName.hashCode());
		result = prime * result + ((StartDate == null) ? 0 : StartDate.hashCode());
		result = prime * result + ((TenantId == null) ? 0 : TenantId.hashCode());
		result = prime * result + ((TenantName == null) ? 0 : TenantName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppTenantAndHouseAndRoom other = (AppTenantAndHouseAndRoom) obj;
		if (Desc == null) {
			if (other.Desc != null)
				return false;
		} else if (!Desc.equals(other.Desc))
			return false;
		if (EndDate == null) {
			if (other.EndDate != null)
				return false;
		} else if (!EndDate.equals(other.EndDate))
			return false;
		if (RoomName == null) {
			if (other.RoomName != null)
				return false;
		} else if (!RoomName.equals(other.RoomName))
			return false;
		if (StartDate == null) {
			if (other.StartDate != null)
				return false;
		} else if (!StartDate.equals(other.StartDate))
			return false;
		if (TenantId == null) {
			if (other.TenantId != null)
				return false;
		} else if (!TenantId.equals(other.TenantId))
			return false;
		if (TenantName == null) {
			if (other.TenantName != null)
				return false;
		} else if (!TenantName.equals(other.TenantName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (roomId == null) {
			if (other.roomId != null)
				return false;
		} else if (!roomId.equals(other.roomId))
			return false;
		return true;
	}

}
