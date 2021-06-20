package app.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forestry.model.sys.TenantQuery;

import app.com.model.AppHouse;
import app.com.model.StudentDemo;
import app.com.service.AppHouseService;
import app.com.service.AppTenantService;
import core.support.BaseParameter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class AppTestController {

	@Resource
	private AppHouseService appHouseService;
	
	@Resource
	private AppTenantService appTenantService;
	
	@ResponseBody
	@RequestMapping(value = "/erweimalogin", method = { RequestMethod.POST, RequestMethod.GET })
    public String testerweimalogin(HttpServletResponse response) throws ServletException, IOException, IOException {

		return "running end!";
	}
	
	@ResponseBody
	@RequestMapping(value = "/hello", method = { RequestMethod.POST, RequestMethod.GET })
    public String getTenant(HttpServletResponse response) throws ServletException, IOException, IOException {
		
//		AppHouse appHouse = new AppHouse();
//		appHouse.setHouseAddress("余姚市阳明街道XXXXX");
//		appHouse.setHouseName("我的家");
//		appHouseService.persist(appHouse);
//		System.out.println(appHouse.getId());
//
//		appHouseService.delete(appHouse);
		
//		AppHouse appHouse = new AppHouse();
//		appHouse = appHouseService.get(13l);
//		List<AppHouse> houses = new ArrayList<AppHouse>();
//		houses = appHouseService.queryByProerties("houseAddress", "余姚市阳明街道XXXX");
		
		String sql = "select * from tenant";

		TenantQuery tenant = new TenantQuery();
		List<BaseParameter> arr=appTenantService.creatNativeSqlQuery(sql, tenant);
		
		
		System.out.println("end!!");
	
		return "running end!";
	}
}
