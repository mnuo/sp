package com.hwagain.system.order.service.impl;

import com.hwagain.system.order.entity.OmOrderUser;
import com.hwagain.system.order.dto.OmOrderUserDto;
import com.hwagain.system.order.mapper.OmOrderUserMapper;
import com.hwagain.system.order.service.IOmOrderUserService;
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
 * @author mon
 * @since 2019-08-16
 */
@Service("omOrderUserService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmOrderUserServiceImpl extends ServiceImpl<OmOrderUserMapper, OmOrderUser> implements IOmOrderUserService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OmOrderUser.class, OmOrderUserDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OmOrderUserDto.class, OmOrderUser.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
}
