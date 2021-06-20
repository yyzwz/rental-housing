package app.com.dao.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import app.com.dao.AppHouseDao;
import app.com.model.AppHouse;
import core.dao.BaseDao;
import core.util.QRCodeUtil;

/**
 * @author 郑为中
 */

@Repository
public class AppHouseDaoImpl extends BaseDao<AppHouse> implements AppHouseDao {

	public AppHouseDaoImpl() {
		super(AppHouse.class);
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
		
		// 嵌入二维码的图片路径
		String imgPath = filePath+"\\qmm2.jpg";
		// 生成的二维码的路径及名称
		String destPath = filePath+"\\static\\img\\houseerweima\\house_"+String.valueOf(id)+".png";
		//生成二维码
		try {
			QRCodeUtil.encode(text, imgPath, destPath, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// updateByProperties(conditionName,conditionValue,propertyName,propertyValue);
		}
		
		return 1;

	}
	
	public int saveHouseTwoDimensionalCode1(HttpServletRequest request, String filePath,Long id){
		
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
		
		// 嵌入二维码的图片路径
		String imgPath = filePath+"\\qmm2.jpg";
		// 生成的二维码的路径及名称
		String destPath = filePath+"C:\\Tomcat8.5\\webapps\\forestry\\static\\img\\houseerweima\\house_"+String.valueOf(id)+".png";
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
	
	public static void erweima(Long id) {
//		String[] conditionName= {"id"};
//		Long[] conditionValue= {id}; 
//		String[] propertyName= {"houseTwoDimensionalCode"};
//		String[] propertyValue= {"house_"+String.valueOf(id)+".png"};
//		// 存放在二维码中的请求内容
//		//ArrayList<String> contents=new ArrayList<String>();
//		//contents.add("id:1111");
//		//contents.add("right:1111");
//		//contents.add("ruquestURL:"+request.getRequestURL()+"/sys/house/getHouseByTwoDimensionalCode");
//		String text = "https://ypcqmm.net?id=" + id;//+String.valueOf(id);
//		String filePath = "";
//		// 嵌入二维码的图片路径
//		String imgPath = filePath+"\\qmm2.jpg";
//		// 生成的二维码的路径及名称
//		String destPath = "C:\\Tomcat8.5\\webapps\\forestry\\static\\img\\houseerweima\\house_"+String.valueOf(id)+".png";
//		//生成二维码
//		try {
//			QRCodeUtil.encode(text, imgPath, destPath, true);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			updateByProperties(conditionName,conditionValue,propertyName,propertyValue);
//		}
	}
	public static void main(String[] args) {
		
	}
}
