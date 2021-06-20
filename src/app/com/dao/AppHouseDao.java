package app.com.dao;

import javax.servlet.http.HttpServletRequest;

import app.com.model.AppHouse;
import core.dao.Dao;

/**
 * @author 郑为中
 */

public interface AppHouseDao extends Dao<AppHouse> {
	int saveHouseTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);
}
