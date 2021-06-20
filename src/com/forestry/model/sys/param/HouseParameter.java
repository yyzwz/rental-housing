package com.forestry.model.sys.param;

import java.sql.Date;

import javax.persistence.Column;

import core.extjs.ExtJSBaseParameter;

/**
 * @author 齐鸣鸣
 */
public class HouseParameter extends ExtJSBaseParameter {

	private static final long serialVersionUID = 6683458492945661067L;


	private String $like_houseName;
	private String $like_houseOwnerName;
	private Long $eq_houseTypeId;
	private Long $eq_departmentId;
	private String $eq_checkOpion;
	private Long $eq_houseOwnerId;
	private String $leDate_registDate;//<=于日期
	private String $geDate_registDate;//>=日期
	
	

	public String get$geDate_registDate() {
		return $geDate_registDate;
	}

	public void set$geDate_registDate(String $geDate_registDate) {
		this.$geDate_registDate = $geDate_registDate;
	}

	public String get$leDate_registDate() {
		return $leDate_registDate;
	}

	public void set$leDate_registDate(String $leDate_registDate) {
		this.$leDate_registDate = $leDate_registDate;
	}

	public Long get$eq_houseOwnerId() {
		return $eq_houseOwnerId;
	}

	public void set$eq_houseOwnerId(Long $eq_houseOwnerId) {
		this.$eq_houseOwnerId = $eq_houseOwnerId;
	}

	public String get$eq_checkOpion() {
		return $eq_checkOpion;
	}

	public void set$eq_checkOpion(String $eq_checkOpion) {
		this.$eq_checkOpion = $eq_checkOpion;
	}

	public String get$like_houseName() {
		return $like_houseName;
	}

	public void set$like_houseName(String $like_houseName) {
		this.$like_houseName = $like_houseName;
	}

	public String get$like_houseOwnerName() {
		return $like_houseOwnerName;
	}

	public void set$like_houseOwnerName(String $like_houseOwnerName) {
		this.$like_houseOwnerName = $like_houseOwnerName;
	}

	public Long get$eq_houseTypeId() {
		return $eq_houseTypeId;
	}

	public void set$eq_houseTypeId(Long $eq_houseTypeId) {
		this.$eq_houseTypeId = $eq_houseTypeId;
	}

	public Long get$eq_departmentId() {
		return $eq_departmentId;
	}

	public void set$eq_departmentId(Long $eq_departmentId) {
		this.$eq_departmentId = $eq_departmentId;
	}



}
