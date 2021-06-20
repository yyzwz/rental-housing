package app.com.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import app.com.model.param.AppTenantParameter;

/**
 * @author 郑为中
 * @租客实体类(小程序分页面2用)
 */

@Entity
@Table(name = "tenant")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppTenant extends AppTenantParameter{
	
	private static final long serialVersionUID = 7034294844559070211L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "tenantName", length = 30)
	private String TenantName; // 姓名
	
	@Column(name = "tenantIdentify", length = 30)
	private String TenantIdentify; // 身份证号
	
	@Column(name = "tenantTel", length = 15)
	private String TenantTel; // 电话
	
	@Column(name = "tenantFromShen", length = 200)
	private String TenantFromShen; // 户籍
	
	@Column(name = "tenantFromShi", length = 200)
	private String TenantFromShi; // 户籍
	
	@Column(name = "tenantFromXian", length = 200)
	private String TenantFromXian; // 户籍
	
	@Column(name = "tenantWorkOrganization", length = 255)
	private String TenantWorkOrganization; // 工作单位
	
	@Column(name = "tenantDesc", length = 255)
	private String TenantDesc; // 租客描述

	@Column(name = "tenantImage", length = 255)
	private String tenantImage; // 照片
	
	@Column(name = "tenantCode", length = 255)
	private String HouseOwnerCode; // 房东账号
	
	@Column(name = "checkOpion", length = 50)
	private String checkOpion; // 是否审核

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((HouseOwnerCode == null) ? 0 : HouseOwnerCode.hashCode());
		result = prime * result + ((TenantDesc == null) ? 0 : TenantDesc.hashCode());
		result = prime * result + ((TenantFromShen == null) ? 0 : TenantFromShen.hashCode());
		result = prime * result + ((TenantFromShi == null) ? 0 : TenantFromShi.hashCode());
		result = prime * result + ((TenantFromXian == null) ? 0 : TenantFromXian.hashCode());
		result = prime * result + ((TenantIdentify == null) ? 0 : TenantIdentify.hashCode());
		result = prime * result + ((TenantName == null) ? 0 : TenantName.hashCode());
		result = prime * result + ((TenantTel == null) ? 0 : TenantTel.hashCode());
		result = prime * result + ((TenantWorkOrganization == null) ? 0 : TenantWorkOrganization.hashCode());
		result = prime * result + ((checkOpion == null) ? 0 : checkOpion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tenantImage == null) ? 0 : tenantImage.hashCode());
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
		AppTenant other = (AppTenant) obj;
		if (HouseOwnerCode == null) {
			if (other.HouseOwnerCode != null)
				return false;
		} else if (!HouseOwnerCode.equals(other.HouseOwnerCode))
			return false;
		if (TenantDesc == null) {
			if (other.TenantDesc != null)
				return false;
		} else if (!TenantDesc.equals(other.TenantDesc))
			return false;
		if (TenantFromShen == null) {
			if (other.TenantFromShen != null)
				return false;
		} else if (!TenantFromShen.equals(other.TenantFromShen))
			return false;
		if (TenantFromShi == null) {
			if (other.TenantFromShi != null)
				return false;
		} else if (!TenantFromShi.equals(other.TenantFromShi))
			return false;
		if (TenantFromXian == null) {
			if (other.TenantFromXian != null)
				return false;
		} else if (!TenantFromXian.equals(other.TenantFromXian))
			return false;
		if (TenantIdentify == null) {
			if (other.TenantIdentify != null)
				return false;
		} else if (!TenantIdentify.equals(other.TenantIdentify))
			return false;
		if (TenantName == null) {
			if (other.TenantName != null)
				return false;
		} else if (!TenantName.equals(other.TenantName))
			return false;
		if (TenantTel == null) {
			if (other.TenantTel != null)
				return false;
		} else if (!TenantTel.equals(other.TenantTel))
			return false;
		if (TenantWorkOrganization == null) {
			if (other.TenantWorkOrganization != null)
				return false;
		} else if (!TenantWorkOrganization.equals(other.TenantWorkOrganization))
			return false;
		if (checkOpion == null) {
			if (other.checkOpion != null)
				return false;
		} else if (!checkOpion.equals(other.checkOpion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tenantImage == null) {
			if (other.tenantImage != null)
				return false;
		} else if (!tenantImage.equals(other.tenantImage))
			return false;
		return true;
	}
	
	
	
}
