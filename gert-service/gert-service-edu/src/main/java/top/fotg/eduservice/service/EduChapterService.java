package top.fotg.eduservice.service;

import top.fotg.eduservice.entity.EduChapter;
import top.fotg.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     *  课程大纲列表,根据课程id进行查询
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 课程章节添加
     * @param chapter
     * @return
     */
    boolean saveChapter(EduChapter chapter);


    //删除章节的方法
    boolean deleteChapter(String chapterId);

    //2 根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
