package com.forestry.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.RoleAuthorityParameter;
import com.google.common.base.Objects;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 * @角色权限实体类
 */
@Entity
@Table(name = "role_authority")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoleAuthority extends RoleAuthorityParameter {

	private static final long serialVersionUID = -6407426487164414994L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id; // ID
	@Column(name = "role", length = 1, nullable = false)
	private Short role; // 角色（被禁用：0，超级管理员：1，普通管理员：2，普通用户：3）
	@Column(name = "authority_id", length = 10, nullable = false)
	private String authorityId; // 权限菜单ID

	public RoleAuthority() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Short getRole() {
		return role;
	}

	public void setRole(Short role) {
		this.role = role;
	}

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RoleAuthority other = (RoleAuthority) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.role, other.role) && Objects.equal(this.authorityId, other.authorityId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.role, this.authorityId);
	}

}
