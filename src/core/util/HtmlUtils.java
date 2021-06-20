package core.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class HtmlUtils {

	/**
	 * omit string
	 * 
	 * @param strText original string
	 * @param KeepLen expect length
	 * @return
	 */
	public static String omitString(String strText, int KeepLen) {
		String strOmit = "... ";
		if (strText == null)
			return "";
		if (((strText + strOmit).length()) <= KeepLen)
			return strText;
		else
			try {
				return (strText.substring(0, KeepLen - 2) + strOmit);
			} catch (Exception e) {
				return strText;
			}
	}

	/**
	 * remove html tag and "&nbsp;"
	 * 
	 * @param input original string
	 * @param length expect length
	 * @return
	 */
	public static String removeHTML(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		String str = input.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "");
		str = str.replaceAll("&nbsp;", "");
		if (length > 0) {
			str = omitString(str, length);
		}
		return str;
	}

	/**
	 * 滤除帖子中的危险 HTML 代码, 主要是脚本代码, 滚动字幕代码以及脚本事件处理代码
	 * 
	 * @param content 需要滤除的字符串
	 * @return 过滤的结果
	 */
	public static String replaceHtmlCode(String content) {
		if (StringUtils.isEmpty(content)) {
			return "";
		} // 需要滤除的脚本事件关键字
		String[] eventKeywords = { "onmouseover", "onmouseout", "onmousedown", "onmouseup", "onmousemove", "onclick", "ondblclick", "onkeypress", "onkeydown", "onkeyup", "ondragstart", "onerrorupdate", "onhelp",
				"onreadystatechange", "onrowenter", "onrowexit", "onselectstart", "onload", "onunload", "onbeforeunload", "onblur", "onerror", "onfocus", "onresize", "onscroll", "oncontextmenu" };
		content = StringUtils.replaceChars(content, "<script", "&ltscript");
		content = StringUtils.replace(content, "</script", "&lt/script");
		content = StringUtils.replace(content, "<marquee", "&ltmarquee");
		content = StringUtils.replace(content, "</marquee", "&lt/marquee");
		// FIXME 加这个过滤换行到 BR 的功能会把原始 HTML 代码搞乱 2006-07-30
		// content = replace(content, "\r\n", "<BR>");
		// 滤除脚本事件代码
		for (int i = 0; i < eventKeywords.length; i++) {
			content = StringUtils.replace(content, eventKeywords[i], "_" + eventKeywords[i]);
			// 添加一个"_", 使事件代码无效
		}
		return content;
	}

	/**
	 * 去除html代码
	 * 
	 * @param inputString
	 * @return
	 */
	public static String htmltoText(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_ba;
		java.util.regex.Matcher m_ba;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>}
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>}
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String patternStr = "\\s+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
			m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll(""); // 过滤空格

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}
}
