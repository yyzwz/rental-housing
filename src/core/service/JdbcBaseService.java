package core.service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import core.dao.JdbcBaseDao;

/**
 * @author 郑为中
 */
@Transactional
public class JdbcBaseService {

	@Resource
	protected JdbcBaseDao jdbcBaseDao;

}
