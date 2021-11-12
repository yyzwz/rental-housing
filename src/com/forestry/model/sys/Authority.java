package com.forestry.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.forestry.model.sys.param.AuthorityParameter;
import com.google.common.base.Objects;

/**
 * @author 郑为中
 *  * @权限管理实体类
 */
@Entity
@Table(name = "authority")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority extends AuthorityParameter {

	private static final long serialVersionUID = -5233663741711528284L;
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id; // ID
	@Column(name = "sort_order", nullable = false)
	private Integer sortOrder; //排序
	@Column(name = "menu_code", length = 50, nullable = false)
	private String menuCode; // 菜单代号
	@Column(name = "menu_name", length = 50, nullable = false)
	private String menuName; // 菜单名称
	@Column(name = "menu_config", length = 200)
	private String menuConfig; //菜单配置参数
	@Column(name = "buttons", length = 50)
	private String buttons; // 按钮显示项
	@Column(name = "expanded", columnDefinition = "int default 0 not null")
	private Boolean expanded; //可伸展的
	@Column(name = "checked", columnDefinition = "int")
	private Boolean checked; //可勾选的
	@Column(name = "leaf", columnDefinition = "int default 0 not null")
	private Boolean leaf; //是否是叶子项
	@Column(name = "url", length = 100)
	private String url; //创建Tab的路径
	@Column(name = "icon_cls", length = 20)
	private String iconCls; //按钮样式
	@Column(name = "parent_id")
	private Long parentId; //父节点ID

	@Column(name = "descInfo", length = 50)
	private String descInfo; // 菜单项说明
	
	
	
	public String getDescInfo() {
		return descInfo;
	}

	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
	}

	public Authority() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuConfig() {
		return menuConfig;
	}

	public void setMenuConfig(String menuConfig) {
		this.menuConfig = menuConfig;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Authority other = (Authority) obj;
		return Objects.equal(this.id, other.id) && Objects.equal(this.sortOrder, other.sortOrder) && Objects.equal(this.menuCode, other.menuCode) && Objects.equal(this.menuName, other.menuName)
				&& Objects.equal(this.menuConfig, other.menuConfig) && Objects.equal(this.buttons, other.buttons) && Objects.equal(this.expanded, other.expanded) && Objects.equal(this.checked, other.checked)
				&& Objects.equal(this.leaf, other.leaf) && Objects.equal(this.url, other.url) && Objects.equal(this.iconCls, other.iconCls) && Objects.equal(this.parentId, other.parentId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.sortOrder, this.menuCode, this.menuName, this.menuConfig, this.buttons, this.expanded, this.checked, this.leaf, this.url, this.iconCls, this.parentId);
	}

}
