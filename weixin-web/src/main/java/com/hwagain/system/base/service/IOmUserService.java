package com.hwagain.system.base.service;

import com.hwagain.system.base.dto.OmUserDto;
import com.hwagain.system.base.entity.OmUser;
import com.hwagain.framework.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mon
 * @since 2019-08-09
 */
public interface IOmUserService extends IService<OmUser> {

	boolean saveOne(OmUserDto dto);

	String sendCheckCode(String phone);

	OmUserDto login(String phone, String vcode);

	OmUserDto checklogin(String authorization);
	
}
