/*
 *  Copyright 1999-2021 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.work.order.service;

import com.work.order.feign.AccountFeignClient;
import com.work.order.model.Order;
import com.work.order.repository.OrderDAO;
import io.seata.core.context.RootContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Program Name: springcloud-nacos-seata
 * <p>
 * Description:
 * <p>
 *
 * @author zhangjianwei
 * @version 1.0
 * @date 2019/8/28 4:05 PM
 */
@Service
public class OrderService {

    @Resource
    private AccountFeignClient accountFeignClient;

    @Resource
    private OrderDAO orderDAO;



    @Transactional(rollbackFor = Exception.class)
    public void create(String userId, String commodityCode, Integer count) {

        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));

        Order order = new Order().setUserId(userId).setCommodityCode(commodityCode).setCount(count).setMoney(
            orderMoney);
        order.setId(UUID.randomUUID().toString());
        orderDAO.insert(order);
        System.out.println("========X_ID:"+ RootContext.getXID()+","+RootContext.getBranchType());

    }

}
