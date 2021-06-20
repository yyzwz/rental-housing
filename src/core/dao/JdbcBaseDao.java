package core.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class JdbcBaseDao extends JdbcDaoSupport {

	@Resource(name = "dataSource")
	public void setDS(DataSource dataSource) {
		setDataSource(dataSource);
	}

}
