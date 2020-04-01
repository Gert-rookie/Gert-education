package top.fotg.eduservice.entity.vo;


import lombok.Data;
import top.fotg.eduservice.entity.EduCourse;
import top.fotg.eduservice.entity.EduCourseDescription;

/**
 * @author：Dragon Wen
 * @email：18475536452@163.com
 * @date：Created in 2020/3/3 15:55
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class CourseVo {

    private EduCourse eduCourse;

    private EduCourseDescription eduCourseDesc;
}
