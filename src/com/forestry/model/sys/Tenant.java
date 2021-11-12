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

import com.forestry.model.sys.param.TenantParameter;
import com.google.common.base.Objects;

/**
 * @author 郑为中
 * @房屋类型实体类
 */
@Entity
@Table(name = "tenant")
@DynamicInsert(true) 
@DynamicUpdate(true) 
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tenant extends TenantParameter {

	private static final long serialVersionUID = 6683458492945661067L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "tenantName", length = 50)
	private String tenantName; //姓名
	@Column(name = "tenantIdentify", length = 200)
	private String tenantIdentify; //身份证号
	@Column(name = "tenantTel", length = 200)
	private String tenantTel; //电话
	@Column(name = "tenantFromShen", length = 200)
	private String tenantFromShen; //来源省
	@Column(name = "tenantFromShi", length = 200)
	private String tenantFromShi; //来源市
	@Column(name = "tenantFromXian", length = 200)
	private String tenantFromXian; //来源县
	@Column(name = "tenantWorkOrganization", length = 200)
	private String tenantWorkOrganization; // 工作单位
	@Column(name = "tenantDesc", length = 200)
	private String tenantDesc; // 描述信息
	@Column(name = "tenantImage", length = 200)
	private String tenantImage; // 照片
	@Column(name = "checkOpion", length = 200)
	private String checkOpion; // 注册日期
	@Column(name = "checkDate", length = 200)
	private Date checkDate; // 审核日期
	@Column(name = "checkerId", length = 200)
	private Long checkerId; // 审核者ID
	@Column(name = "checkerName", length = 200)
	private String checkerName; //审核姓名
	
	



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setTenantFromXian(String tenantFromXian) {
		this.tenantFromXian = tenantFromXian;
	}

	public String getTenantWorkOrganization() {
		return tenantWorkOrganization;
	}

	public void setTenantWorkOrganization(String tenantWorkOrganization) {
		this.tenantWorkOrganization = tenantWorkOrganization;
	}

	public String getTenantDesc() {
		return tenantDesc;
	}

	public void setTenantDesc(String tenantDesc) {
		this.tenantDesc = tenantDesc;
	}

	public String getTenantImage() {
		return tenantImage;
	}

	public void setTenantImage(String tenantImage) {
		this.tenantImage = tenantImage;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Tenant other = (Tenant) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.tenantIdentify, other.tenantIdentify) && Objects.equal(this.tenantName, other.tenantName);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.tenantName, this.tenantIdentify);
	}

}
