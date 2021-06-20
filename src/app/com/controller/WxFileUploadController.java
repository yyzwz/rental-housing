package app.com.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import app.com.model.AppTenant;
import app.com.service.AppTenantService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




@Controller
@RequestMapping("/file")
public class WxFileUploadController {

	@Resource
	private AppTenantService appTenantService; 
	
	/**
	 * 上传房屋照片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadHouseImages", method = { RequestMethod.POST,RequestMethod.GET})
    public ModelAndView uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
        MultipartFile multipartFile =  req.getFile("file");
        String houseID = (String)request.getParameter("houseID");
        houseID = houseID.substring(2, houseID.length()-2); //手动解析JSON数据 去外部双引号
        if(multipartFile != null){
        	System.out.println("获取图片成功");
        }
        String realPath=request.getServletContext().getRealPath("/")+"\\static\\img\\houseImages";
        System.out.println(realPath);
        try {
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file  =  new File(realPath, "house_"+houseID + ".jpg");
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
	 * 上传房间照片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadRoomImages", method = { RequestMethod.POST,RequestMethod.GET})
    public ModelAndView uploadRoomImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
        MultipartFile multipartFile =  req.getFile("file");
        String ROOMID = (String)request.getParameter("roomImage");
        ROOMID = ROOMID.substring(2, ROOMID.length() - 2); //手动解析JSON数据 去外部双引号
        System.out.println(ROOMID);
        if(multipartFile != null){
        	System.out.println("获取房间图片成功");
        }
        String realPath=request.getServletContext().getRealPath("/")+"\\static\\img\\houseImages";
        System.out.println("图片路劲为  = " + realPath);
        try {
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file  =  new File(realPath,  "room_" + ROOMID + ".jpg");
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
	 * 上传租客身份证照片
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadTenantImages", method = { RequestMethod.POST,RequestMethod.GET})
    public ModelAndView tenantImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进入tenantImage方法！");
        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
        MultipartFile multipartFile =  req.getFile("file");
        String TenantID = (String)request.getParameter("tenantID");
        System.out.println("Tenant id = "  + TenantID);
        if(multipartFile != null){
        	System.out.println("获取图片成功");
        }
        String realPath=request.getServletContext().getRealPath("/")+"\\static\\img\\houseImages";
        System.out.println(realPath);
        try {
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file  =  new File(realPath, "houseOwner_"+ TenantID + ".jpg");
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	
	
}
