package com.hwagain.system.weixin.handler;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;


public abstract class AbstractHandler implements WxMpMessageHandler {
    protected Logger logger = LoggerFactory.getLogger(AbstractHandler.class);
}
