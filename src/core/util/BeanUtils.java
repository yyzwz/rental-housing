package core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.forestry.model.sys.House;

import net.sf.json.JSONObject;


/**
 * @author 郑为中
 */
public class BeanUtils {

	private static final Log log = LogFactory.getLog(BeanUtils.class);


	public static Map describeAvailableParameter(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (bean == null) {
			return (new java.util.HashMap());
		}
		Map description = new HashMap();
		if (bean instanceof DynaBean) {
			DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass().getDynaProperties();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				description.put(name, org.apache.commons.beanutils.BeanUtils.getProperty(bean, name));
			}
		} else {
			PropertyDescriptor[] descriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(bean);
			Class clazz = bean.getClass();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (name.startsWith("$")) {
					//System.out.println("clazz===" + clazz);
					//System.out.println("descriptors[i].getReadMethod()===" + descriptors[i].getReadMethod());
					if (MethodUtils.getAccessibleMethod(clazz, descriptors[i].getReadMethod()) != null) {
						description.put(name, PropertyUtils.getNestedProperty(bean, name));
					}
				}
			}
		}
		return (description);
	}

	// revise BeanUtils describe method do not copy data type
	public static Map describe(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (bean == null) {
			return (new java.util.HashMap());
		}
		Map description = new HashMap();
		if (bean instanceof DynaBean) {
			DynaProperty[] descriptors = ((DynaBean) bean).getDynaClass().getDynaProperties();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				description.put(name, org.apache.commons.beanutils.BeanUtils.getProperty(bean, name));
			}
		} else {
			PropertyDescriptor[] descriptors = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptors(bean);
			Class clazz = bean.getClass();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (MethodUtils.getAccessibleMethod(clazz, descriptors[i].getReadMethod()) != null) {
					description.put(name, PropertyUtils.getNestedProperty(bean, name));
				}
			}
		}
		return (description);
	}

	private static Set<String> notCopyProperties = new HashSet<String>();

	static {
		notCopyProperties.add("class");
		notCopyProperties.add("propName");
		notCopyProperties.add("propValue");
		notCopyProperties.add("currentPage");
		notCopyProperties.add("maxResults");
		notCopyProperties.add("sortColumns");
		notCopyProperties.add("cmd");
	}

	public static void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled()) {
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}

		// Copy the properties, converting as necessary
		BeanUtilsBean bub = BeanUtilsBean.getInstance();
		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					bub.copyProperty(dest, name, value);
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (bub.getPropertyUtils().isWriteable(dest, name)) {
					bub.copyProperty(dest, name, entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = bub.getPropertyUtils().getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if (notCopyProperties.contains(name) || name.startsWith("$")) {
					continue; // No point in trying to set an object's class
				}
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					try {
						Object value = bub.getPropertyUtils().getSimpleProperty(orig, name);
						bub.copyProperty(dest, name, value);
					} catch (NoSuchMethodException e) {
						// Should not happen
					}
				}
			}
		}
	}

	public static void copyPropertiesExceptNull(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled()) {
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}

		// Copy the properties, converting as necessary
		BeanUtilsBean bub = BeanUtilsBean.getInstance();
		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					bub.copyProperty(dest, name, value);
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (bub.getPropertyUtils().isWriteable(dest, name)) {
					bub.copyProperty(dest, name, entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = bub.getPropertyUtils().getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if ("class".equals(name) || "propName".equals(name) || "propValue".equals(name)) {
					continue; // No point in trying to set an object's class
				}
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					try {
						Object value = bub.getPropertyUtils().getSimpleProperty(orig, name);
						if (value != null) {
							bub.copyProperty(dest, name, value);
						}
					} catch (NoSuchMethodException e) {
						// Should not happen
					}
				}
			}
		}
	}

	public static void copyProperties(Object dest, Object orig, List<String> excludedProp) throws IllegalAccessException, InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		if (log.isDebugEnabled()) {
			log.debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");
		}

		// Copy the properties, converting as necessary
		BeanUtilsBean bub = BeanUtilsBean.getInstance();
		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					bub.copyProperty(dest, name, value);
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (bub.getPropertyUtils().isWriteable(dest, name)) {
					bub.copyProperty(dest, name, entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = bub.getPropertyUtils().getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if ("class".equals(name) || excludedProp.contains(name)) {
					continue; // No point in trying to set an object's class
				}
				if (bub.getPropertyUtils().isReadable(orig, name) && bub.getPropertyUtils().isWriteable(dest, name)) {
					try {
						Object value = bub.getPropertyUtils().getSimpleProperty(orig, name);
						bub.copyProperty(dest, name, value);
					} catch (NoSuchMethodException e) {
						// Should not happen
					}
				}
			}
		}
	}
	/**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     * */
	public static ArrayList getFiledsInfo(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        ArrayList list = new ArrayList();
        Map infoMap=null;
        for(int i=0;i<fields.length;i++){
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }
   
    /**
     * 根据属性名获取属性值
     * */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {  
            String firstLetter = fieldName.substring(0, 1).toUpperCase();  
            String getter = "get" + firstLetter + fieldName.substring(1);  
            Method method = o.getClass().getMethod(getter, new Class[] {});  
            Object value = method.invoke(o, new Object[] {});  
            return value;  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
            return null;  
        }  
    } 
    
    /**
     * 获取属性名数组
     * */
    public static String[] getFiledName(Object o){
        Field[] fields=o.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            System.out.println(fields[i].getType());
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    } 
    
    /**
     * 获取属性名数组
     * */
    public static String getValuleInObject(Object o){
        String  result="";
        if(o!=null)
        	result=o.toString();
        return result;
    } 
    /**
     * 字符转Date
     * */
    public static java.sql.Date getDateOfString(String str){
    	java.sql.Date  result=null;
    	if(str==null)
    		return result;
		try {
			result = java.sql.Date.valueOf(strToDateFormat(str));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    } 
    /**
     *将字符串格式yyyyMMdd的字符串转为日期，格式"yyyy-MM-dd"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static String strToDateFormat(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yy年MM月dd日");
        formatter.setLenient(false);
        Date newDate= formatter.parse(date);
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(newDate);
    }
    
    /**
     *将字符串格式yyyyMMdd的字符串转为日期，格式"yyyy-MM-dd"
     *
     * @param date 日期字符串
     * @return 返回格式化的日期
     * @throws ParseException 分析时意外地出现了错误异常
     */
    public static String getCurrentWithString() throws ParseException {
    	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
    	Date date = new Date(System.currentTimeMillis());
    	return formatter.format(date);
    }
    
    /**
	 * 根据身份证号计算年龄
	 * @param pensonnelIdCard
	 * @return
	 */
	public static Integer getPersonAgeFromIdCard(String pensonnelIdCard) {
		if (!cardCodeVerifySimple(pensonnelIdCard))
			return 0;
		//截取身份证中出行人出生日期中的年、月、日
		Integer personYear = Integer.parseInt(pensonnelIdCard.substring(6, 10));
		Integer personMonth = Integer.parseInt(pensonnelIdCard.substring(10, 12));
		Integer personDay = Integer.parseInt(pensonnelIdCard.substring(12, 14));
		
		Calendar cal = Calendar.getInstance();
		// 得到当前时间的年、月、日
		Integer yearNow = cal.get(Calendar.YEAR);
		Integer monthNow = cal.get(Calendar.MONTH) + 1;
		Integer dayNow = cal.get(Calendar.DATE);
		
		// 用当前年月日减去生日年月日
		Integer yearMinus = yearNow - personYear;
		Integer monthMinus = monthNow - personMonth;
		Integer dayMinus = dayNow - personDay;
 
		Integer age = yearMinus; //先大致赋值
		
		if (yearMinus == 0) { //出生年份为当前年份 
			age = 0;
		}else{ //出生年份大于当前年份
			if (monthMinus < 0) {//出生月份小于当前月份时，还没满周岁
				age = age - 1;
			}
			if (monthMinus == 0) {//当前月份为出生月份时，判断日期
				if (dayMinus < 0) {//出生日期小于当前月份时，没满周岁
				   age = age - 1;
				}
			} 
		}
		return age;
	}
	
	static boolean cardCodeVerifySimple(String cardcode) {
	     //第一代身份证正则表达式(15位)
	     String isIDCard1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
	     //第二代身份证正则表达式(18位)
	     String isIDCard2 ="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$";
	 
	     //验证身份证
	     if (cardcode.matches(isIDCard2)) {
	         return true;
	     }
	     return false;
	 }
	
	/**
    * 获取当前系统时间最近12月的年月（含当月）
    * 2018-04~2019-03
    */
    public static String[] getLatest12Month(){

    	String[] last12Months = new String[12];
        Calendar cal = Calendar.getInstance();
        //如果当前日期大于二月份的天数28天或者29天会导致计算月份错误，会多出一个三月份，故设置一个靠前日期解决此问题
        cal.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < 12; i++) {
        	if((cal.get(Calendar.MONTH) + 1)<10)
        		last12Months[11 - i] = cal.get(Calendar.YEAR) + "-0" + (cal.get(Calendar.MONTH) + 1);
        	else
        		last12Months[11 - i] = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1);
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); //逐次往前推1个月
        }
        for (int i = 0; i < last12Months.length; i++) {
            System.out.println(last12Months[i]);
        }
        return last12Months;

    }


    public static void main(String args[]) throws ParseException { 
    	
    	//将java对象转换为json对象
    	//String qmm="Sat Apr 04 2020 00:00:00 GMT 0800 (中国标准时间)";
    	//System.out.println(strToDateFormat("20年03月28日"));
		//System.out.println(getDateOfString(qmm));
    	//System.out.println(getPersonAgeFromIdCard("330281199908010011"));
    	System.out.println(getLatest12Month());
		
    	
    	 
    	


    } 
}
