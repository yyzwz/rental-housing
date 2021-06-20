package com.forestry.model.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.google.common.base.Objects;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.SysUserParameter;

import core.extjs.DateTimeSerializer;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 * @管理员信息实体类
 */
@Entity
@Table(name = "sys_user")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysUser extends SysUserParameter {

	private static final long serialVersionUID = 822330369002149147L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id; // ID
	@Column(name = "user_name", length = 30, nullable = false, unique = true)
	private String userName; // 用户名
	@Column(name = "password", length = 32, nullable = false)
	private String password; // 密码
	@Column(name = "real_name", length = 30, nullable = true)
	private String realName; // 姓名
	@Column(name = "tel", length = 15, nullable = true)
	private String tel; // 手机号
	@Column(name = "email", length = 30, nullable = true)
	private String email; // 邮箱
	@Column(name = "last_logintime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime; // 最后登录时间
	@Column(name = "role", nullable = false)
	private Short role; // 角色（被禁用：0，超级管理员：1，普通管理员：2，普通用户：3）

	@Column(name = "departmentId", nullable = false)
	private Long departmentId; // 部门ID
	@Column(name = "departmentName", length = 50, nullable = true)
	private String departmentName; //部门名
	
	@Column(name = "userGrade", length = 50, nullable = true)
	private String userGrade; //等级
	
	

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public SysUser() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Short getRole() {
		return role;
	}

	public void setRole(Short role) {
		this.role = role;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SysUser other = (SysUser) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.userName, other.userName) && Objects.equal(this.password, other.password) && Objects.equal(this.realName, other.realName)
				&& Objects.equal(this.tel, other.tel) && Objects.equal(this.email, other.email) && Objects.equal(this.lastLoginTime, other.lastLoginTime) && Objects.equal(this.role, other.role);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.userName, this.password, this.realName, this.tel, this.email, this.lastLoginTime, this.role);
	}

}