package com.hwagain.system.order.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.framework.mybatisplus.toolkit.IdWorker;
import com.hwagain.system.order.dto.OmOrderListDto;
import com.hwagain.system.order.entity.OmOrderList;
import com.hwagain.system.order.mapper.OmOrderListMapper;
import com.hwagain.system.order.service.IOmOrderListService;
import com.hwagain.system.util.DateUtil;

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
@Service("omOrderListService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmOrderListServiceImpl extends ServiceImpl<OmOrderListMapper, OmOrderList> implements IOmOrderListService {
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OmOrderList.class, OmOrderListDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OmOrderListDto.class, OmOrderList.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}
	
	@Autowired
	OmOrderListMapper omOrderListMapper;

	@Override
	public OmOrderListDto saveOrUpdate(OmOrderListDto dto) {
		Wrapper<OmOrderList> wrapper = new CriterionWrapper<OmOrderList>(OmOrderList.class);
		wrapper.eq("fd_date", DateUtil.formatString(dto.getFdDate()));
		wrapper.eq("meal_type", dto.getMealType());
		wrapper.eq("from_type", dto.getFromType());
		wrapper.eq("user_id", dto.getUserId()); // TODO
		if(StringUtils.isEmpty(dto.getDinnerUserId())){
			wrapper.isNull("dinner_user_id");
		}else{
			wrapper.eq("dinner_user_id", dto.getDinnerUserId());
		}
		if(StringUtils.isEmpty(dto.getDinnerUserName())){
			wrapper.isNull("dinner_user_name");
		}else{
			wrapper.eq("dinner_user_name", dto.getDinnerUserName());
		}
		wrapper.eq("status", 10);
		
		OmOrderList entity = super.selectFirst(wrapper);
		if(entity != null){
			if(dto.getFdId() == null){
				dto.setFdId(entity.getFdId());
			}
			super.updateAllById(dtoToEntityMapper.map(dto, OmOrderList.class));
		} else {
			dto.setFdId(IdWorker.getId());
			super.insert(dtoToEntityMapper.map(dto, OmOrderList.class));
		}
		return dto;
	}

	@Override
	public List<OmOrderListDto> findByFromTypeAndStatus(Map<String, String> params) {
		return omOrderListMapper.findByFromTypeAndStatus(params);
	}
}
