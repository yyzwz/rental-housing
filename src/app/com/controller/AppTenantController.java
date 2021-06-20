package app.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import core.extjs.ListView;
import core.support.BaseParameter;
import core.support.QueryResult;
import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.TenantQuery;

import app.com.model.AppCheckTenant;
import app.com.model.AppFWType;
import app.com.model.AppHouse;
import app.com.model.AppRoom;
import app.com.model.AppRoomAndTenant;
import app.com.model.AppTenant;
import app.com.model.AppTenantAndHouseAndRoom;
import app.com.model.AppUser;
import app.com.model.AppUserAndHouse;
import app.com.service.AppFWTypeService;
import app.com.service.AppHouseService;
import app.com.service.AppRoomAndTenantService;
import app.com.service.AppRoomService;
import app.com.service.AppTenantAndHouseAndRoomService;
import app.com.service.AppTenantService;
import app.com.service.AppUserAndHouseService;
import app.com.service.AppUserService;
import core.extjs.ExtJSBaseParameter;
import core.extjs.ListView;
import core.support.QueryResult;

/**
 * @author 郑为中
 */
@Controller
@RequestMapping("/tenant")
public class AppTenantController extends ForestryBaseController<app.com.model.AppTenant> {
		
		@Resource
		private AppTenantService appTenantService;
		
		@Resource
		private AppTenantAndHouseAndRoomService appTenantAndHouseAndRoomService;
		
		@Resource
		private AppUserService appUserService;
		
		@Resource
		private AppUserAndHouseService appUserAndHouseService;
		
		@Resource
		private AppHouseService appHouseService;
		
		@Resource
		private AppRoomService appRoomService;
		
		@Resource
		private AppRoomAndTenantService appRoomAndTenantService;
		
		/**
		 * 根据房间ID找租客信息
		 * 
		 * @param roomID
		 * @param response
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 * @throws IOException
		 */
		@ResponseBody
		@RequestMapping(value="/getTenantByRoom")
	    public List<AppTenant> getTenantByRoom(String roomID,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
			List<AppTenant> ans = new ArrayList<AppTenant>();
			List<AppRoomAndTenant> TARs = new ArrayList<AppRoomAndTenant>();
			TARs = appRoomAndTenantService.doQueryAll();
			System.out.println("租客关系总共有 " + TARs.size());
			List<AppTenant> tenants = new ArrayList<AppTenant>();
			tenants = appTenantService.doQueryAll();
			System.out.println("租客总共有 " + tenants.size());
			for(int i = 0 ; i < TARs.size(); i++){
				if(TARs.get(i).getRoomId() == Long.parseLong(roomID)){
					for(int j = 0 ; j < tenants.size(); j ++){
						System.out.println("tenants.get(j).getId() = " + tenants.get(j).getId());
						System.out.println("TARs.get(i).getTenantId() = " + TARs.get(i).getTenantId());
						if(tenants.get(j).getId().equals(TARs.get(i).getTenantId())){
							System.out.println("111");
							ans.add(tenants.get(j));
						}
					}
				}
			}
			System.out.println("ans size = " + ans.size());
			Collections.reverse(ans);
			return ans;
		}
		
		/**
		 * 根据房间ID 找RAT关系表信息
		 * @param roomID
		 * @param response
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 * @throws IOException
		 */
		@ResponseBody
		@RequestMapping(value="/getRATByRoom")
	    public List<AppRoomAndTenant> getRATByRoom(String roomID,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
			List<AppRoomAndTenant> ans = new ArrayList<AppRoomAndTenant>();
			List<AppRoomAndTenant> TARs = new ArrayList<AppRoomAndTenant>();
			TARs = appRoomAndTenantService.doQueryAll();
			List<AppTenant> tenants = new ArrayList<AppTenant>();
			tenants = appTenantService.doQueryAll();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for(int i = 0 ; i < TARs.size(); i++){
				if(TARs.get(i).getRoomId() == Long.parseLong(roomID)){
					Date Startdate = TARs.get(i).getStartDate();
					format.format(Startdate);
					TARs.get(i).setStartDateWithString(format.format(Startdate));
					Date Enddate = TARs.get(i).getEndDate();
					format.format(Enddate);
					TARs.get(i).setEndDateWithString(format.format(Enddate));
					ans.add(TARs.get(i));
				}
			}
			Collections.reverse(ans);
			return ans;
		}
		
