package com.forestry.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.dao.sys.ForestryDao;
import com.forestry.dao.sys.RoomToTenantDao;
import com.forestry.model.sys.Forestry;
import com.forestry.model.sys.RoomToTenant;

import core.dao.BaseDao;
import core.util.QRCodeUtil;

/**
 * @author 郑为中
 */
@Repository
public class RoomToTenantDaoImpl extends BaseDao<RoomToTenant> implements RoomToTenantDao {

	public RoomToTenantDaoImpl() {
		super(RoomToTenant.class);
	}
	
	@Override
	public List<Object[]> queryExportedRoomToTenant(Long[] ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i] + ",");
		}
		Query query = getSession().createSQLQuery(
				"select DISTINCT h.departmentName,h.houseName,t.tenantIdentify,t.tenantTel,t.tenantFromShen,t.tenantFromShi,t.tenantFromXian,t.tenantWorkOrganization,rt.* from houseowner hw ,room r ,house h,room_tenant rt ,tenant t where t.id=rt.tenantId and rt.roomId=r.id and r.houseId=h.id and rt.id in (" + sb.deleteCharAt(sb.toString().length() - 1).toString() + ")");
		return query.list();
	}
	
	@Override
	public int CheckRoomToTenant(Long[] ids,String checkOption){
		for (int i = 0; i < ids.length; i++) {
			String[] conditionName= {"id"};
			Long[] conditionValue= {Long.valueOf(ids[i])}; 
			String[] propertyName= {"checkOpion"};
			String[] propertyValue= {checkOption};
			updateByProperties(conditionName,conditionValue,propertyName,propertyValue);
		}
		return 1;

	}
	@Override
	public void updateRoomToTenant(RoomToTenant house) {
		updateQmm(house);
	}

}
