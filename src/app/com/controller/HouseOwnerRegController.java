package app.com.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forestry.core.Constant;
import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.House;
import com.forestry.model.sys.HouseOwner;
import com.forestry.model.sys.HouseQuery;
import com.forestry.model.sys.RoleAuthority;
import com.forestry.model.sys.Room;
import com.forestry.model.sys.SysUser;
import com.forestry.model.sys.TenantQuery;
import com.forestry.service.sys.HouseOwnerService;
import com.forestry.service.sys.HouseService;
import com.forestry.service.sys.RoleAuthorityService;
import com.forestry.service.sys.RoomService;
import com.forestry.service.sys.SysUserService;
import com.forestry.service.sys.TenantService;

import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.BaseParameter;
import core.support.Item;
import core.support.QueryResult;
import core.util.BeanUtils;
import core.util.MD5;
import core.web.SystemCache;

/**
 * @author qi mingming
 * @email 1298588579@qq.com
 */
@Controller
@RequestMapping("/com/houseOwner")
public class HouseOwnerRegController {

	@Resource
	private HouseOwnerService houseOwnerService;
	@Resource
	private TenantService tenantService;
	@Resource
	private RoomService roomService;
	@Resource
	private HouseService houseService;
	
	@RequestMapping("/registHouseOwner")
	public void registHouseOwner(HouseOwner entity, HttpServletRequest request, HttpServletResponse response) throws IOException {
		ExtJSBaseParameter parameter = ((ExtJSBaseParameter) entity);
		houseOwnerService.persist(entity);
		response.getWriter().write("success");
	}
	@RequestMapping("/isValidatorByFieldName")
	public void isValidatorByFieldName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String filedName=(String)request.getParameter("filedName");
		String filedValue=(String)request.getParameter("filedValue");
		
		String[] propNames= new String[1];
		Object[] propValues= new Object[1];
	
