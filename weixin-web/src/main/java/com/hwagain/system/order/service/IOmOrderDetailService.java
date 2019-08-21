package com.hwagain.system.order.service;

import com.hwagain.system.order.dto.OmOrderDetailDto;
import com.hwagain.system.order.entity.OmOrderDetail;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
public interface IOmOrderDetailService extends IService<OmOrderDetail> {

	boolean insert(OmOrderDetailDto detailDto);
	
}
