package com.hwagain.system.base.api.impl;

import com.hwagain.system.base.api.IWxUserApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2019-08-21
 */
@Service("wxUserApi")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class WxUserApiImpl implements IWxUserApi {
	
}
