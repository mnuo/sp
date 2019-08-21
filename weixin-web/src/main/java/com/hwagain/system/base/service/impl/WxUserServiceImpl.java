package com.hwagain.system.base.service.impl;

import com.hwagain.system.base.entity.WxUser;
import com.hwagain.system.base.dto.WxUserDto;
import com.hwagain.system.base.mapper.WxUserMapper;
import com.hwagain.system.base.service.IWxUserService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2019-08-21
 */
@Service("wxUserService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements IWxUserService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(WxUser.class, WxUserDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(WxUserDto.class, WxUser.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
}
