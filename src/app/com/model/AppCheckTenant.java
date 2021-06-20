package app.com.model;

import java.math.BigInteger;
import java.sql.Date;

import app.com.model.param.AppTenantParameter;

public class AppCheckTenant extends AppTenantParameter{

	private static final long serialVersionUID = 6683498492945661067L;
	
	private BigInteger tenantId;
	
	private String TenantName; // 姓名
	
	private String TenantIdentify; // 身份证号
	
	private String TenantTel; // 电话
	
	private String TenantFromShen; // 户籍
	
	private String TenantFromShi; // 户籍
	
	private String TenantFromXian; // 户籍
	
	private String TenantWorkOrganization; // 工作单位
	
	private String TenantDesc; // 租客描述

	private String tenantImage; // 照片
	
	private String HouseOwnerCode; // 房东账号
	
	private String checkOpion; // 是否审核
	
	private BigInteger roomId; // 房间ID
	
	private String RoomName; // 房间名称
	
	private Date StartDate; // 开始入住时间
	
	private Date EndDate; // 结束入住时间
	
	private String HouseOwnerName;
	
	private String HouseOwnerTel;
	
	private String HouseAddress;
	
	private BigInteger id;

	public BigInteger getTenantId() {
		return tenantId;
	}

	public void setTenantId(BigInteger tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return TenantName;
	}

	public void setTenantName(String tenantName) {
		TenantName = tenantName;
	}

	public String getTenantIdentify() {
		return TenantIdentify;
	}

	public void setTenantIdentify(String tenantIdentify) {
		TenantIdentify = tenantIdentify;
	}

	public String getTenantTel() {
		return TenantTel;
	}

	public void setTenantTel(String tenantTel) {
		TenantTel = tenantTel;
	}

	public String getTenantFromShen() {
		return TenantFromShen;
	}

	public void setTenantFromShen(String tenantFromShen) {
		TenantFromShen = tenantFromShen;
	}

	public String getTenantFromShi() {
		return TenantFromShi;
	}

	public void setTenantFromShi(String tenantFromShi) {
		TenantFromShi = tenantFromShi;
	}

	public String getTenantFromXian() {
		return TenantFromXian;
	}

	public void setTenantFromXian(String tenantFromXian) {
		TenantFromXian = tenantFromXian;
	}

	public String getTenantWorkOrganization() {
		return TenantWorkOrganization;
	}

	public void setTenantWorkOrganization(String tenantWorkOrganization) {
		TenantWorkOrganization = tenantWorkOrganization;
	}

	public String getTenantDesc() {
		return TenantDesc;
	}

	public void setTenantDesc(String tenantDesc) {
		TenantDesc = tenantDesc;
	}

	public String getTenantImage() {
		return tenantImage;
	}

	public void setTenantImage(String tenantImage) {
		this.tenantImage = tenantImage;
	}

	public String getHouseOwnerCode() {
		return HouseOwnerCode;
	}

	public void setHouseOwnerCode(String houseOwnerCode) {
		HouseOwnerCode = houseOwnerCode;
	}

	public String getCheckOpion() {
		return checkOpion;
	}

	public void setCheckOpion(String checkOpion) {
		this.checkOpion = checkOpion;
	}

	public BigInteger getRoomId() {
		return roomId;
	}

	public void setRoomId(BigInteger roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return RoomName;
	}

	public void setRoomName(String roomName) {
		RoomName = roomName;
	}

	

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public String getHouseOwnerName() {
		return HouseOwnerName;
	}

	public void setHouseOwnerName(String houseOwnerName) {
		HouseOwnerName = houseOwnerName;
	}

	public String getHouseOwnerTel() {
		return HouseOwnerTel;
	}

	public void setHouseOwnerTel(String houseOwnerTel) {
		HouseOwnerTel = houseOwnerTel;
	}

	public String getHouseAddress() {
		return HouseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		HouseAddress = houseAddress;
	}

	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
	
}
