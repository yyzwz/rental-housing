package app.com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import app.com.model.AppPeople;
import app.com.service.AppPeopleService;

@Controller
@RequestMapping("/people")
public class AppPeopleController {

	@Resource
	private AppPeopleService appPeopleService;
	
	@ResponseBody
	@RequestMapping(value = "/test", method = { RequestMethod.POST,RequestMethod.GET})
    public List<AppPeople> test(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
		List<AppPeople> peoples = new ArrayList<AppPeople>();
		
//		appPeopleService.persist(new AppPeople("zwz01",18,"yuyao")); //插入
		
//		appPeopleService.deleteByPK(2l); //指定ID 删除
		
		
		
		peoples = appPeopleService.doQueryAll();
		return peoples;
	}
}
