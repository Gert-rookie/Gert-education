package top.fotg.eduservice.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import top.fotg.servicebase.exceptionhandler.GertException;
import top.fotg.utils.R;
import top.fotg.eduservice.client.VodClient;
import top.fotg.eduservice.entity.EduVideo;
import top.fotg.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@RestController
@RequestMapping("/eduservice/video")
/*@CrossOrigin*/
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;



    @Autowired
    private VodClient vodClient;

    /**
     *  添加小节
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    /**
     *  删除小节，删除对应阿里云视频
     */
    @PostMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里面是否有视频id
        if(!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id，远程调用实现视频删除
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode() == 20001) {
                throw new GertException(20001,"删除视频失败，熔断器...");
            }
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }


    /**
     * 查询课程章节小节
     * @param id
     * @return
     */
    @ApiOperation(value = "查询课程章节小节")
    @GetMapping("getById/{id}")
    public R getVideoById(
            @ApiParam(name = "id", value = "课程章节小节", required = true)
            @PathVariable String id){
        try {
            EduVideo eduVideo = videoService.getById(id);
            return  R.ok().data("eduVideo",eduVideo);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 修改课程章节小节
     * @param video
     * @return
     */
    @ApiOperation(value = "修改课程章节小节")
    @PostMapping("update")
    public R updateVideo(
            @ApiParam(name = "video", value = "课程章节小节对象", required = true)
            @RequestBody EduVideo video){
        boolean update = videoService.updateById(video);
        if(update){
            return R.ok();
        }
        return R.error();
    }
}

