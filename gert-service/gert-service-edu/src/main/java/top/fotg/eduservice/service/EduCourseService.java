package top.fotg.eduservice.service;

import top.fotg.eduservice.entity.EduCourse;
import top.fotg.eduservice.entity.frontvo.CourseFrontVo;
import top.fotg.eduservice.entity.frontvo.CourseWebVo;
import top.fotg.eduservice.entity.vo.CourseInfoVo;
import top.fotg.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.fotg.eduservice.entity.vo.CourseQuery;
import top.fotg.eduservice.entity.vo.CourseVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 根据条件分页查询课程列表
     * @param pageParam
     * @param courseQuery
     */
    void getPageList(Page<EduCourse> pageParam, CourseQuery courseQuery);


    /**
     *  添加课程基本信息的方法
     */
    String saveCourseInfo(CourseVo CourseVo);

    /**
     *根据课程ID查询课程基本信息
     * @param id
     * @return
     */
    CourseVo getCourseVoById(String id);

    //修改课程信息
    void updateCourseInfo(CourseVo courseVo);

    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);

    //删除课程
    void removeCourse(String courseId);

    //1 条件查询带分页查询课程前台
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    //根据课程id，编写sql语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
