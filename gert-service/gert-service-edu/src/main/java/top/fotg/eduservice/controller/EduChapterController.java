package top.fotg.eduservice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import top.fotg.utils.R;
import top.fotg.eduservice.entity.EduChapter;
import top.fotg.eduservice.entity.chapter.ChapterVo;
import top.fotg.eduservice.service.EduChapterService;
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
@Api("章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
/*@CrossOrigin*/
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    /**
     * 根据课程ID查询章节、小节的列表
     * 1、创建一个对象，作为章节Vo，里面包括二级Vo
     * 2、创建二级的Vo（Video）
     * 3、根据课程ID查询章节的列表，遍历这个列表，根据每一个章节的ID查询二级列表（Video集合）
     * @param courseId
     * @return
     */
    @ApiOperation(value = "根据课程ID查询章节、小节的列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    /**
     * 课程章节添加
     * @param chapter
     * @return
     */
    @ApiOperation(value = "课程章节添加")
    @PostMapping("save")
    public R save(
            @ApiParam(name = "chapter", value = "课程章节对象", required = true)
            @RequestBody EduChapter chapter){
        boolean flag = chapterService.saveChapter(chapter);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     *  根据章节id查询
     */
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }


    /**
     *  修改章节
     */
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }


    /**
     *  删除的方法
     */
    @PostMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }
}

