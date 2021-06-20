package app.com.controller;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.forestry.core.ForestryBaseController;
import com.forestry.model.sys.House;

import app.com.dao.AppHouseDao;
import app.com.dao.impl.AppHouseDaoImpl;
import app.com.model.AppAdmim;
import app.com.model.AppDepartment;
import app.com.model.AppHouse;
import app.com.model.AppHouseType;
import app.com.model.AppHouseTypeAndHouse;
import app.com.model.AppRoom;
import app.com.model.AppRoomAndTenant;
import app.com.model.AppTenant;
import app.com.model.AppTenantAndHouseAndRoom;
import app.com.model.AppUser;
import app.com.model.AppUserAndHouse;
import app.com.service.AppDepartmentService;
import app.com.service.AppHouseService;
import app.com.service.AppHouseTypeAndHouseService;
import app.com.service.AppHouseTypeService;
import app.com.service.AppRoomAndTenantService;
import app.com.service.AppRoomService;
import app.com.service.AppTenantAndHouseAndRoomService;
import app.com.service.AppTenantService;
import app.com.service.AppUserAndHouseService;
import app.com.service.AppUserService;

/**
 * @author 郑为中
 */

@Controller
@RequestMapping("/house")
public class AppHouseController extends ForestryBaseController<app.com.model.AppHouse>{

	@Resource
	private AppHouseService appHouseService;
	
	@Resource
	private AppUserAndHouseService appUserAndHouseService;
	
	@Resource
	private AppUserService appUserService;
	
	@Resource
	private AppRoomService appRoomService;
	
	@Resource
	private AppHouseTypeAndHouseService appHouseTypeAndHouseService;
	
	@Resource
	private AppTenantAndHouseAndRoomService appTenantAndHouseAndRoomService;
	
	@Resource
	private AppTenantService appTenantService;
	
	@Resource
	private AppRoomAndTenantService appRoomAndTenantService;
	
	@Resource
	private AppHouseTypeService appHouseTypeService;
	