		/**
		 * 根据房间ID找租客信息（去除已过期的数据）
		 * 
		 * @param roomID
		 * @param response
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 * @throws IOException
		 */
		@ResponseBody
		@RequestMapping(value="/getTenantByRoomNotHistory")
	    public List<AppTenant> getTenantByRoomNotHistory(String roomID,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
			List<AppTenant> ans = new ArrayList<AppTenant>();
			List<AppRoomAndTenant> TARs = new ArrayList<AppRoomAndTenant>();
			TARs = appRoomAndTenantService.doQueryAll();
			List<AppTenant> tenants = new ArrayList<AppTenant>();
			tenants = appTenantService.doQueryAll();
			for(int i = 0 ; i < TARs.size(); i++){
				if(TARs.get(i).getRoomId().equals(Long.parseLong(roomID)) && TARs.get(i).getIsHistory().equals("no")&&TARs.get(i).getCheckOpion().equals("同意")){
					for(int j = 0 ; j < tenants.size(); j ++){
						if(tenants.get(j).getId().equals(TARs.get(i).getTenantId())){
							ans.add(tenants.get(j));
						}
					}
				}
			}
			Collections.reverse(ans);
			return ans;
		}
		
		/**
		 * 根据房间ID 找RAT关系表信息 (去除已过期的数据)
		 * @param roomID
		 * @param response
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 * @throws IOException
		 */
		@ResponseBody
		@RequestMapping(value="/getRATByRoomNotHistory")
	    public List<AppRoomAndTenant> getRATByRoomNotHistory(String roomID,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
			List<AppRoomAndTenant> ans = new ArrayList<AppRoomAndTenant>();
			List<AppRoomAndTenant> TARs = new ArrayList<AppRoomAndTenant>();
			TARs = appRoomAndTenantService.doQueryAll();
			List<AppTenant> tenants = new ArrayList<AppTenant>();
			tenants = appTenantService.doQueryAll();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for(int i = 0 ; i < TARs.size(); i++){
				if(TARs.get(i).getRoomId().equals(Long.parseLong(roomID)) && TARs.get(i).getIsHistory().equals("no")&&TARs.get(i).getCheckOpion().equals("同意")){
					Date Startdate = TARs.get(i).getStartDate();
					format.format(Startdate);
					TARs.get(i).setStartDateWithString(format.format(Startdate));
					Date Enddate = TARs.get(i).getEndDate();
					format.format(Enddate);
					TARs.get(i).setEndDateWithString(format.format(Enddate));
					ans.add(TARs.get(i));
				}
			}
			Collections.reverse(ans);
			return ans;
		}
		
