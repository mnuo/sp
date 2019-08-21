package com.hwagain.system.base.service;

import java.util.List;

import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.system.base.dto.OmDataDictDto;
import com.hwagain.system.base.entity.OmDataDict;
import com.hwagain.system.order.dto.OmOrderListDto;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
public interface IOmDataDictService extends IService<OmDataDict> {

	List<OmDataDictDto> findAllByType(String type);

	List<OmDataDictDto> findByGroup(String type);

	List<OmDataDictDto> findByTypeAndGroup(String type, String group);
	
	OmDataDictDto findByItemNo(String itemNo);

}
