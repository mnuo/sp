package com.hwagain.system.recipe.api.impl;

import com.hwagain.system.recipe.api.IOmRecipeApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 菜单表-普通菜 服务实现类
 * </p>
 *
 * @author mon
 * @since 2019-08-08
 */
@Service("omRecipeApi")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OmRecipeApiImpl implements IOmRecipeApi {
	
}
