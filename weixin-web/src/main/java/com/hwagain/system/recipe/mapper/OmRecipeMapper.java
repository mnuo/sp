package com.hwagain.system.recipe.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hwagain.framework.mybatisplus.mapper.BaseMapper;
import com.hwagain.system.recipe.dto.OmRecipeDto;
import com.hwagain.system.recipe.dto.RecipeRpsDto;
import com.hwagain.system.recipe.entity.OmRecipe;

/**
 * <p>
  * 菜单表-普通菜 Mapper 接口
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
public interface OmRecipeMapper extends BaseMapper<OmRecipe> {
	List<OmRecipeDto> findByParams(@Param("params") Map<String, String> params);

	List<RecipeRpsDto> findBaseDisheByType(@Param("params") Map<String, String> params);
}