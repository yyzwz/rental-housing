package app.com.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
 
public class HttpUtils {
	public static String url(String urlAdd) {
		String data = "";
		URL url;
		try {
			url = new URL(urlAdd);
		
	        URLConnection connection = url.openConnection();
	
	
	        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
	        httpURLConnection.setConnectTimeout(1000);
	        httpURLConnection.setRequestMethod("POST");
	        httpURLConnection.setDoInput(true);
	        httpURLConnection.setDoOutput(true);
	
	        httpURLConnection.setInstanceFollowRedirects(false);
	        httpURLConnection.setUseCaches(false);
	
	        httpURLConnection.getOutputStream().write("username=admin".getBytes());
	
	        InputStream in = httpURLConnection.getInputStream();
	
	        if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
	        	 data = "";
                 byte[] b = new byte[1024];
                 int len1 ;
                 while((len1 = in.read(b))!=-1){
                     data += new String(b,0,len1);
                 }
                 in.close();
                 // System.out.println(data);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
    
    public static void main(String[] args) {
    	String ken = url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx846bd21637cca145&secret=45135d68ebe49de6fe3132b5b394f5bf");
    	System.out.println(ken);
    	ken = ken.substring(17, ken.length() - 20);
    	System.out.println(ken);
    	String str = url("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token='" + ken + "'&page='pages/TwoWeiMaLogin/TwoWeiMaLogin'&scene='20'");
		System.out.println(str);
 
    }
 
}