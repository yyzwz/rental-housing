package app.com.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:这个程序员有纹身
 * @Date: Created in 21:24 2018\1\9 0009
 */
public class HttpServletRequestUtil {

    public static int getInt(HttpServletRequest request, String key) {
        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    public static Double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1d;
        }
    }

    public static boolean getBoolean(HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if (result != null) {
                result = result.trim();
            }
            if ("".equals(result)) {
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getObjectArray(HttpServletRequest request, String key){
        try {
            String newResult = "";
            String[] arrays = request.getParameterValues(key);
            System.err.println("arrays.toString(): " + arrays.toString());
            if (arrays != null){
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < arrays.length; i++){
                    stringBuffer.append(arrays[i]);
                }
                newResult = stringBuffer.toString();
            }
            return newResult;
        } catch (Exception e) {
            return null;
        }
    }

}
