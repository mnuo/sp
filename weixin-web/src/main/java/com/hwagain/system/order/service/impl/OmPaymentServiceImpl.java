package com.hwagain.system.order.service.impl;

import com.hwagain.system.order.entity.OmPayment;
import com.hwagain.system.order.dto.OmPaymentDto;
import com.hwagain.system.order.mapper.OmPaymentMapper;
import com.hwagain.system.order.service.IOmPaymentService;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 支付表 服务实现类
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
@Service("omPaymentService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmPaymentServiceImpl extends ServiceImpl<OmPaymentMapper, OmPayment> implements IOmPaymentService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OmPayment.class, OmPaymentDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OmPaymentDto.class, OmPayment.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
}
