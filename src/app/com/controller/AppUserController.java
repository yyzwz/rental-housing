package app.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import com.forestry.core.ForestryBaseController;

import app.com.model.AppAdmim;
import app.com.model.AppCheckHouse;
import app.com.model.AppCheckTenant;
import app.com.model.AppDepartment;
import app.com.model.AppHouse;
import app.com.model.AppRoom;
import app.com.model.AppTenant;
import app.com.model.AppTenantAndHouseAndRoom;
import app.com.model.AppUser;
import app.com.model.query.AppTenantQuery;
import app.com.service.AppAdminService;
import app.com.service.AppDepartmentService;
import app.com.service.AppHouseService;
import app.com.service.AppRoomService;
import app.com.service.AppTenantAndHouseAndRoomService;
import app.com.service.AppTenantService;
import app.com.service.AppUserService;
import core.extjs.ListView;
import core.support.BaseParameter;
import core.util.PageUtils;

/**
 * 	@author 郑为中
 */

@Controller
@RequestMapping("/houseWoner")
public class AppUserController extends ForestryBaseController<app.com.model.AppUser> {
	
	@Resource
	private AppUserService appUserService;
	
	@Resource
	private AppHouseService appHouseService;
	
	@Resource
	private AppAdminService appAdminService;
	
	@Resource
	private AppRoomService appRoomService;
	
	@Resource
	private AppTenantAndHouseAndRoomService appTenantAndHouseAndRoomService;

	@Resource
	private AppTenantService appTenantService;
	
	/**
	 * 模板代码  新增方法时拷贝用
	 * 
	 * 郑为中
	 */
	@ResponseBody
	@RequestMapping(value="/test")
    public List<Boolean> test01(HttpServletResponse response)throws ServletException, IOException, IOException{
		List<Boolean> ans = new ArrayList<Boolean>();
		ans.add(true);
		return ans;
	}
	
	
	
