package app.com.service;

import java.util.List;

import app.com.model.AppFWType;

import core.service.Service;

/**
 * @author 齐鸣鸣
 */
public interface AppFWTypeService extends Service<AppFWType> {

	List<Object[]> queryExportedFWType(Long[] ids);
}
