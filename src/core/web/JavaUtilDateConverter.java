package core.web;

import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class JavaUtilDateConverter implements Converter {

	public Object convert(Class type, Object value) {
		if (value instanceof Date)
			return value;

		String p = (String) value;
		if (p == null || p.trim().length() == 0) {
			return null;
		}
		try {
			return DateUtils.parseDate(p, new String[] { "yyyy-MM-dd HH:mm:ss" });
		} catch (Exception e) {
			throw new ConversionException("unexpected type:" + value.getClass());
		}
	}

}