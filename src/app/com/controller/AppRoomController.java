package app.com.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forestry.core.ForestryBaseController;

import app.com.service.AppHouseService;
import app.com.service.AppRoomAndTenantService;
import app.com.service.AppRoomService;

/**
 * @author 郑为中
 */

@Controller
@RequestMapping("/room")
public class AppRoomController extends ForestryBaseController<app.com.model.AppRoom>{

	@Resource
	private AppHouseService appHouseService;
	
	@Resource
	private AppRoomService appRoomService;
	
	@Resource
	private AppRoomAndTenantService appRoomAndTenantService;
	
	
	
	@ResponseBody
	@RequestMapping(value = "/myHouseList", method = { RequestMethod.POST, RequestMethod.GET })
	public void testPost(String houseOwnerId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
	
	}
}
