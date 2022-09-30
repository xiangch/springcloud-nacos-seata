package com.work.business.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2019-04-04
 */
@FeignClient(name = "order-service")
public interface OrderFeignClient {

    @GetMapping("order/create")
    void create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode,
                @RequestParam("count") Integer count);

}
