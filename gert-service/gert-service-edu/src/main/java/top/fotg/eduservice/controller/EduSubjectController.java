package top.fotg.eduservice.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import top.fotg.eduservice.entity.EduSubject;
import top.fotg.eduservice.entity.subject.OneSubject;
import top.fotg.utils.R;
import top.fotg.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author gert
 * @since 2020-02-29
 */
@Api(description="课程科目(分类)管理")
@RestController
@RequestMapping("/eduservice/subject")
/*@CrossOrigin*/
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     */
    //获取上传过来文件，把文件内容读取出来
    @ApiOperation(value = "导入课程分类")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        //考虑到Excel模板中的数据不准确，所以返回多个错误信息，那么多个错误信息放在集合中
        /*List<String> mesList = subjectService.importExcel(file);

        if(mesList.size() ==0){
            return R.ok();
        } else {
            return R.ok().data("messageList", mesList);
        }*/
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    /**
     *  课程分类列表（树形）
     */
    @ApiOperation(value = "获取课程分类的Tree")
    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
    /**
     *  删除课程分类
     */
    @ApiOperation(value = "根据ID删除课程分类")
    @GetMapping("/{id}")
    public R removeById(@PathVariable String id) {
        boolean isDelete =subjectService.removeById(id);
        if(isDelete){
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 新增一级分类
     * @param subject
     * @return
     */
    @ApiOperation(value = "新增一级分类")
    @PostMapping("saveLevelOne")
    public R saveLevelOne(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody EduSubject subject){

        boolean result = subjectService.saveLevelOne(subject);
        if(result){
            return R.ok();
        }else{
            return R.error().message("新增失败");
        }
    }
    /**
     * 新增二级分类
     * @param subject
     * @return
     */
    @ApiOperation(value = "新增二级分类")
    @PostMapping("saveLevelTwo")
    public R saveLevelTwo(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody EduSubject subject){

        boolean result = subjectService.saveLevelTwo(subject);
        if(result){
            return R.ok();
        }else{
            return R.error().message("新增失败");
        }
    }





}