		/**
		 *  添加租客  如果身份证号已存在 ，则只插入租借信息
		 *  
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 * @throws IOException
		 */
		@ResponseBody
		@RequestMapping(value = "/setTenant", method = { RequestMethod.POST,RequestMethod.GET})
	    public List<String> setTenant(String name, String idcard, String tel, String sheng,
	    		String shi,String qu,String work, String desc,String roomIdIndex,
	    		String startTime,String endTime,String imageExist,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
	        List<String> ans = new ArrayList<String>(); //账号  返回main界面用
	        List<AppTenant> tenants = new ArrayList<AppTenant>();
	        tenants = appTenantService.doQueryAll();
//	        boolean flag = false;//判断是否存在此租客
	        Long TENANTID = 0l; //租客ID
//	        for(int i = 0 ; i < tenants.size();i++){
//	        	if(tenants.get(i).getTenantIdentify().equals(idcard)){
//	        		flag = true;
//	        		TENANTID = tenants.get(i).getId();
//	        		break;
//	        	}
//	        }
//	        if(!flag){ //如果租客不存在 则添加租客  按照身份证为标准
	        	AppTenant appTenant = new AppTenant();
	        	String age = "" + Integer.parseInt(idcard.substring(6, 14));
	        	// appTenant.setTenantAge(age);
		        appTenant.setTenantName(name);
		        appTenant.setTenantIdentify(idcard);
		        appTenant.setTenantTel(tel);
		        appTenant.setTenantFromShen(sheng);
		        appTenant.setTenantFromShi(shi);
		        appTenant.setTenantFromXian(qu);
		        appTenant.setTenantWorkOrganization(work);
		        appTenant.setTenantDesc(desc);
		        appTenant.setTenantImage(imageExist);
		        appTenant.setCheckOpion("未审核");
		        appTenantService.persist(appTenant);
		        tenants = appTenantService.doQueryAll();
		        TENANTID = appTenant.getId();
//	        } 
	        List<AppRoom> rooms = new ArrayList<AppRoom>();
	        rooms = appRoomService.doQueryAll();
	        String ROOMNAME = "";
	        for(int i = 0 ; i < rooms.size() ; i ++ ) {
	        	if(rooms.get(i).getId() == Long.parseLong(roomIdIndex)){
	        		ROOMNAME = rooms.get(i).getRoomName();
	        	}
	        }
	        AppRoomAndTenant appRoomAndTenant = new AppRoomAndTenant();
	        appRoomAndTenant.setDescInfo(desc);
	        appRoomAndTenant.setRoomId(Long.parseLong(roomIdIndex));
	        appRoomAndTenant.setTenantName(name);
	        appRoomAndTenant.setTenantId(TENANTID);
	        appRoomAndTenant.setRoomName(ROOMNAME);
	        Date StartDate = new Date();
	        Date EndDate = new Date();
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
	        try {
				StartDate = simpleDateFormat.parse(startTime);
				EndDate = simpleDateFormat.parse(endTime);
			} catch (ParseException e) {
				System.out.println("日期 格式错误");
			}
	        appRoomAndTenant.setStartDate(StartDate);
	        appRoomAndTenant.setEndDate(EndDate);
	        appRoomAndTenant.setIsHistory("no");
	        appRoomAndTenant.setCheckOpion("未审核");
	        appRoomAndTenantService.persist(appRoomAndTenant);
	        ans.add("" + TENANTID);
			return ans;
		}
		
		/**
		 * 获得指定村的待审核租客列表  （五层for循环， 改造成了 SQL 五表查询）
		 * 
		 * @param departname
		 * @param response
		 * @throws ServletException
		 * @throws IOException
		 * @throws IOException
		 */
		@RequestMapping(value="/checkTenants")
		@ResponseBody
	    public List<BaseParameter> checkTenants(String departname,
	                            HttpServletResponse response) throws ServletException, IOException, IOException {
			String departmentString = "'" + departname + "'";
			String sql = "select ro.tenantId,t.tenantName,t.tenantIdentify,t.tenantTel,t.tenantFromShen,t.tenantFromShi,t.tenantFromXian,t.tenantWorkOrganization,t.tenantDesc,t.tenantImage,ho.houseOwnerCode,ro.checkOpion,ro.roomId,r.roomName,ro.startDate,ro.endDate,ho.houseOwnerName,ho.houseOwnerTel,h.houseAddress,ro.id from houseowner as ho,house as h,room as r,room_tenant as ro,tenant as t where ho.id=h.houseOwnerId and h.id=r.houseId and r.id=ro.roomId and ro.tenantId=t.id and ro.checkOpion='未审核' and h.departmentName="+departmentString;
			AppCheckTenant tenant = new AppCheckTenant();
			List<BaseParameter> arr=appTenantService.creatNativeSqlQuery(sql, tenant);
			System.out.println(sql);
			return arr;
		}
		
		/**
		 * 根据租户ID和RoomID 返回租户房屋的所有信息   用于修改更新租客信息（租客部分）
		 * 
		 * @param tenantId
		 * @param response
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 * @throws IOException
		 */
		@ResponseBody
		@RequestMapping(value="/getTenantData1")
	    public List<AppTenant> getTenantData1(String tenantId,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
			List<AppTenant> ans = new ArrayList<AppTenant>();
			List<AppTenant> tenants = new ArrayList<AppTenant>();
			tenants = appTenantService.doQueryAll();
			
			for(int i = 0 ; i < tenants.size(); i ++){
				if(tenants.get(i).getId() == Long.parseLong(tenantId)){
					ans.add(tenants.get(i));
					break;
				}
			}
			return ans;
		}
		
