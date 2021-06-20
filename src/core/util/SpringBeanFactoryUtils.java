package core.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.forestry.core.Constant;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class SpringBeanFactoryUtils implements BeanFactoryAware {

	private static BeanFactory beanFactory = null;
	private static SpringBeanFactoryUtils factoryUtils = null;

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		SpringBeanFactoryUtils.beanFactory = beanFactory;
	}

	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public static SpringBeanFactoryUtils getInstance() {
		if (factoryUtils == null) {
			// 可以使用单例模式
			// factoryUtils = (SpringBeanFactoryUtils)beanFactory.getBean("springBeanFactoryUtils");
			factoryUtils = new SpringBeanFactoryUtils();
		}
		return factoryUtils;
	}

	public static Object getBean(String name) {
		return beanFactory.getBean(name);
	}

	public static Connection getScsConnection() throws Exception {
		DataSource dataSource = (DataSource) SpringBeanFactoryUtils.getBean(Constant.FORESTRY_DATA_SOURCE_BEAN_ID);
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			throw e;
		}
		return con;
	}

}
