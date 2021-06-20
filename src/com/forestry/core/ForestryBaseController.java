package com.forestry.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.forestry.model.sys.SysUser;

import core.controller.ExtJSBaseController;
import core.extjs.ExtJSBaseParameter;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public abstract class ForestryBaseController<E extends ExtJSBaseParameter> extends ExtJSBaseController<E> implements Constant {

	public SysUser getCurrentSysUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return (SysUser) request.getSession().getAttribute(SESSION_SYS_USER);
	}

}
