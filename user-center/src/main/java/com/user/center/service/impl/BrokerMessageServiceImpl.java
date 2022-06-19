package com.user.center.service.impl;

import com.user.center.entity.BrokerMessage;
import com.user.center.mapper.BrokerMessageMapper;
import com.user.center.service.BrokerMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息发送记录表 服务实现类
 * </p>
 *
 * @author yym
 * @since 2022-06-16
 */
@Service
public class BrokerMessageServiceImpl extends ServiceImpl<BrokerMessageMapper, BrokerMessage> implements BrokerMessageService {

}
