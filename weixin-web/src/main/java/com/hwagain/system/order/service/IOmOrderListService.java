package com.hwagain.system.order.service;

import java.util.List;
import java.util.Map;

import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.system.order.dto.OmOrderListDto;
import com.hwagain.system.order.entity.OmOrderList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
public interface IOmOrderListService extends IService<OmOrderList> {

	OmOrderListDto saveOrUpdate(OmOrderListDto dto);

	List<OmOrderListDto> findByFromTypeAndStatus(Map<String, String> params);
	
}
