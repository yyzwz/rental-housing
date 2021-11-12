package app.com.service;

import java.util.List;

import app.com.model.AppFWType;

import core.service.Service;

/**
 * @author 郑为中
 */
public interface AppFWTypeService extends Service<AppFWType> {

	List<Object[]> queryExportedFWType(Long[] ids);
}
