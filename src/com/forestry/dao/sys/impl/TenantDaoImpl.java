package com.forestry.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.dao.sys.ForestryDao;
import com.forestry.dao.sys.TenantDao;
import com.forestry.model.sys.Forestry;
import com.forestry.model.sys.Tenant;

import core.dao.BaseDao;
import core.util.QRCodeUtil;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class TenantDaoImpl extends BaseDao<Tenant> implements TenantDao {

	public TenantDaoImpl() {
		super(Tenant.class);
	}
	
	@Override
	public List<Object[]> queryExportedTenant(Long[] ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i] + ",");
		}
		Query query = getSession().createSQLQuery(
				"select ho.houseOwnerName, ho.houseOwnerIdentify,ho.houseOwnerTel,ho.houseOwnerAddress, h.departmentName,h.houseName,r.roomName,h.houseAddress,t.tenantName,t.tenantIdentify,t.tenantTel,t.tenantFromShen,t.tenantFromShi,t.tenantFromXian,t.tenantWorkOrganization,t.tenantDesc from tenant t,room_tenant rt ,room r ,house h,houseowner ho where h.houseOwnerId = ho.id and t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id and  t.id in (" + sb.deleteCharAt(sb.toString().length() - 1).toString() + ")");
		return query.list();
	}
	@Override
	public int saveTenantTwoDimensionalCode(HttpServletRequest request, String filePath,Long id){
		
		String[] conditionName= {"id"};
		Long[] conditionValue= {id}; 
		String[] propertyName= {"TenantTwoDimensionalCode"};
		String[] propertyValue= {"Tenant_"+String.valueOf(id)+".png"};
		// 存放在二维码中的请求内容
		//ArrayList<String> contents=new ArrayList<String>();
		//contents.add("id:1111");
		//contents.add("right:1111");
		//contents.add("ruquestURL:"+request.getRequestURL()+"/sys/Tenant/getTenantByTwoDimensionalCode");
		String text = request.getRequestURL()+"/sys/Tenant/getTenantByTwoDimensionalCode?id="+String.valueOf(id);
		// 嵌入二维码的图片路径
		String imgPath = filePath+"\\qmm2.jpg";
		// 生成的二维码的路径及名称
		String destPath = filePath+"\\Tenant_"+String.valueOf(id)+".png";
		//生成二维码
		try {
			QRCodeUtil.encode(text, imgPath, destPath, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			updateByProperties(conditionName,conditionValue,propertyName,propertyValue);
		}
		
		return 1;

	}
	@Override
	public int CheckTenant(Long[] ids,String checkOption){
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
	public void updateTenant(Tenant Tenant) {
		updateQmm(Tenant);
	}

}
