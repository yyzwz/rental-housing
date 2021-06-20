package com.forestry.dao.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.forestry.model.sys.HouseOwner;

import core.dao.Dao;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public interface HouseOwnerDao extends Dao<HouseOwner> {
	
	List<Object[]> queryExportedHouseOwner(Long[] ids);

	int CheckHouseOwner(Long[] ids,String checkOption);
    void updateHouseOwner(HouseOwner HouseOwner);
    int saveHouseOwnerTwoDimensionalCode(HttpServletRequest request, String filePath,Long id);//saveHouseOwnerTwoDimensionalCode
}
