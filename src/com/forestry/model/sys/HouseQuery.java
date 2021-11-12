package com.forestry.model.sys;

import java.math.BigInteger;
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

import com.forestry.model.sys.param.HouseOwnerParameter;
import com.google.common.base.Objects;

/**
 * @author 郑为中
 * @房主类型查询实体类
 */

public class HouseQuery extends HouseOwnerParameter {

	private static final long serialVersionUID = 6683458492945661067L;

	private BigInteger id;
	
	

	
	public BigInteger getId() {
		return id;
	}




	public void setId(BigInteger id) {
		this.id = id;
	}




	public HouseQuery() {

	}



	


}
