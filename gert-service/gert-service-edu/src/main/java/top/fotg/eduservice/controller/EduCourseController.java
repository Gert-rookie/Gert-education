package top.fotg.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import top.fotg.eduservice.entity.vo.CourseQuery;
import top.fotg.eduservice.entity.vo.CourseVo;
import top.fotg.utils.R;
import top.fotg.eduservice.entity.EduCourse;
import top.fotg.eduservice.entity.vo.CourseInfoVo;
import top.fotg.eduservice.entity.vo.CoursePublishVo;
import top.fotg.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/eduservice/course")
/*@CrossOrigin*/
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    /**
     * 根据条件分页查询课程列表
     * @param page
     * @param limit
     * @param courseQuery
     * @return
     */
    @ApiOperation(value = "分页课程列表")
    @PostMapping("{page}/{limit}")
    public R getPageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit" ,value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(page, limit);
        courseService.getPageList(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows",records);

    }


    /**
     * 添加课程基本信息的方法
     * @param courseVo
     * @return
     */
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseVo courseVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseVo);
        return R.ok().data("courseId",id);
    }


    /**
     * 根据课程id查询课程基本信息
     */
    @GetMapping("getCourseInfo/{id}")
    public R getCourseVoById(@PathVariable String id){
        CourseVo vo = courseService.getCourseVoById(id);
        return R.ok().data("courseInfo",vo);
    }


    /**
     * 更新课程信息
     * @param courseVo
     * @return
     */
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseVo courseVo) {
        courseService.updateCourseInfo(courseVo);
        return R.ok();
    }

    /**
     *  根据课程id查询课程确认信息
     */
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }


    /**
     * 课程最终发布,修改课程状态
     * @param id
     * @return
     */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    /**
     *  删除课程
     */

    @PostMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

