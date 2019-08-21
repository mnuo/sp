package com.hwagain.system.order.service;

import java.util.List;

import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.system.order.dto.OmOrderDto;
import com.hwagain.system.order.dto.OmOrderListDto;
import com.hwagain.system.order.entity.OmOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
public interface IOmOrderService extends IService<OmOrder> {

	boolean saveOneOrderList(String token, OmOrderListDto dto);

	boolean saveBatchOrderList(String token, List<OmOrderListDto> dto);

	boolean submitOrder(String token, OmOrderDto dto);

	List<OmOrderListDto> findByFromTypeAndStatus(String token, String fromType, String string);

	List<OmOrderListDto> findOrderByFromType(String token, String fromType);

	boolean deleteOneNotGenericOrder(String token, String fdId);
	
}