	@Resource
	private AppDepartmentService appDepartmentService;
	
	
	/**
	 * 模板
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/test", method = { RequestMethod.POST, RequestMethod.GET })
	public void testPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
	}
	
	
	/**
	 * 房屋类型ID列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getHouseTypeIdList", method = { RequestMethod.POST, RequestMethod.GET })
	public List<Long> getHouseTypeIdList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<AppHouseType> types = new ArrayList<AppHouseType>();
		types = appHouseTypeService.doQueryAll();		
		List<Long> ids = new ArrayList<Long>();	
		for(int i = 0 ; i < types.size() ; i ++){
			ids.add(types.get(i).getId());
		}
		return ids;
	}

	/**
	 * 房屋类型  全部列表
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/houseTypeList")
	@ResponseBody
    public List<AppHouseType> houseTypeList(
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppHouseType> types = new ArrayList<AppHouseType>();
		types = appHouseTypeService.doQueryAll();
		return types;
	}
	
	/**
	 * 房屋类型 名字列表
	 * 
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/houseTypeNameList")
	@ResponseBody
    public List<String> houseTypeNameList(
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppHouseType> types = new ArrayList<AppHouseType>();
		types = appHouseTypeService.doQueryAll();
		List<String> ans = new ArrayList<String>();
		for(int i = 0 ; i < types.size();i++){
			ans.add(types.get(i).getHouseTypeName());
		}
		return ans;
	}
	
	/**
	 * 村庄ID列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getVillageIdList", method = { RequestMethod.POST, RequestMethod.GET })
	public List<Long> getVillageIdList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<AppDepartment> departments = new ArrayList<AppDepartment>();
		departments = appDepartmentService.doQueryAll();
		List<Long> ids = new ArrayList<Long>();
		for(int i = 0 ; i < departments.size() ; i ++){
			ids.add(departments.get(i).getId());
		}
		return ids;
	}
	
	/**
	 * 村庄 全部信息 列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepartmentList", method = { RequestMethod.POST, RequestMethod.GET })
	public List<AppDepartment> getDepartmentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<AppDepartment> des = new ArrayList<AppDepartment>();
		des = appDepartmentService.doQueryAll();
		return des;
	}
	
	/**
	 * 村庄 名字 列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepartmentNameList", method = { RequestMethod.POST, RequestMethod.GET })
	public List<String> getDepartmentNameList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<AppDepartment> des = new ArrayList<AppDepartment>();
		des = appDepartmentService.doQueryAll();
		List<String> ans = new ArrayList<String>();
		for(int i = 0 ; i< des.size() ; i ++){
			ans.add(des.get(i).getName());
		}
		return ans;
	}
	
	/**
	 * 查询指定房东ID的房屋列表
	 * 
	 * @param houseOwnerId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/myHouseList", method = { RequestMethod.POST, RequestMethod.GET })
	public List<AppHouse> testPost(String houseOwnerId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> arrPropNames = new ArrayList<String>();
		ArrayList<Object> arrPropValues = new ArrayList<Object>();
		arrPropNames.add("houseOwnerId");
		arrPropValues.add(Long.valueOf(houseOwnerId));
		String[] propNames= new String[arrPropNames.size()];
		Object[] propValues= new Object[arrPropNames.size()];
		for(int i=0;i<arrPropNames.size();i++) {
			propNames[i]=(String)arrPropNames.get(i);
			propValues[i]=arrPropValues.get(i);
		}
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.queryByProerties(propNames, propValues);
		return houses;
	}
	
	/**
	 * 删除指定ID的房屋
	 * @param houseID
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/deleteHouse")
	@ResponseBody
    public List<String> deleteHouse(String houseID,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<String> ans = new ArrayList<String>();
		List<AppRoom> rooms = new ArrayList<AppRoom>();
		boolean flag = false;
		rooms = appRoomService.doQueryAll();
		for(int i = 0 ; i < rooms.size(); i++){
			if(rooms.get(i).getHouseID().equals(Long.parseLong(houseID))){
				ans.add("existRooms");
				flag = true;
				break;
			}
		}
		if(!flag) {
			appHouseService.deleteByPK(Long.parseLong(houseID));
		}
		ans.add("ok");
		return ans;
	}
	/**
	 * 局部更新房屋信息
	 * 
	 * @param houseID
	 * @param typeId
	 * @param typeName
	 * @param address
	 * @param sheQuID
	 * @param sheQuName
	 * @param name
	 * @param about
	 * @param dingWei
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updeteHouse", method = { RequestMethod.POST, RequestMethod.GET })
	public void updeteHouse(String houseID,
			String typeId,String typeName,String address,String sheQuID,
			String sheQuName,String name,String about,String dingWei,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppHouse appHouse = new AppHouse();
		appHouse.setId(Long.parseLong(houseID));
		appHouse.setHouseTypeId(Long.parseLong(typeId));
		appHouse.setHouseTypeName(typeName);
		appHouse.setDepartmentId(Long.parseLong(sheQuID));
		appHouse.setDepartmentName(sheQuName);
		if(!address.equals("undefined")){
			appHouse.setHouseAddress(address);
		}
		if(!name.equals("undefined")){
			appHouse.setHouseName(name);
		}
		if(!about.equals("undefined")){
			appHouse.setHouseDesc(about);
		}
		appHouse.setCheckOpion("未审核");
		appHouseService.saveHouse(appHouse);
	}
	
	@ResponseBody
	@RequestMapping(value = "/updeteDingWei", method = { RequestMethod.POST, RequestMethod.GET })
	public void updeteDingWei(String houseID,String dingwei,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppHouse appHouse = new AppHouse();
		appHouse.setId(Long.parseLong(houseID));
		appHouse.setHouseMapLocation(dingwei);
		System.out.println("dingWei = " + dingwei);
		appHouse.setCheckOpion("未审核");
		appHouseService.saveHouse(appHouse);
		// appHouseService.creatNativeSqlQuery("update house set houseMapLocation = " +dingWei , appHouse);
	}
	
	/**
	 * 局部更新房间信息
	 * 
	 * @param roomid
	 * @param name
	 * @param area
	 * @param about
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updeteRoom", method = { RequestMethod.POST, RequestMethod.GET })
	public void updeteRoom(String roomid,String name,String area,String about,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		AppRoom appRoom = new AppRoom();
		appRoom.setId(Long.parseLong(roomid));
		if(!name.equals("")){
			appRoom.setRoomName(name);
		}
		if(!area.equals("")){
			appRoom.setRoomArea(area);
		}
		if(!about.equals("")){
			appRoom.setRoomDesc(about);
		}
		appRoomService.saveRoom(appRoom);	
	}
	/**
	 * 获取指定ID房屋 的原来信息
	 * @param houseID
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/getHouseMsg")
	@ResponseBody
    public List<AppHouse> getHouseMsg(String houseID,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppHouse> ans = new ArrayList<AppHouse>();
		
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		for(int i = 0 ; i< houses.size() ; i ++){
			if(houses.get(i).getId().equals(Long.parseLong(houseID))){
				ans.add(houses.get(i));
				break;
			}
		}
		return ans;
	}
	
	/**
	 * 获取指定房间的 历史信息
	 * @param roomid
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/getRoomMsg")
	@ResponseBody
    public List<AppRoom> getRoomMsg(String roomid,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppRoom> ans = new ArrayList<AppRoom>();
		List<AppRoom> rooms = new ArrayList<AppRoom>();
		rooms = appRoomService.doQueryAll();
		for(int i = 0 ; i< rooms.size() ; i ++){
			if(rooms.get(i).getId().equals(Long.parseLong(roomid))){
				ans.add(rooms.get(i));
				break;
			}
		}
		return ans;
	}
	
	/**
	 * 添加房屋  备用
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveHouse", method = { RequestMethod.POST, RequestMethod.GET })
	public void saveHouse(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = (String)request.getParameter("id");
		String houseName = (String)request.getParameter("houseName");
		String houseDesc = (String)request.getParameter("houseDesc");
		AppHouse appHouse=new AppHouse();
		appHouse.setId(Long.valueOf(id));
		appHouse.setHouseDesc(houseDesc);
		appHouse.setHouseName(houseName);
		appHouseService.saveHouse(appHouse);
	}
	
	/**
	 * 根据房间ID找房东账号 备用
	 * @param houseID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findUserIdByRoomId", method = { RequestMethod.POST, RequestMethod.GET })
	public String findUserIdByRoomId(String houseID,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		Long HOUSEOWNID = 0l;
		for(int i = 0 ; i < houses.size() ;  i++){
			if(houses.get(i).getId().equals(Long.parseLong(houseID))){
				HOUSEOWNID = houses.get(i).getHouseOwnerId();
			}
		}
		List<AppUser> users = new ArrayList<AppUser>();
		users = appUserService.doQueryAll();
		String code = "";
		for(int i = 0 ; i < users.size(); i ++){
			if(users.get(i).getId().equals(HOUSEOWNID)){
				code = users.get(i).getHouseOwnerCode();
			}
		}
		return code;
	}
	
	/**
	 * 添加房屋 
	 * 
	 * @param typeId
	 * @param typeName
	 * @param address
	 * @param sheQuID
	 * @param sheQuName
	 * @param name
	 * @param about
	 * @param dingWei
	 * @param loginID
	 * @param loginName
	 * @param imageExist
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@ResponseBody	
	@RequestMapping(value = "/addHouse", method = { RequestMethod.POST, RequestMethod.GET })
	public List<String> addHouse(String typeId,String typeName,String address,
    		String sheQuID, String sheQuName,String name,String about,
    		String dingWei,String loginID,String loginName,String imageExist,
    		HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException, IOException {
		AppHouse appHouse = new AppHouse();
		appHouse.setHouseTypeId(Long.parseLong(typeId));
		appHouse.setHouseTypeName(typeName);
		appHouse.setHouseAddress(address);
		appHouse.setDepartmentId(Long.parseLong(sheQuID));
		appHouse.setDepartmentName(sheQuName);
		appHouse.setHouseName(name);
		appHouse.setHouseDesc(about);
		appHouse.setHouseMapLocation(dingWei);
		appHouse.setHouseImage(imageExist); //是否有图片
		appHouse.setCheckOpion("未审核");
		appHouse.setHouseOwnerId(Long.valueOf(loginID));
		appHouse.setHouseOwnerName(loginName);
		appHouseService.persist(appHouse);
		List<String> ans = new ArrayList<String>();
		AppHouseTypeAndHouse appHouseTypeAndHouse = new AppHouseTypeAndHouse();
		appHouseTypeAndHouse.setHouseId(appHouse.getId());
		appHouseTypeAndHouse.setHouseTypeId(Long.parseLong(typeId));
		appHouseTypeAndHouseService.persist(appHouseTypeAndHouse);
		ans.add("" + appHouse.getId());
		
//		String filePath=request.getServletContext().getRealPath("/");
//		appHouseService.saveHouseTwoDimensionalCode(request, filePath,Long.valueOf(appHouse.getId()));
		
        return ans;
    }
	/**
	 * 添加房间
	 * 
	 * @param roomName
	 * @param roomSize
	 * @param roomDesc
	 * @param houseID
	 * @param imageExist
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/setRoom", method = { RequestMethod.POST, RequestMethod.GET })
	public List<String> addRoom(String roomName,String roomSize,String roomDesc,
    		String houseID,String imageExist,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		List<String> ans = new ArrayList<String>();
		AppRoom appRoom = new AppRoom();
		appRoom.setRoomName(roomName);
		appRoom.setRoomArea(roomSize);
		appRoom.setRoomDesc(roomDesc);
		Long HouseID = Long.parseLong(houseID);
		appRoom.setHouseID(HouseID);
		String HOUSENAME = "";
		for(int i = 0;i<houses.size();i++){
			if(houses.get(i).getId().equals(HouseID)){
				HOUSENAME = houses.get(i).getHouseName();
				break;
			}
		}
		appRoom.setHouseName(HOUSENAME);
		if(imageExist.equals("0")){
			appRoom.setRoomImage("0");
		}else{
			appRoom.setRoomImage("1");
		}
		appRoomService.persist(appRoom);
		ans.add("" + appRoom.getId());
		return ans;
	}
	/**
	 * 添加租客/我的租客页面 用于获取房屋列表
	 * 
	 * @param loginID
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value = "/houseList", method = { RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
    public List<AppHouse> houseList(String loginID,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppHouse> ans = new ArrayList<AppHouse>();
		Long HouseOwnerID = Long.valueOf(loginID);//房东ID
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		for(int i = 0 ; i < houses.size();i++){
			if(houses.get(i).getHouseOwnerId().equals(HouseOwnerID)){
				ans.add(houses.get(i));
			}
		}
		return ans;
	}
	/**
	 * 根据房屋ID 获取房间列表
	 * 
	 * @param HouseID
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/getRoomListByHouseId")
	@ResponseBody
    public List<AppRoom> getRoomListByHouseId(String HouseID,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppRoom> ans = new ArrayList<AppRoom>();
		List<AppRoom> rooms = new ArrayList<AppRoom>();
		rooms = appRoomService.doQueryAll();
		for(int i = 0 ; i < rooms.size();i ++){
			if(rooms.get(i).getHouseID().equals(Long.parseLong(HouseID))){
				ans.add(rooms.get(i));
			}
		}
		return ans;		
	}

	/**
	 * 根据登入账号 获取房间列表   （备用）
	 * 
	 * @param loginID
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/roomList")
	@ResponseBody
    public List<AppRoom> roomList(String loginID,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppRoom> ans = new ArrayList<AppRoom>();//房间列表
		//	第一步 根据房东账号loginID 找到房东ID
		List<AppUser> users = new ArrayList<AppUser>();
		users = appUserService.doQueryAll();
		Long HouseOwnerID = 0l;//房东ID
		for(int i = 0 ; i< users.size();i++){
			if(users.get(i).getHouseOwnerCode().endsWith(loginID)){
				HouseOwnerID = users.get(i).getId();
				break;
			}
		}
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		List<AppRoom> rooms = new ArrayList<AppRoom>();//房间列表
		rooms = appRoomService.doQueryAll();
		for(int i = 0 ;i<houses.size();i++){
			if(houses.get(i).getHouseOwnerId().equals(HouseOwnerID)){
				for(int j = 0;j<rooms.size();j++){
					if(rooms.get(j).getHouseID().equals(houses.get(i).getId())){
						ans.add(rooms.get(j));
					}
				}
			}
		}	
		return ans;
	}
	
	/**
	 * 根据房间ID 获取房间所有信息 （备用）
	 * @param roomID
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/findRoom")
	@ResponseBody
    public AppRoom findRoom(String roomID,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		AppRoom appRoom = new AppRoom();
		List<AppRoom> rooms = new ArrayList<AppRoom>();
		rooms = appRoomService.doQueryAll();
		for(int i = 0 ; i < rooms.size();i++){
			if(rooms.get(i).getId().equals(Long.parseLong(roomID))){
				appRoom = rooms.get(i);
				break;
			}
		}	
		return appRoom;
	}
	
	/**
	 * 根据房屋ID 获取房屋所有信息 （备用）
	 * 
	 * @param HouseID
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/findHouse")
	@ResponseBody
    public AppHouse findHouse(String HouseID,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		
		AppHouse appHouse = new AppHouse();
		
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		for(int i = 0 ; i < houses.size();i++){
			if(houses.get(i).getId().equals(Long.parseLong(HouseID))){
				appHouse = houses.get(i);
				break;
			}
		}	
		return appHouse;
	}
	
	/**
	 * 添加租客和房屋的关系表  （备用）
	 * @param roomID
	 * @param tenantID
	 * @param startTime
	 * @param endTime
	 * @param about
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/addTAR")
	@ResponseBody
    public List<Boolean> addTenantAndRoom(String roomID,String tenantID,
    		String startTime,String endTime,String about,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<Boolean> ans = new ArrayList<Boolean>();
		// 根据房间ID 找房间名
		List<AppRoom> rooms = new ArrayList<AppRoom>();
		rooms = appRoomService.doQueryAll();
		String ROOMNAME = "";
		for(int i = 0 ; i < rooms.size() ; i ++){
			if(rooms.get(i).getId().equals(Long.parseLong(roomID))){
				ROOMNAME = rooms.get(i).getRoomName();
				break;
			}
		}
		//		根据租客ID 找租客名
		List<AppTenant> tenants = new ArrayList<AppTenant>();
		String TENANTNAME = "";
		tenants = appTenantService.doQueryAll();
		for(int i = 0 ; i < tenants.size(); i ++){
			if(tenants.get(i).getId().equals(Long.parseLong(tenantID))){
				TENANTNAME = tenants.get(i).getTenantName();
				break;
			}
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null; Date endDate = null;
		try {
			startDate = sdf.parse(startTime);
			endDate = sdf.parse(endTime);	
		} catch (ParseException e) {
			e.printStackTrace();
		}
		AppRoomAndTenant appRoomAndTenant = new AppRoomAndTenant();
		appRoomAndTenant.setRoomId(Long.parseLong(roomID));//
		appRoomAndTenant.setTenantId(Long.parseLong(tenantID));
		appRoomAndTenant.setRoomName(ROOMNAME);
		appRoomAndTenant.setTenantName(TENANTNAME);
		appRoomAndTenant.setStartDate(startDate);
		appRoomAndTenant.setEndDate(endDate);
		appRoomAndTenant.setDescInfo(about);
		appRoomAndTenantService.persist(appRoomAndTenant);
		ans.add(true);
		return ans;
	}
	
	/**
	 * 根据房间ID  删除房间
	 * @param roomid
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws IOException
	 */
	@RequestMapping(value="/deleteRoom")
	@ResponseBody
    public List<String> deleteRoom(String roomid,
    		HttpServletResponse response) throws ServletException, IOException, IOException {
		List<String> ans = new ArrayList<String>();
		boolean canDelete = true;
		List<AppRoomAndTenant> RATs = new ArrayList<AppRoomAndTenant>();
		RATs = appRoomAndTenantService.doQueryAll();
		for(int i = 0 ; i < RATs.size() ; i ++){
			if(RATs.get(i).getRoomId().equals(Long.parseLong(roomid))){
				canDelete = false;
				break;
			}
		}
		if(!canDelete){
			ans.add("cannot");
		}else{
			ans.add("ok");
			appRoomService.deleteByPK(Long.parseLong(roomid));
		}
		return ans;
	}
	
