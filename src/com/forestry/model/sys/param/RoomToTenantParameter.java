package com.forestry.model.sys.param;

import java.sql.Date;

import javax.persistence.Column;

import core.extjs.ExtJSBaseParameter;

/**
 * @author 齐鸣鸣
 */
public class RoomToTenantParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 6683458492945661067L;
	
	private String tenantIdentify; // 租客身份证号
	private String tenantTel; // 租客身份证号
	private String tenantFromShen; // 租客身份证号
	private String tenantFromShi; // 租客身份证号
	private String tenantFromXian; // 租客身份证号
	private String tenantWorkOrganization; // 租客身份证号
	private String tenantDesc; // 租客身份证号
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
	
	
	
}
