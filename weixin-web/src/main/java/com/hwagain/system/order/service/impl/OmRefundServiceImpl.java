package com.hwagain.system.order.service.impl;

import com.hwagain.system.order.entity.OmRefund;
import com.hwagain.system.order.dto.OmRefundDto;
import com.hwagain.system.order.mapper.OmRefundMapper;
import com.hwagain.system.order.service.IOmRefundService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 退款表 服务实现类
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@Service("omRefundService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmRefundServiceImpl extends ServiceImpl<OmRefundMapper, OmRefund> implements IOmRefundService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OmRefund.class, OmRefundDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OmRefundDto.class, OmRefund.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
}
