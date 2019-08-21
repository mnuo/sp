package com.hwagain.system.recipe.service;

import java.util.Map;

import com.hwagain.framework.mybatisplus.service.IService;
import com.hwagain.system.recipe.entity.OmRecipe;

/**
 * <p>
 * 菜单表-普通菜 服务类
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
public interface IOmRecipeService extends IService<OmRecipe> {
	Map<String, Object> findDingcByDate(Map<String, String> params);

	Map<String, Object> findDiancByDate(Map<String, String> params);

	Map<String, Object> findZhaodByDate(Map<String, String> params);

	Map<String, Object> findWeixByDate(Map<String, String> params);
}
