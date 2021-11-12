package com.forestry.dao.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.dao.sys.ForestryDao;
import com.forestry.dao.sys.RoomDao;
import com.forestry.model.sys.Forestry;
import com.forestry.model.sys.Room;

import core.dao.BaseDao;
import core.util.QRCodeUtil;

/**
 * @author 郑为中
 */
@Repository
public class RoomDaoImpl extends BaseDao<Room> implements RoomDao {

	public RoomDaoImpl() {
		super(Room.class);
	}
	
	@Override
	public List<Object[]> queryExportedRoom(Long[] ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			sb.append(ids[i] + ",");
		}
		Query query = getSession().createSQLQuery(
				"select h.departmentName,h.houseName,r.roomName ,r.roomArea from house h,room r where h.id = r.houseId and r.id in (" + sb.deleteCharAt(sb.toString().length() - 1).toString() + ")");
		return query.list();
	}
	@Override
	public int saveRoomTwoDimensionalCode(HttpServletRequest request, String filePath,Long id){
		
		String[] conditionName= {"id"};
		Long[] conditionValue= {id}; 
		String[] propertyName= {"houseTwoDimensionalCode"};
		String[] propertyValue= {"house_"+String.valueOf(id)+".png"};
		// 存放在二维码中的请求内容
		//ArrayList<String> contents=new ArrayList<String>();
		//contents.add("id:1111");
		//contents.add("right:1111");
		//contents.add("ruquestURL:"+request.getRequestURL()+"/sys/house/getRoomByTwoDimensionalCode");
		String text = request.getRequestURL()+"/sys/house/getRoomByTwoDimensionalCode?id="+String.valueOf(id);
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
	public int CheckRoom(Long[] ids,String checkOption){
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
	public void updateRoom(Room house) {
		updateQmm(house);
	}

}
