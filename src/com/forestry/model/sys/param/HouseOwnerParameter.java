package com.forestry.model.sys.param;

import java.sql.Date;

import javax.persistence.Column;

import core.extjs.ExtJSBaseParameter;

/**
 * @author 齐鸣鸣
 */
public class HouseOwnerParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 6683458492945661067L;
	private String $like_houseOwnerName;
	private String $like_houseOwnerIdentify;
	private String $like_houseOwnerTel;
	public String get$like_houseOwnerName() {
		return $like_houseOwnerName;
	}
	public void set$like_houseOwnerName(String $like_houseOwnerName) {
		this.$like_houseOwnerName = $like_houseOwnerName;
	}
	public String get$like_houseOwnerIdentify() {
		return $like_houseOwnerIdentify;
	}
	public void set$like_houseOwnerIdentify(String $like_houseOwnerIdentify) {
		this.$like_houseOwnerIdentify = $like_houseOwnerIdentify;
	}
	public String get$like_houseOwnerTel() {
		return $like_houseOwnerTel;
	}
	public void set$like_houseOwnerTel(String $like_houseOwnerTel) {
		this.$like_houseOwnerTel = $like_houseOwnerTel;
	}
	
	
}
