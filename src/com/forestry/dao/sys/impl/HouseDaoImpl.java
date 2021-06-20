package com.forestry.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.dao.sys.ForestryDao;
import com.forestry.dao.sys.HouseDao;
import com.forestry.model.sys.Forestry;
import com.forestry.model.sys.House;
import com.forestry.model.sys.SysUser;

import core.dao.BaseDao;
import core.util.QRCodeUtil;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
@Repository
public class HouseDaoImpl extends BaseDao<House> implements HouseDao {

	public HouseDaoImpl() {
		super(House.class);
	}
	
	@Override
	public List<Object[]> queryExportedHouse(Long[] ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i] + ",");
		}
		Query query = getSession().createSQLQuery(
				"select h.departmentName,h.houseTypeName,h.houseName,h.houseOwnerName,h.houseAddress,h.registDate,h.houseDesc,h.checkOpion from house h where h.id in (" + sb.deleteCharAt(sb.toString().length() - 1).toString() + ")");
		return query.list();
	}
	@Override
	public int saveHouseTwoDimensionalCode(HttpServletRequest request, String filePath,Long id){
		
		String[] conditionName= {"id"};
		Long[] conditionValue= {id}; 
		String[] propertyName= {"houseTwoDimensionalCode"};
		String[] propertyValue= {"house_"+String.valueOf(id)+".png"};
		// 存放在二维码中的请求内容
		//ArrayList<String> contents=new ArrayList<String>();
		//contents.add("id:1111");
		//contents.add("right:1111");
		//contents.add("ruquestURL:"+request.getRequestURL()+"/sys/house/getHouseByTwoDimensionalCode");
		String text = "https://ypcqmm.net?id=" + id;//+String.valueOf(id);
		// String text = request.getRequestURL()+"/sys/house/getHouseByTwoDimensionalCode?id="+String.valueOf(id);
		// 嵌入二维码的图片路径
		String imgPath = filePath+"\\qmm2.jpg";
		// 生成的二维码的路径及名称
		String destPath = filePath+"\\house_"+String.valueOf(id)+".png";
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
	public void updateHouse(House house) {
		updateQmm(house);
	}

}
