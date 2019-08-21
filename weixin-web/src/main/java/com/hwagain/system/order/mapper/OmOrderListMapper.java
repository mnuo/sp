package com.hwagain.system.order.mapper;

import com.hwagain.system.order.dto.OmOrderListDto;
import com.hwagain.system.order.entity.OmOrderList;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hwagain.framework.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mon
 * @since 2019-08-16
 */
public interface OmOrderListMapper extends BaseMapper<OmOrderList> {

	List<OmOrderListDto> findByFromTypeAndStatus(@Param("params") Map<String, String> params);

}