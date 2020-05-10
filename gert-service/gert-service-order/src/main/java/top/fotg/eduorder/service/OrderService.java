package top.fotg.eduorder.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.fotg.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-13
 */
public interface OrderService extends IService<Order> {

    //1 生成订单的方法
    String createOrders(String courseId, String memberIdByJwtToken);

}
