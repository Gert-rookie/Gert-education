package top.fotg.eduservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.fotg.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
@Mapper
public interface EduSubjectMapper extends BaseMapper<EduSubject> {
       int  saveEduSubject(EduSubject eduSubject);
}
