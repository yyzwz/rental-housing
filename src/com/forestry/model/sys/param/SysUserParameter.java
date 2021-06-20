package com.forestry.model.sys.param;

import javax.persistence.Column;

import core.extjs.ExtJSBaseParameter;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class SysUserParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 7656443663108619135L;
	private String $like_userName;
	private String $like_realName;
	private Short $eq_role;
	private Long $eq_departmentId;
	private String roleName;

	private Long departmentId; // 部门ID
	private String departmentName; //部门名
	
	
	
	

	public Long get$eq_departmentId() {
		return $eq_departmentId;
	}

	public void set$eq_departmentId(Long $eq_departmentId) {
		this.$eq_departmentId = $eq_departmentId;
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

	public String get$like_userName() {
		return $like_userName;
	}

	public void set$like_userName(String $like_userName) {
		this.$like_userName = $like_userName;
	}

	public String get$like_realName() {
		return $like_realName;
	}

	public void set$like_realName(String $like_realName) {
		this.$like_realName = $like_realName;
	}

	public Short get$eq_role() {
		return $eq_role;
	}

	public void set$eq_role(Short $eq_role) {
		this.$eq_role = $eq_role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
