package top.fotg.eduservice.service;

import top.fotg.eduservice.entity.EduSubject;
import top.fotg.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     */
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    /**
     * 课程分类列表（树形）
     */
    List<OneSubject> getAllOneTwoSubject();


    List<String> importExcel(MultipartFile file);
    /**
     * 保存课程一级分类
     * @param subject
     * @return
     */
    boolean saveLevelOne(EduSubject subject);

    /**
     * 保存课程二级分类
     * @param subject
     * @return
     */
    boolean saveLevelTwo(EduSubject subject);

    /**
     * 保存课程分类信息
     * @param eduSubject
     * @return
     */
    int  saveEduSubject(EduSubject eduSubject);
}