	/**
	 * 查询指定房东ID的房屋列表
	 * 
	 * @param houseOwnerId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/adminHouseList", method = { RequestMethod.POST, RequestMethod.GET })
	public List<AppHouse> adminHouseList(String departName,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<AppHouse> ans = new ArrayList<AppHouse>();
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		if(departName.equals("")) { //超级管理员
			return houses;
		}
		for(int i = 0 ; i < houses.size() ; i ++) {
			if(houses.get(i).getDepartmentName().equals(departName)) {
				ans.add(houses.get(i));
			}
		}
		return ans; //区域管理员
	}
	
	@ResponseBody
	@RequestMapping(value = "/fingHouseDingWei", method = { RequestMethod.POST, RequestMethod.GET })
	public List<String> fingHouseDingWei(String houseid,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> ans = new ArrayList<String>();
		List<AppHouse> houses = new ArrayList<AppHouse>();
		String str = "";
		houses = appHouseService.doQueryAll();
		for(int i = 0 ; i < houses.size() ; i ++) {
			if(houses.get(i).getId().equals(Long.parseLong(houseid))) {
				str = houses.get(i).getHouseMapLocation();
				break;
			}
		}
		int jingdu = str.indexOf(",latitude");
		int weidu = str.indexOf(",longitude");
//		System.out.println("str = " +str);
//		System.out.println("jingdu = " + jingdu + " 字符开始 = " + str.charAt(jingdu+10));
//		System.out.println("weidu = " + weidu+ " 字符开始 = " + str.charAt(weidu+11));
		String temp = "";
		for(int i = jingdu + 10 ; i < str.length() ; i ++) {
			if(str.charAt(i)>='0' && str.charAt(i)<='9' || str.charAt(i) == '.' ) {
				temp += str.charAt(i);
			}else {
				if(!temp.equals("")) {
					ans.add(temp);
					temp = "";
				}
			}
		}
		ans.add(temp);
		if(ans.size() == 0) {
			ans.add("29.253901");
			ans.add("121.719823");
		}
		return ans;
	}
	
	@ResponseBody
	@RequestMapping(value = "/pl", method = { RequestMethod.POST, RequestMethod.GET })
	public List<String> PiLiangEWM(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> ans = new ArrayList<String>();
		
		String filePath=request.getServletContext().getRealPath("/");
		
		for(int id = 1; id<=30000;id ++) {
			appHouseService.saveHouseTwoDimensionalCode(request, filePath,Long.valueOf(Long.parseLong(""+id)));
		}
		ans.add("ok");
		return ans; 
	}
	
}
