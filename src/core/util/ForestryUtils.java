package core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.math.RandomUtils;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class ForestryUtils {

	public static String getRandomString(int i) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < i; j++) {
			sb.append(String.valueOf(RandomUtils.nextInt(10)));
		}
		return sb.toString();
	}

	public static String stringFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

}
