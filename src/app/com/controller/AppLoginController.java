package app.com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forestry.core.ForestryBaseController;

import app.com.model.AppAdmim;
import app.com.model.AppUser;
import app.com.service.AppAdminService;
import app.com.service.AppUserService;

/**
 * 	@author 郑为中
 */

@Controller
@RequestMapping("/login")
public class AppLoginController extends ForestryBaseController<app.com.model.AppUser> {

	@Resource
	private AppUserService appUserService;
	
	@Resource
	private AppAdminService appAdminService;
	
	/**
	 * 
	 * 登入界面 使用手机号快捷登入
	 * 
	 * 1. 首先找 是否为管理员账号，找到则返回管理员所在村名
	 * 2. 没找到 则找房东，找到则返回房东ID
	 * 3. 如果都没找到 返回none
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
			ans.add(""+users.get(0).getId()); //返回房东ID
			ans.add("" + users.get(0).getHouseOwnerCode());
			ans.add("" + users.get(0).getHouseOwnerTel());
			ans.add("" + users.get(0).getHouseOwnerName());
			return ans;
		}
		ans.add("none");//找不到
		return ans;
	}

	/**
	 * 
	 *  账号普通登入
	 *  
	 *  1.登入成功 返回房东ID
	 *  
	 *  2.登入失败 返回 no
	 * 
	 *  郑为中
	 */
	@RequestMapping(value="/login")
	@ResponseBody
    public List<String> Login(String logincode,String loginpassword,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		List<String> ans = new ArrayList<String>();
		// 过渡数组
		ArrayList<String> arrPropNames = new ArrayList<String>();
		ArrayList<Object> arrPropValues = new ArrayList<Object>();
		//查询条件
		arrPropNames.add("houseOwnerCode");
		arrPropNames.add("houseOwnerPassWord");
		arrPropValues.add(logincode);
		arrPropValues.add(loginpassword);
		//queryByProerties 要传的数组
		String[] propNames= new String[arrPropNames.size()];
		Object[] propValues= new Object[arrPropNames.size()];
		//强制转换
		for(int i=0;i<arrPropNames.size();i++) {
			propNames[i]=(String)arrPropNames.get(i);
			propValues[i]=arrPropValues.get(i);
		}
		List<AppUser> users = appUserService.queryByProerties(propNames, propValues);
		if(!users.isEmpty()){
			ans.add("" + users.get(0).getId());
			ans.add("" + users.get(0).getHouseOwnerCode());
			ans.add("" + users.get(0).getHouseOwnerTel());
			ans.add("" + users.get(0).getHouseOwnerName());
		}
		else{
			ans.add("no");
		}
		return ans;
		
	}
	/**
	 * 
	 *  账号注册 功能
	 *  
	 *  1.账号已存在 返回 exist
	 *  
	 *  2.注册成功  返回 房东账号
	 * 
	 *  郑为中
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
    public List<String> HouseWonerRegister(String code,String password,String name,
    		String phone, String idcard,String desc,String address,
                            HttpServletResponse response) throws ServletException, IOException, IOException {
		List<String> ans = new ArrayList<String>();
		ArrayList<String> arrPropNames = new ArrayList<String>();
		ArrayList<Object> arrPropValues = new ArrayList<Object>();
		arrPropNames.add("houseOwnerTel");
		arrPropValues.add(phone);
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
        ans.add("" + appUser.getId());
        ans.add("" + appUser.getHouseOwnerCode());
		ans.add("" + appUser.getHouseOwnerTel());
		ans.add("" + appUser.getHouseOwnerName());
        return ans;
    }
	
	/**
	 * 
	 * 小程序房东主页 ，根据房东ID， 找姓名
	 */
	@ResponseBody
	@RequestMapping(value = "/findNameById", method = { RequestMethod.POST, RequestMethod.GET })
    public List<String> FindNameByCode(String id,
    		HttpServletResponse response)throws ServletException, IOException, IOException{
		List<String> ans = new ArrayList<String>();
		if(id.equals("")){
			ans.add("none");
			return ans;
		}
		ArrayList<String> arrPropNames = new ArrayList<String>();
		ArrayList<Object> arrPropValues = new ArrayList<Object>();
		arrPropNames.add("id");
		arrPropValues.add(Long.valueOf(id));
		String[] propNames= new String[arrPropNames.size()];
		Object[] propValues= new Object[arrPropNames.size()];
		for(int i=0;i<arrPropNames.size();i++) {
			propNames[i]=(String)arrPropNames.get(i);
			propValues[i]=arrPropValues.get(i);
		}
		List<AppUser> users = appUserService.queryByProerties(propNames, propValues);
		if(!users.isEmpty()){
			ans.add(users.get(0).getHouseOwnerName());
		}
		else{
			ans.add("none");
		}
		return ans;
	}
	
}
