package core.web;

import com.forestry.model.sys.SysUser;

/**
 * @author 郑为中
 */
public class SessionThreadLocal {

	private static ThreadLocal<SysUser> ADMINUSERTHREADLOCAL = new ThreadLocal<SysUser>();

	public static SysUser getThreadSysUser() {
		return ADMINUSERTHREADLOCAL.get();
	}

	public static void setThreadSysUser(SysUser sysUser) {
		ADMINUSERTHREADLOCAL.set(sysUser);
	}

}
