package app.com.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import app.com.model.param.AppRoomAndTenantParameter;

/**
 * @author 郑为中
 */

@Entity
@Table(name = "room_tenant")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppRoomAndTenant extends AppRoomAndTenantParameter {
	
	private static final long serialVersionUID = 9034294844559970211L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "roomId", length = 20)
	private Long roomId; // 房间ID
	
	@Column(name = "roomName", length = 255)
	private String roomName; // 房间名
	
	@Column(name = "tenantId", length = 20)
	private Long tenantId; // 租客ID
	
	@Column(name = "tenantName", length = 255)
	private String tenantName; // 租客名
	
	@Column(name = "descInfo", length = 255)
	private String descInfo; // 描述信息
	
	@Column(name = "startDate", length = 200)
	private Date startDate; // 开始日期
	
	@Column(name = "endDate", length = 200)
	private Date endDate; // 结束日期

	@Column(name = "checkOpion", length = 50)
	private String checkOpion; 
	
	@Column(name = "isHistory", length = 25)
	private String isHistory; 
	
	
	
	public String getCheckOpion() {
		return checkOpion;
	}

	public void setCheckOpion(String checkOpion) {
		this.checkOpion = checkOpion;
	}

	public String getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(String isHistory) {
		this.isHistory = isHistory;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	
	
	

	
	
	
}
