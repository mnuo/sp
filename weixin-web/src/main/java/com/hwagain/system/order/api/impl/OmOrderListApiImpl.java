package com.hwagain.system.order.api.impl;

import com.hwagain.system.order.api.IOmOrderListApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@Service("omOrderListApi")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmOrderListApiImpl implements IOmOrderListApi {
	
}
