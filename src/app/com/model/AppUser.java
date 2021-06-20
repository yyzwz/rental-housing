package app.com.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import app.com.model.param.AppUserParameter;

/**
 * @author 郑为中
 * @房东实体类
 */
@Entity
@Table(name = "houseowner")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppUser extends AppUserParameter{
	
	private static final long serialVersionUID = 7034294844559970211L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "houseOwnerCode", length = 30)
	private String houseOwnerCode; // 用户名

	@Column(name = "houseOwnerPassWord", length = 32)
	private String houseOwnerPassWord; // 用户密码
	
	@Column(name = "houseOwnerName", length = 30)
	private String houseOwnerName; // 房东姓名
	
	@Column(name = "houseOwnerIdentify", length = 30)
	private String houseOwnerIdentify; // 房东身份证
	
	@Column(name = "houseOwnerTel", length = 200)
	private String houseOwnerTel; // 用户电话号码
	
	@Column(name = "houseOwnerDesc", length = 200)
	private String houseOwnerDesc; // 用户描述
	
	@Column(name = "houseOwnerAddress", length = 200)
	private String houseOwnerAddress; // 用户住址
	
	@Column(name = "houseOwnerRole", length = 200)
	private int houseOwnerRole; // 用户角色
	
	@Column(name = "houseOwnerLastLoginTime", length = 200)
	private Date houseOwnerLastLoginTime; //最后登入日期
	
	@Column(name = "registDate", length = 200)
	private Date registDate; // 注册日期

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getHouseOwnerAddress() {
		return houseOwnerAddress;
	}

	public void setHouseOwnerAddress(String houseOwnerAddress) {
		this.houseOwnerAddress = houseOwnerAddress;
	}

	public int getHouseOwnerRole() {
		return houseOwnerRole;
	}

	public void setHouseOwnerRole(int houseOwnerRole) {
		this.houseOwnerRole = houseOwnerRole;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
