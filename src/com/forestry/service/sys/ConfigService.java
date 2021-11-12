package com.forestry.service.sys;

import java.util.List;

import com.forestry.model.sys.Config;

import core.service.Service;

/**
 * @author 郑为中
 */
public interface ConfigService extends Service<Config> {

	List<Config> getConfigList(List<Config> resultList);

}