	/**
	 * 
	 * 登入界面 使用手机号快捷登入
	 * 
	 * 1. 首先找 是否为管理员账号，找到则返回管理员所在村名
	 * 
	 * 2. 没找到 则找房东，找到则返回房东账号
	 * 
	 * 3. 如果都没找到 返回none
	 * 
	 * 郑为中
	 */
	@ResponseBody
	@RequestMapping(value="/telLogin")
    public List<String> telLogin(String tel,
    		HttpServletResponse response)throws ServletException, IOException, IOException{
		
		List<String> ans = new ArrayList<String>();
		
		// 过渡数组
		ArrayList<String> arrPropNames = new ArrayList<String>();
		ArrayList<Object> arrPropValues = new ArrayList<Object>();
		
		//查询条件
		arrPropNames.add("tel");
		arrPropValues.add(tel);
		
		//queryByProerties 要传的数组
		String[] propNames= new String[arrPropNames.size()];
		Object[] propValues= new Object[arrPropNames.size()];
		
		//强制转换
		for(int i=0;i<arrPropNames.size();i++) {
			propNames[i]=(String)arrPropNames.get(i);
			propValues[i]=arrPropValues.get(i);
		}

		//查询是否是管理员
		List<AppAdmim> admins = appAdminService.queryByProerties(propNames, propValues);
		
		//如果是管理员
		if(!admins.isEmpty()){
			ans.add("admin"); //告知前台是管理员
			ans.add(admins.get(0).getDepartmentName()); //管理员的部门名
			return ans;
		}

		// 同理查找房东 
		// 注意一定要建立新的 数组   ，老的清空后再加会出错
		ArrayList<String> arrPropNames2 = new ArrayList<String>();
		ArrayList<Object> arrPropValues2 = new ArrayList<Object>();
		
		arrPropNames2.add("houseOwnerTel");
		arrPropValues2.add(tel);
		
		String[] propNames2= new String[arrPropNames2.size()];
		Object[] propValues2= new Object[arrPropNames2.size()];
		
		for(int i=0;i<arrPropNames2.size();i++) {
			propNames2[i]=(String)arrPropNames2.get(i);
			propValues2[i]=arrPropValues2.get(i);
		}
		
		List<AppUser> users = appUserService.queryByProerties(propNames2, propValues2);
		
		if(!users.isEmpty()){
			ans.add("user");// 告知前台是房东
			ans.add(users.get(0).getHouseOwnerCode()); //返回房东账号
			return ans;
		}
		
		// 如果找不到 告知前台none
		ans.add("none");
		return ans;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/twoWeiMaLogin")
    public boolean twoWeiMaLogin(String houseID,String tel,
    		HttpServletResponse response)throws ServletException, IOException, IOException{
		
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		String CunName = null;
		Long HouseOwnId = 0l;
		for(int i = 0 ; i < houses.size(); i ++){
			if(houses.get(i).getId() == Long.parseLong(houseID)){
				CunName = houses.get(i).getDepartmentName();
				if(CunName == null){
					CunName = houses.get(i).getDepartmentName();
				}
				HouseOwnId = houses.get(i).getHouseOwnerId();
				break;
			}
		}
		
		
		List<AppAdmim> admins = new ArrayList<AppAdmim>();
		admins = appAdminService.doQueryAll();
		
		for(int i = 0 ; i<admins.size(); i ++){
			if(admins.get(i).getTel().equals(tel)){
				if(admins.get(i).getRole() == 1){
					return true;
				}
				else if(admins.get(i).getRole() == 2){

					if(CunName.equals(admins.get(i).getDepartmentName())){
						return true;
					}
					else{
						return false;
					}
				}
			}
		}
		
		
		List<AppUser> users = new ArrayList<AppUser>();
		users = appUserService.doQueryAll();
		
		
		
		for(int i = 0 ; i <users.size() ; i ++){
			if(users.get(i).getHouseOwnerTel().equals(tel)){
				if(HouseOwnId == users.get(i).getId()){
					return true;
				}
				break;
			}
		}
		
		return false;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/findNameByCode" ,produces="text/html;charset=UTF-8")
    public String FindNameByCode(String code,
    		HttpServletResponse response)throws ServletException, IOException, IOException{
		response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        
		List<AppUser> users = new ArrayList<AppUser>();
		users = appUserService.doQueryAll();
		String UserName = "";
		for(int i = 0 ; i < users.size() ; i ++){
			if(users.get(i).getHouseOwnerCode().equals(code)){
				UserName = users.get(i).getHouseOwnerName();
				break;
			}
		}
		System.out.println("根据code找到的名字 = " + UserName);
		return UserName;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
    public List<String> HouseWonerRegister(String code,String password,String name,
    		String phone, String idcard,String desc,String address,
                            HttpServletResponse response) throws ServletException, IOException, IOException {

		List<String> ans = new ArrayList<String>();

		ArrayList<String> arrPropNames = new ArrayList<String>();
		ArrayList<Object> arrPropValues = new ArrayList<Object>();
		arrPropNames.add("houseOwnerCode");
		arrPropValues.add(code);
		String[] propNames= new String[arrPropNames.size()];
		Object[] propValues= new Object[arrPropNames.size()];
		for(int i=0;i<arrPropNames.size();i++) {
			propNames[i]=(String)arrPropNames.get(i);
			propValues[i]=arrPropValues.get(i);
		}
		List<AppUser> users = appUserService.queryByProerties(propNames, propValues);
		if(!users.isEmpty()){//注册账号已存在
			ans.add("exist");
			return ans;
		}
		AppUser appUser = new AppUser();
        appUser.setHouseOwnerCode(code);
        appUser.setHouseOwnerPassWord(password);
        appUser.setHouseOwnerName(name);
        appUser.setHouseOwnerTel(phone);
        appUser.setHouseOwnerIdentify(idcard);
        appUser.setHouseOwnerDesc(desc);
        appUser.setHouseOwnerRole(3);
        appUser.setHouseOwnerLastLoginTime(new Date());
        appUser.setHouseOwnerAddress(address);
        appUser.setRegistDate(new Date());
        appUserService.persist(appUser);
        ans.add("yes");
        ans.add(appUser.getHouseOwnerCode());
		
        return ans;
    }
	
	
	@RequestMapping(value="/login")
	@ResponseBody
    public List<String> Login(int role,String logincode,String loginpassword,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		List<String> ans = new ArrayList<String>();
		
		
		List<AppUser> lists03 = new ArrayList<AppUser>();
		lists03 = appUserService.doQueryAll();//所有用户的列表
		Boolean flagExist = false;//用于判断账号是否存在
		Boolean flag = false;
		String HouseOwnerName = "";
		for(int i = 0 ; i < lists03.size() ; i ++){
			if(lists03.get(i).getHouseOwnerCode().equals(logincode)){//如果账号已经存在
				flagExist = true;
				if(lists03.get(i).getHouseOwnerPassWord().equals(loginpassword)){
					flag = true;
					HouseOwnerName = lists03.get(i).getHouseOwnerName();
				}
			}
			System.out.println("-------------------------------------------------");
			System.out.println("账号为 = " + lists03.get(i).getHouseOwnerCode());
			System.out.println("密码为 = " + lists03.get(i).getHouseOwnerPassWord());
		}
			
		
		if(!flagExist) {
			ans.add("none");
			ans.add("none");
		}
		else if(flag) {
			ans.add("yes");
			ans.add(HouseOwnerName);
		}
		else {
			ans.add("no");
			ans.add("none");
		}
		return ans;
	}
	
	@RequestMapping(value="/checkHouses")
	@ResponseBody
    public List<AppCheckHouse> checkHouses(String departname,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppCheckHouse> ans = new ArrayList<AppCheckHouse>();
		
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		List<AppUser> users = new ArrayList<AppUser>();
		users = appUserService.doQueryAll();
		
		
		for(int i = 0 ; i < houses.size() ; i ++ ){
			if(houses.get(i).getDepartmentName().equals(departname) && houses.get(i).getCheckOpion().equals("未审核")){
				AppCheckHouse appCheckHouse = new AppCheckHouse();
				appCheckHouse.setHouseID(houses.get(i).getId());
				appCheckHouse.setHouseTypeName(houses.get(i).getHouseTypeName());
				appCheckHouse.setHouseAddress(houses.get(i).getHouseAddress());
				appCheckHouse.setDepartmentName(houses.get(i).getDepartmentName());
				appCheckHouse.setHouseName(houses.get(i).getHouseName());
				appCheckHouse.setHouseDesc(houses.get(i).getHouseDesc());
				appCheckHouse.setHouseImage(houses.get(i).getHouseImage());
				appCheckHouse.setHouseOwnerName(houses.get(i).getHouseOwnerName());
				
				for(int j = 0 ; j < users.size(); j ++){
					if(users.get(j).getId() == houses.get(i).getHouseOwnerId()){
						appCheckHouse.setHouseOwnerId(users.get(j).getId());
						appCheckHouse.setHouseOwnerIdentify(users.get(j).getHouseOwnerIdentify());
						appCheckHouse.setHouseOwnerTel(users.get(j).getHouseOwnerTel());
						appCheckHouse.setHouseOwnerDesc(users.get(j).getHouseOwnerDesc());
						appCheckHouse.setHouseOwnerAddress(users.get(j).getHouseOwnerAddress());
						break;
					}
				}
				
				ans.add(appCheckHouse);
			}
		}

		return ans;
	}
	
	@RequestMapping(value="/checkHousesPass")
	@ResponseBody
    public void checkHousesPass(String houseID,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		List<AppAdmim> ments = new ArrayList<AppAdmim>();
		ments = appAdminService.doQueryAll();
		
		for(int i = 0 ; i < houses.size(); i ++){
			if(houses.get(i).getId() == Long.parseLong(houseID)){
				AppHouse appHouse = new AppHouse();
				appHouse.setId(houses.get(i).getId());
				appHouse.setCheckOpion("同意");
				for(int j = 0 ; j < ments.size() ; j++){
					if(ments.get(j).getDepartmentId() == houses.get(i).getDepartmentId()){
						appHouse.setCheckerId(ments.get(j).getId());
						appHouse.setCheckerName(ments.get(j).getReal_name());
						
//						appHouse.setCheckDate(new java.sql.Date());
						break;
					}
				}
				appHouseService.saveHouse(appHouse);
				return ;
			}
		}
	}
	
	@RequestMapping(value="/checkHousesNotPass")
	@ResponseBody
    public void checkHousesNotPass(String houseID,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		List<AppAdmim> ments = new ArrayList<AppAdmim>();
		ments = appAdminService.doQueryAll();
		
		for(int i = 0 ; i < houses.size(); i ++){
			if(houses.get(i).getId() == Long.parseLong(houseID)){
				AppHouse appHouse = new AppHouse();
				appHouse.setId(houses.get(i).getId());
				appHouse.setCheckOpion("不同意");
				for(int j = 0 ; j < ments.size() ; j++){
					if(ments.get(j).getDepartmentId() == houses.get(i).getDepartmentId()){
						appHouse.setCheckerId(ments.get(j).getId());
						appHouse.setCheckerName(ments.get(j).getReal_name());
						
//						appHouse.setCheckDate(new java.sql.Date());
						break;
					}
				}
				appHouseService.saveHouse(appHouse);
				return ;
			}
		}
	}
	
	@RequestMapping(value="/checkTenants")
	@ResponseBody
    public List<AppCheckTenant> checkTenants(String departname,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		List<AppRoom> rooms = new ArrayList<AppRoom>();
		rooms = appRoomService.doQueryAll();
		List<AppTenantAndHouseAndRoom> rats = new ArrayList<AppTenantAndHouseAndRoom>();
		rats = appTenantAndHouseAndRoomService.doQueryAll();
		List<AppTenant> tenants = new ArrayList<AppTenant>();
		tenants = appTenantService.doQueryAll();
		List<AppUser> users = new ArrayList<AppUser>();
		users = appUserService.doQueryAll();
		
		List<AppCheckTenant> ans = new ArrayList<AppCheckTenant>();
		
		for(int i = 0 ; i < houses.size() ; i ++){
			if(houses.get(i).getDepartmentName().equals(departname)){ //如果是所在村的房屋
				for(int j = 0 ; j < rooms.size() ; j ++){
					if(rooms.get(j).getHouseID() == houses.get(i).getId()){ // 如果是该房屋的房间
						for(int k = 0 ; k < rats.size() ; k ++){
							if(rats.get(k).getRoomId() == rooms.get(j).getId() && rats.get(k).getCheckOpion().equals("未审核")){ // 如果有租借信息
								AppCheckTenant appCheckTenant = new AppCheckTenant();
								for(int l = 0 ; l < tenants.size() ; l ++){
									if(tenants.get(l).getId() == rats.get(k).getTenantId()){ // 是此租客
//										appCheckTenant.setTenantId(tenants.get(l).getId());
										appCheckTenant.setTenantName(tenants.get(l).getTenantName());
										appCheckTenant.setTenantTel(tenants.get(l).getTenantTel());
										appCheckTenant.setTenantIdentify(tenants.get(l).getTenantIdentify());
										appCheckTenant.setTenantFromShen(tenants.get(l).getTenantFromShen());
										appCheckTenant.setTenantFromShi(tenants.get(l).getTenantFromShi());
										appCheckTenant.setTenantFromXian(tenants.get(l).getTenantFromXian());
										appCheckTenant.setTenantWorkOrganization(tenants.get(l).getTenantWorkOrganization());
										appCheckTenant.setTenantDesc(tenants.get(l).getTenantDesc());
										appCheckTenant.setTenantImage(tenants.get(l).getTenantImage());
										appCheckTenant.setHouseOwnerCode(tenants.get(l).getHouseOwnerCode());
									}
								}
//								appCheckTenant.setRatID(rats.get(k).getId());
//								appCheckTenant.setRoomId(rats.get(k).getRoomId());
								appCheckTenant.setRoomName(rats.get(k).getRoomName());
//								appCheckTenant.setStartDate(rats.get(k).getStartDate());
//								appCheckTenant.setEndDate(rats.get(k).getEndDate());
								appCheckTenant.setHouseAddress(houses.get(i).getHouseAddress());
								appCheckTenant.setHouseOwnerName(houses.get(i).getHouseOwnerName());
								for(int o = 0 ; o <users.size() ; o ++){
									if(users.get(o).getId() == houses.get(i).getHouseOwnerId()){
										appCheckTenant.setHouseOwnerTel(users.get(o).getHouseOwnerTel());
									}
								}
								
								
								ans.add(appCheckTenant);
								
							}
						}
					}
				}
			}
		}
		
		return ans;
		
	}
	
	@RequestMapping(value="/checkTenantPass")
	@ResponseBody
    public void checkTenantPass(String ratID,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		
		List<AppTenantAndHouseAndRoom> rat = new ArrayList<AppTenantAndHouseAndRoom>();
		rat = appTenantAndHouseAndRoomService.doQueryAll();
		
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		List<AppRoom> rooms = new ArrayList<AppRoom>();
		rooms = appRoomService.doQueryAll();
		List<AppAdmim> admins = new ArrayList<AppAdmim>();
		admins = appAdminService.doQueryAll();
		
		for(int i = 0 ; i < rat.size() ; i ++){
			if(rat.get(i).getId() == Long.parseLong(ratID)){
				AppTenantAndHouseAndRoom t = new AppTenantAndHouseAndRoom();
				t.setId(rat.get(i).getId());
				t.setCheckOpion("同意");
				for(int j = 0 ; j < rooms.size() ; j ++){
					if(rooms.get(j).getId() == rat.get(i).getRoomId()){ //房间找到
						for(int k = 0 ; k < houses.size() ; k ++){
							if(houses.get(k).getId() == rooms.get(j).getHouseID()){ // 房屋找到
								for(int l = 0 ; l < admins.size() ; l ++){
									if(admins.get(l).getDepartmentId() == houses.get(k).getDepartmentId()){
										t.setCheckerId(admins.get(l).getId());
										t.setCheckerName(admins.get(l).getReal_name());
									}
								}
							}
						}
					}
				}
				appTenantAndHouseAndRoomService.saveTenantAndHouseAndRoom(t);
				break;
			}
		}
	}
	
	@RequestMapping(value="/checkTenantNotPass")
	@ResponseBody
    public void checkTenantNotPass(String ratID,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		
		List<AppTenantAndHouseAndRoom> rat = new ArrayList<AppTenantAndHouseAndRoom>();
		rat = appTenantAndHouseAndRoomService.doQueryAll();
		
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		List<AppRoom> rooms = new ArrayList<AppRoom>();
		rooms = appRoomService.doQueryAll();
		List<AppAdmim> admins = new ArrayList<AppAdmim>();
		admins = appAdminService.doQueryAll();
		
		for(int i = 0 ; i < rat.size() ; i ++){
			if(rat.get(i).getId() == Long.parseLong(ratID)){
				AppTenantAndHouseAndRoom t = new AppTenantAndHouseAndRoom();
				t.setId(rat.get(i).getId());
				t.setCheckOpion("不同意");
				for(int j = 0 ; j < rooms.size() ; j ++){
					if(rooms.get(j).getId() == rat.get(i).getRoomId()){ //房间找到
						for(int k = 0 ; k < houses.size() ; k ++){
							if(houses.get(k).getId() == rooms.get(j).getHouseID()){ // 房屋找到
								for(int l = 0 ; l < admins.size() ; l ++){
									if(admins.get(l).getDepartmentId() == houses.get(k).getDepartmentId()){
										t.setCheckerId(admins.get(l).getId());
										t.setCheckerName(admins.get(l).getReal_name());
									}
								}
							}
						}
					}
				}
				appTenantAndHouseAndRoomService.saveTenantAndHouseAndRoom(t);
				break;
			}
		}
	}
	@RequestMapping(value="/findByHouseId")
	@ResponseBody
    public AppUser findByHouseId(String houseid,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		List<AppHouse> houses = new ArrayList<AppHouse>();
		houses = appHouseService.doQueryAll();
		List<AppUser> users = new ArrayList<AppUser>();
		users = appUserService.doQueryAll();
		Long userid = -1l;
		for(int i = 0 ; i < houses.size(); i ++) {
			if(houses.get(i).getId() == Long.parseLong(houseid)) {
				userid = houses.get(i).getHouseOwnerId();
				for(int j = 0 ; j < users.size(); j ++) {
					if(users.get(j).getId().equals(userid)) {
						return users.get(j);
					}
				}
			}
		}
		return new AppUser();
	}
	
}

 