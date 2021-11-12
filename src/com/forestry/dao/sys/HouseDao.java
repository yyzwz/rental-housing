package com.forestry.dao.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.model.sys.House;
import com.forestry.model.sys.SysUser;

import core.dao.Dao;

/**
 * @author 郑为中
 */
public interface HouseDao extends Dao<House> {
	
	List<Object[]> queryExportedHouse(Long[] ids);

	
    void updateHouse(House house);
    int saveHouseTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);//saveHouseTwoDimensionalCode
}