		/**
		 * 根据租户ID和RoomID 返回租户房屋的所有信息   用于修改更新租客信息（租借关系表部分）
		 * 
		 * @param tenantId
		 * @param roomID
		 * @param response
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 * @throws IOException
		 */
		@ResponseBody
		@RequestMapping(value="/getTenantData2")
	    public List<AppRoomAndTenant> getTenantData2(String tenantId,String roomID,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
			List<AppRoomAndTenant> ans = new ArrayList<AppRoomAndTenant>();
			List<AppRoomAndTenant> RATs = new ArrayList<AppRoomAndTenant>();
			RATs = appRoomAndTenantService.doQueryAll();
			for(int i = 0 ; i<RATs.size() ; i ++){
				if(RATs.get(i).getRoomId() == Long.parseLong(roomID) && RATs.get(i).getTenantId() == Long.parseLong(tenantId)){
					ans.add(RATs.get(i));
					break;
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date Startdate = ans.get(0).getStartDate();
			format.format(Startdate);
			ans.get(0).setStartDateWithString(format.format(Startdate));
			Date Enddate = ans.get(0).getEndDate();
			format.format(Enddate);
			ans.get(0).setEndDateWithString(format.format(Enddate));
			return ans;
		}
		
		
		@ResponseBody
		@RequestMapping(value="/deleteTenant")
	    public void deleteTenant(String roomID,String tenantID,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
			List<AppRoomAndTenant> RATs = new ArrayList<AppRoomAndTenant>();
			RATs = appRoomAndTenantService.doQueryAll();
			
			Long tabelID = 0l;
			boolean canDelete = false;
			for(int i = 0 ; i < RATs.size(); i ++){
				if(RATs.get(i).getRoomId() == Long.parseLong(roomID)){
					if(RATs.get(i).getTenantId() == Long.parseLong(tenantID)){
						tabelID = RATs.get(i).getId();
						if(RATs.get(i).getCheckOpion().equals("未审核")){
							canDelete = true;
						}
						break;
					}
				}
			}
			if(canDelete){
				appRoomAndTenantService.deleteByPK(tabelID);
			}
			else{
				AppRoomAndTenant appRoomAndTenant = new AppRoomAndTenant();
				appRoomAndTenant.setId(tabelID);
				appRoomAndTenant.setIsHistory("yes");
				appRoomAndTenantService.saveRoomAndTenant(appRoomAndTenant);
			}
		}
		
		/**
		 * 局部更新租客信息
		 * 
		 * @return
		 * @throws ServletException
		 * @throws IOException
		 * @throws IOException
		 */
		@ResponseBody
		@RequestMapping(value="/updateTenant")
	    public void updateTenant(String tenantId,String name,String idcard,String tel,
	    		String sheng,String shi,String qu,String work,
	    		String desc,String roomID,String startTime,String endTime,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
			AppTenant appTenant = new AppTenant();
			appTenant.setId(Long.parseLong(tenantId));
			appTenant.setTenantName(name);
			appTenant.setTenantIdentify(idcard);
			appTenant.setTenantTel(tel);
			appTenant.setTenantFromShen(sheng);
			appTenant.setTenantFromShi(shi);
			appTenant.setTenantFromXian(qu);
			appTenant.setTenantWorkOrganization(work);
			appTenant.setTenantDesc(desc);
			appTenantService.saveTenant(appTenant);
			Long TabelId = 0l; // 租客 和 房间关系标的ID 
			List<AppRoomAndTenant> RATs = new ArrayList<AppRoomAndTenant>();
			RATs = appRoomAndTenantService.doQueryAll();
			for(int i = 0 ; i < RATs.size(); i ++){
				if(RATs.get(i).getRoomId() == Long.parseLong(roomID)){
					if(RATs.get(i).getTenantId() == Long.parseLong(tenantId)){
						TabelId = RATs.get(i).getId();
						break;
					}
				}
			}
			AppRoomAndTenant appRoomAndTenant = new AppRoomAndTenant();
			appRoomAndTenant.setId(TabelId);
			java.util.Date StartDate = new Date();
			java.util.Date EndDate = new Date();
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
	        try {
				StartDate = simpleDateFormat.parse(startTime);
				EndDate = simpleDateFormat.parse(endTime);
			} catch (ParseException e) {
				System.out.println("日期 格式错误");
			}
	        appRoomAndTenant.setTenantName(name);
	        appRoomAndTenant.setStartDate(new java.sql.Date(StartDate.getTime()));
	        appRoomAndTenant.setEndDate(new java.sql.Date(EndDate.getTime()));
	        appRoomAndTenant.setDescInfo(desc);
	        appRoomAndTenant.setCheckOpion("未审核");
	        appRoomAndTenantService.saveRoomAndTenant(appRoomAndTenant);
		}

		
		
		
		
		
		@ResponseBody
		@RequestMapping(value="/getTenantList")
	    public List<AppTenant> getTenant(String loginID,
	            HttpServletResponse response) throws ServletException, IOException, IOException {

			List<AppTenant> ans = new ArrayList<AppTenant>();
			
			List<AppTenant> tenants = new ArrayList<AppTenant>();
			tenants = appTenantService.doQueryAll();
			
			for(int i = 0 ;i<tenants.size();i++){
				if(tenants.get(i).getHouseOwnerCode().equals(loginID)){
					ans.add(tenants.get(i));
				}
			}
			
			return ans;
		}
		@ResponseBody
		@RequestMapping(value="/getTenantIdList")
	    public List<Long> getTenantIdList(String loginID,
	            HttpServletResponse response) throws ServletException, IOException, IOException {
			List<Long> ans = new ArrayList<Long>();
			
//			1.找到房东ID 
			Long USERID = 0l;
			List<AppUser> users = new ArrayList<AppUser>();
			users = appUserService.doQueryAll();
			for(int i = 0 ; i < users.size() ; i ++){
				if(users.get(i).getHouseOwnerCode().equals(loginID)){
					USERID = users.get(i).getId();
					break;
				}
			}
			System.out.println("第一步  根据loginID 找到 房东ID = " + USERID);
			
//			2.找到房东的房房屋列表
			List<AppHouse> houses = new ArrayList<AppHouse>();
			List<Long> HouseIds = new ArrayList<Long>();
			houses = appHouseService.doQueryAll();
			for(int i = 0;i< houses.size(); i ++){
				if(houses.get(i).getHouseOwnerId() == USERID){
					HouseIds.add(houses.get(i).getId());
				}
			}
			System.out.println("第二步 获取该房东的房屋有" + HouseIds.size()+"套 ，其中第一套ID是" + HouseIds.get(0));
			
//			3.找到房屋列表对应的所有房间
			List<AppRoom> rooms = new ArrayList<AppRoom>();
			rooms = appRoomService.doQueryAll();
			
			for(int i = 0 ; i < rooms.size(); i ++){ //所有房间
				for(int j = 0 ; j < HouseIds.size() ; j ++){ //该房东的房屋ID列表
					if(rooms.get(i).getHouseID() == HouseIds.get(j)){
						ans.add(rooms.get(i).getId());
					}
				}
			}
			System.out.println("第三步 获得该房东的租客ID 列表一共有" + ans.size() + " 个 ，其中第一个ID为" +ans.get(0));
			return ans;
		}
		
		@ResponseBody
		@RequestMapping(value="/getTenantById")
	    public AppTenant getTenantById(String tenantID,
	            HttpServletResponse response) throws ServletException, IOException, IOException {

			AppTenant ans = new AppTenant();
			
			List<AppTenant> tenants = new ArrayList<AppTenant>();
			tenants = appTenantService.doQueryAll();
			
			for(int i = 0 ;i<tenants.size();i++){
				if(tenants.get(i).getId() == Long.parseLong(tenantID)){
					ans = tenants.get(i);
				}
			}
			
			return ans;
		}
		
		public static void main(String[] args) {
			String idcard = "330281199908010001";
			String result = idcard.substring(6, 14);
			System.out.println(result);
		}
		
}
