package app.com.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import app.com.model.param.AppHouseParameter;

/**
 * @author 郑为中
 * @房屋实体类
 */
@Entity
@Table(name = "house")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppHouse extends AppHouseParameter{

	private static final long serialVersionUID = 9034294844559970211L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "houseTypeId", length = 200)
	private Long houseTypeId; // 房屋类型ID
	
	@Column(name = "houseTypeName", length = 200)
	private String houseTypeName; // 房屋类型名
	
	@Column(name = "houseAddress", length = 200)
	private String houseAddress; // 房屋地址
	
	@Column(name = "manageDepartmentId", length = 200)
	private Long manageDepartmentId; // 管理部门ID
	
	@Column(name = "manageDepartmentName", length = 200)
	private String manageDepartmentName; // 管理部门名
	
	@Column(name = "houseName", length = 200)
	private String houseName; // 房屋名字

	@Column(name = "houseDesc", length = 255)
	private String houseDesc; // 房屋描述信息
	
	@Column(name = "houseMapLocation", length = 255)
	private String houseMapLocation; // 房屋定位
	
	@Column(name = "houseTwoDimensionalCode", length = 250)
	private String houseTwoDimensionalCode; // 房屋二维码

	@Column(name = "houseImage", length = 250)
	private String houseImage; // 房屋照片
	
	@Column(name = "houseOwnerId", length = 20)
	private Long houseOwnerId; // 房东ID
	
	@Column(name = "houseOwnerName", length = 200)
	private String houseOwnerName; // 房东姓名
	
	@Column(name = "registDate", length = 200)
	private Date registDate; // 注册时间
	
	@Column(name = "departmentId", length = 20)
	private Long departmentId; // 所属部门ID
	
	@Column(name = "departmentName", length = 50)
	private String departmentName; // 所属部门名
	
	@Column(name = "checkOpion", length = 50)
	private String checkOpion; // 是否审核
	
	@Column(name = "checkerId", length = 50)
	private Long checkerId; // 是否审核
	
	@Column(name = "checkerName", length = 50)
	private String checkerName; // 是否审核
	
	@Column(name = "checkDate", length = 50)
	private Date checkDate; // 是否审核

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(Long houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public String getHouseTypeName() {
		return houseTypeName;
	}

	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public Long getManageDepartmentId() {
		return manageDepartmentId;
	}

	public void setManageDepartmentId(Long manageDepartmentId) {
		this.manageDepartmentId = manageDepartmentId;
	}

	public String getManageDepartmentName() {
		return manageDepartmentName;
	}

	public void setManageDepartmentName(String manageDepartmentName) {
		this.manageDepartmentName = manageDepartmentName;
	}

	public String getHouseName() {
		return houseName;
	}

	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

	public String getHouseDesc() {
		return houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	public String getHouseMapLocation() {
		return houseMapLocation;
	}

	public void setHouseMapLocation(String houseMapLocation) {
		this.houseMapLocation = houseMapLocation;
	}

	public String getHouseTwoDimensionalCode() {
		return houseTwoDimensionalCode;
	}

	public void setHouseTwoDimensionalCode(String houseTwoDimensionalCode) {
		this.houseTwoDimensionalCode = houseTwoDimensionalCode;
	}

	public String getHouseImage() {
		return houseImage;
	}

	public void setHouseImage(String houseImage) {
		this.houseImage = houseImage;
	}

	public Long getHouseOwnerId() {
		return houseOwnerId;
	}

	public void setHouseOwnerId(Long houseOwnerId) {
		this.houseOwnerId = houseOwnerId;
	}

	public String getHouseOwnerName() {
		return houseOwnerName;
	}

	public void setHouseOwnerName(String houseOwnerName) {
		this.houseOwnerName = houseOwnerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
