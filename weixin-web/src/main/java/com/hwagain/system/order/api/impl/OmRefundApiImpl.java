package com.hwagain.system.order.api.impl;

import com.hwagain.system.order.api.IOmRefundApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 退款表 服务实现类
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@Service("omRefundApi")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmRefundApiImpl implements IOmRefundApi {
	
}
