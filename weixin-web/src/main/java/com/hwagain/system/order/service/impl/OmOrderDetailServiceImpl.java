package com.hwagain.system.order.service.impl;

import com.hwagain.system.order.entity.OmOrderDetail;
import com.hwagain.system.order.dto.OmOrderDetailDto;
import com.hwagain.system.order.mapper.OmOrderDetailMapper;
import com.hwagain.system.order.service.IOmOrderDetailService;
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
@Service("omOrderDetailService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmOrderDetailServiceImpl extends ServiceImpl<OmOrderDetailMapper, OmOrderDetail> implements IOmOrderDetailService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OmOrderDetail.class, OmOrderDetailDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OmOrderDetailDto.class, OmOrderDetail.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public boolean insert(OmOrderDetailDto detailDto) {
		return super.insert(dtoToEntityMapper.map(detailDto, OmOrderDetail.class));
	}
}
