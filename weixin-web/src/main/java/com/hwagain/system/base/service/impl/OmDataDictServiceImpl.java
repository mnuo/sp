package com.hwagain.system.base.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.hwagain.framework.mybatisplus.mapper.CriterionWrapper;
import com.hwagain.framework.mybatisplus.mapper.Wrapper;
import com.hwagain.framework.mybatisplus.service.impl.ServiceImpl;
import com.hwagain.system.base.dto.OmDataDictDto;
import com.hwagain.system.base.entity.OmDataDict;
import com.hwagain.system.base.mapper.OmDataDictMapper;
import com.hwagain.system.base.service.IOmDataDictService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
@Service("omDataDictService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmDataDictServiceImpl extends ServiceImpl<OmDataDictMapper, OmDataDict> implements IOmDataDictService {
	private final static Logger logger = LoggerFactory.getLogger(OmDataDictServiceImpl.class);
	
	// entity转dto
	static MapperFacade entityToDtoMapper;
	
	// dto转entity
	static MapperFacade dtoToEntityMapper;

	static {
		MapperFactory factory = new DefaultMapperFactory.Builder().build();
		factory.classMap(OmDataDict.class, OmDataDictDto.class).byDefault().register();
		entityToDtoMapper = factory.getMapperFacade();
		
		MapperFactory factorytwo = new DefaultMapperFactory.Builder().build();
		factorytwo.classMap(OmDataDictDto.class, OmDataDict.class).byDefault().register();
		dtoToEntityMapper = factorytwo.getMapperFacade();
	}

	@Override
	public List<OmDataDictDto> findAllByType(String type) {
		Wrapper<OmDataDict> wrapper=new CriterionWrapper<OmDataDict>(OmDataDict.class);
		wrapper.eq("type_name", type);//有效状态
		List<OmDataDict> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OmDataDictDto.class);
	}

	@Override
	public List<OmDataDictDto> findByGroup(String type) {
		Wrapper<OmDataDict> wrapper=new CriterionWrapper<OmDataDict>(OmDataDict.class);
		wrapper.eq("group_code", type);//有效状态
		List<OmDataDict> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OmDataDictDto.class);
	}

	@Override
	public List<OmDataDictDto> findByTypeAndGroup(String type, String group) {
		Wrapper<OmDataDict> wrapper=new CriterionWrapper<OmDataDict>(OmDataDict.class);
		wrapper.eq("type_name", type);//有效状态
		
		if(StringUtils.isEmpty(group)){
			wrapper.isNull("group_code");
		}else{
			String[] arr = group.split(",");
			StringBuffer sb = new StringBuffer();
			for(String str : arr){
				sb.append(" group_code='" + str + "' or");
			}
			logger.info(sb.substring(0, sb.length()-2));
			wrapper.addFilter(sb.substring(0, sb.length()-2));
		}
		
		List<OmDataDict> list = super.selectList(wrapper);
		return entityToDtoMapper.mapAsList(list, OmDataDictDto.class);
	}

	@Override
	public OmDataDictDto findByItemNo(String itemNo) {
		Wrapper<OmDataDict> wrapper=new CriterionWrapper<OmDataDict>(OmDataDict.class);
		wrapper.eq("item_no", itemNo);//有效状态
		
		OmDataDict entity = super.selectFirst(wrapper);
		return entityToDtoMapper.map(entity, OmDataDictDto.class);
	}
}