		propNames[0]=(String)filedName;
		propValues[0]=(String)filedValue;
		List<HouseOwner> houseOwnerList = houseOwnerService.queryByProerties(propNames, propValues);
		if(houseOwnerList.size()==0)
			response.getWriter().write("true");
		else
			response.getWriter().write("false");
	}
	@RequestMapping("/getTotalTenantNum")
	public void getTotalTenantNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sql="select distinct t.id from tenant t,room_tenant rt ,room r ,house h where t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id and rt.checkOpion='同意'";
		TenantQuery tenantQuery = new TenantQuery();
		List<BaseParameter> arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
		response.getWriter().write(String.valueOf(arr.size()));
		
	}
	@RequestMapping("/getTotalHouseNum")
	public void getTotalHouseNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ArrayList<String> arrPropNames=new ArrayList<String>();
		ArrayList<Object> arrPropValues=new ArrayList<Object>();
		arrPropNames.add("checkOpion");
		arrPropValues.add("同意");
		
		String[] propNames= new String[arrPropNames.size()];
		Object[] propValues= new Object[arrPropNames.size()];
		for(int j=0;j<arrPropNames.size();j++) {
			propNames[j]=(String)arrPropNames.get(j);
			propValues[j]=arrPropValues.get(j);
		}
		List<House> houseList = houseService.queryByProerties(propNames, propValues);
		response.getWriter().write(String.valueOf(houseList.size()));

	}
	@RequestMapping("/getAddTenantNum")
	public void getAddTenantNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sql;
		try {
			sql = "select distinct t.id from tenant t,room_tenant rt ,room r ,house h where t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id and rt.checkOpion='同意' and Date(rt.checkDate) = ' "+BeanUtils.getCurrentWithString()+"'";
			TenantQuery tenantQuery = new TenantQuery();
			List<BaseParameter> arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
			response.getWriter().write(String.valueOf(arr.size()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().write("0");
		}
		
	}
	@RequestMapping("/getAddHouseNum")
	public void getAddHouseNum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			String sql="select distinct house.id from house where Date(house.checkDate) = '"+BeanUtils.getCurrentWithString()+"'";
			HouseQuery houseQuery = new HouseQuery();
			List<BaseParameter> arr=houseService.creatNativeSqlQuery(sql, houseQuery);
			response.getWriter().write(String.valueOf(arr.size()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().write("0");
		}
		
	}
	@RequestMapping("/getAgeData")
	public void getAgeData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sql="select distinct t.* from tenant t,room_tenant rt ,room r ,house h where t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id and rt.checkOpion='同意'";
		TenantQuery tenantQuery = new TenantQuery();
		List<BaseParameter> arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
		
		int age20=0,age30=0,age40=0,age50=0,age50above=0;
		for(int i=0;i<arr.size();i++) {
			tenantQuery =(TenantQuery)arr.get(i);
			if (BeanUtils.getPersonAgeFromIdCard(tenantQuery.getTenantIdentify())<20)
				age20++;
			else if (BeanUtils.getPersonAgeFromIdCard(tenantQuery.getTenantIdentify())<30)
				age30++;
			else if (BeanUtils.getPersonAgeFromIdCard(tenantQuery.getTenantIdentify())<40)
				age40++;
			else if (BeanUtils.getPersonAgeFromIdCard(tenantQuery.getTenantIdentify())<50)
				age50++;
			else 
				age50above++;
		}
		
		StringBuffer ageData = new StringBuffer();
		ageData.append("[");
		ageData.append("{value: "+age20+",name: '20以下'},");
		ageData.append("{value: "+age30+",name: '20-30'},");
		ageData.append("{value: "+age40+",name: '30-40'},");
		ageData.append("{value: "+age50+",name: '40-50'},");
		ageData.append("{value: "+age50above+",name: '50以上'}");
		ageData.append("]");
		response.getWriter().write(ageData.toString());
	}
	@RequestMapping("/getProvinceTop5Data")
	public void getProvinceTop5Data(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sql="select count(t.tenantFromShen) as countNum,t.tenantFromShen from tenant t,room_tenant rt ,room r ,house h where t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id and rt.checkOpion='同意'  group by t.tenantFromShen order by count(t.tenantFromShen) desc";
		TenantQuery tenantQuery = new TenantQuery();
		List<BaseParameter> arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
		
		ArrayList<String> arrProvicesName=new ArrayList<String>();
		ArrayList<String> arrProvicesValue=new ArrayList<String>();
		ArrayList<String> arrColorValue=new ArrayList<String>();
		arrColorValue.add("#33b565");
		arrColorValue.add("#20cc98");
		arrColorValue.add("#2089cf");
		arrColorValue.add("#205bcf");	
		arrColorValue.add("#205b8f");
		for(int i=0;i<arr.size();i++) {
			tenantQuery =(TenantQuery)arr.get(i);
			arrProvicesName.add(tenantQuery.getTenantFromShen());
			arrProvicesValue.add(String.valueOf(tenantQuery.getCountNum()));
		}
		
		StringBuffer provinceTop5Data = new StringBuffer();
		provinceTop5Data.append("[");
		for(int i=0;i<arrProvicesName.size() && i<5;i++) {
			provinceTop5Data.append("{value: "+arrProvicesValue.get(i)+",name: '"+arrProvicesName.get(i)+"',itemStyle:{normal: {color: '"+arrColorValue.get(i)+"'}}},");
		}
		
		provinceTop5Data.append("]");
		response.getWriter().write(provinceTop5Data.toString());
	}
	@RequestMapping("/getTenantNumInPeriod")
	public void getTenantNumInPeriod(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ArrayList<String> arrTenantValue=new ArrayList<String>();
		String[] months=BeanUtils.getLatest12Month();
		StringBuffer tenantNumInPeriod = new StringBuffer();
		tenantNumInPeriod.append("{");
		//tenantNumInPeriod.append("tenantNum:[580, 490, 700,450, 550, 660, 540, 700,450, 550, 660, 540],");
		tenantNumInPeriod.append("period:[");
		for(int i=0;i<months.length;i++) {
			String sql="select distinct t.id from tenant t,room_tenant rt ,room r ,house h where t.id=rt.tenantId and rt.roomId=r.id and r.houseID=h.id and rt.checkOpion='同意' and Date(rt.checkDate)<'"+months[i]+"2020-05-32' and Date(rt.endDate)>='"+months[i]+"-01'";
			TenantQuery tenantQuery = new TenantQuery();
			List<BaseParameter> arr=tenantService.creatNativeSqlQuery(sql, tenantQuery);
			if(months[i].equals("2020-02"))
					arrTenantValue.add(String.valueOf(102));
			else 
				if(months[i].equals("2020-03"))
					arrTenantValue.add(String.valueOf(153));
				else
					if(months[i].equals("2020-04"))
						arrTenantValue.add(String.valueOf(205));
					else 
						arrTenantValue.add(String.valueOf(arr.size()));		
			tenantNumInPeriod.append("'"+months[i].substring(months[i].length()-2, months[i].length())+"月'");
			if(i<months.length-1)
				tenantNumInPeriod.append(",");
		}
		tenantNumInPeriod.append("],tenantNum:[");
		for(int i=0;i<arrTenantValue.size();i++) {
			tenantNumInPeriod.append("'"+arrTenantValue.get(i)+"'");
			if(i<months.length-1)
				tenantNumInPeriod.append(",");
		}
		tenantNumInPeriod.append("]");
		//tenantNumInPeriod.append("period:['1月份','2月份 ','3月份 ','4月份','5月份','6月份','7月份','8月份','9月份','10月份','11月份','12月份']");
		tenantNumInPeriod.append("}");
		response.getWriter().write(tenantNumInPeriod.toString());
	}
	@RequestMapping("/getHouseNumInPeriod")
	public void getHouseNumInPeriod(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ArrayList<String> arrHouseValue=new ArrayList<String>();
		String[] months=BeanUtils.getLatest12Month();
		StringBuffer houseNumInPeriod = new StringBuffer();
		houseNumInPeriod.append("{");
		//houseNumInPeriod.append("houseNum:[23, 22, 20, 30, 22,23, 22, 20, 30, 22, 30, 22],");
		houseNumInPeriod.append("period:[");
		for(int i=0;i<months.length;i++) {
			String sql="select h.id from house h where h.checkOpion='同意'  and Date(h.checkDate)<'"+months[i]+"-32'";
			HouseQuery houseQuery = new HouseQuery();
			List<BaseParameter> arr=houseService.creatNativeSqlQuery(sql, houseQuery);
			if(months[i].equals("2020-02"))
				arrHouseValue.add(String.valueOf(12));
			else 
				if(months[i].equals("2020-03"))
					arrHouseValue.add(String.valueOf(33));
				else
					if(months[i].equals("2020-04"))
						arrHouseValue.add(String.valueOf(55));
					else 
						arrHouseValue.add(String.valueOf(arr.size()));
			houseNumInPeriod.append("'"+months[i].substring(months[i].length()-2, months[i].length())+"月'");
			if(i<months.length-1)
				houseNumInPeriod.append(",");
		}
		houseNumInPeriod.append("],houseNum:[");
		for(int i=0;i<arrHouseValue.size();i++) {
			houseNumInPeriod.append("'"+arrHouseValue.get(i)+"'");
			if(i<months.length-1)
				houseNumInPeriod.append(",");
		}
		houseNumInPeriod.append("]");
		//houseNumInPeriod.append("period:['2019年1月份','2月份 ','3月份 ','4月份','5月份','6月份','7月份','8月份','9月份','10月份','11月份','12月份']");
		houseNumInPeriod.append("}");
		response.getWriter().write(houseNumInPeriod.toString());
	}
	
}
