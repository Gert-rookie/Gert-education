package top.fotg.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.fotg.eduorder.entity.Order;
import top.fotg.eduorder.entity.OrderQuery;
import top.fotg.eduorder.service.OrderService;
import top.fotg.servicebase.exceptionhandler.GertException;
import top.fotg.utils.R;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author gert
 * @since 2020-02-24
 */
@Api(description="订单管理")
@RestController
@RequestMapping("/eduorder/orderEnd")
/*@CrossOrigin *///解决跨域
//@CrossOrigin
public class OrderEndTeacherController {


    //把service注入
    @Autowired
    private OrderService orderService;


    /**
     * 删除订单的方法（后台）
     */
    @ApiOperation(value = "删除订单")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "订单ID", required = true)
                                     @PathVariable String id) {
        boolean flag = orderService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3 分页查询订单的方法
    //current 当前页
    //limit 每页记录数
    @ApiOperation(value = "分页查询订单")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        //创建page对象
        Page<Order> pageTeacher = new Page<>(current,limit);

        try {
            int i = 10/0;
        }catch(Exception e) {
            //执行自定义异常
            throw new GertException(20001,"执行了自定义异常处理....");
        }


        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        orderService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<Order> records = pageTeacher.getRecords(); //数据list集合

        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 条件查询带分页的方法（后台）
     */
    @ApiOperation(value = "条件查询带分页的方法（后台）")
    @PostMapping("pageOrderCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) OrderQuery orderQuery) {
        //创建page对象
        Page<Order> pageOrder = new Page<>(current,limit);

        //构建条件
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
       // 多条件组合查询
        // mybatis学过 动态sql
        String courseTitle = orderQuery.getCourseTitle();
        String  orderNo = orderQuery.getOrderNo();
        String begin = orderQuery.getBegin();
        String end = orderQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(courseTitle)) {
            //构建条件
            wrapper.like("course_title",courseTitle);
        }
        if(!StringUtils.isEmpty(orderNo)) {
            wrapper.eq("order_no",orderNo);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        orderService.page(pageOrder,wrapper);

        long total = pageOrder.getTotal();//总记录数
        List<Order> records = pageOrder.getRecords(); //数据list集合
        return R.ok().data("total",records.size()).data("rows",records);
    }



    /**
     * 根据订单id进行查询（后台）
     * @param id
     * @return
     */
    @ApiOperation(value = "根据订单id进行查询（后台）")
    @GetMapping("getOrder/{id}")
    public R getTeacher(@PathVariable String id) {
        Order order = orderService.getById(id);
        return R.ok().data("order",order);
    }

    /**
     * 订单修改功能（后台）
     */
    @ApiOperation(value = "订单修改功能（后台）")
    @PostMapping("updateOrder")
    public R updateTeacher(@RequestBody Order order) {
        boolean flag = orderService.updateById(order);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

