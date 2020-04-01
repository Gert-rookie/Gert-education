package top.fotg.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;
import top.fotg.eduservice.entity.EduSubject;
import top.fotg.eduservice.entity.excel.SubjectData;
import top.fotg.eduservice.entity.subject.OneSubject;
import top.fotg.eduservice.entity.subject.TwoSubject;
import top.fotg.eduservice.listener.SubjectExcelListener;
import top.fotg.eduservice.mapper.EduSubjectMapper;
import top.fotg.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<String> importExcel(MultipartFile file) {

        //存储错误信息集合
        List<String> meg = new ArrayList<>();
        try {
            //1、获取文件流
            InputStream inputStream = file.getInputStream();
            //2、根据流创建workBook
            Workbook workbook = new HSSFWorkbook(inputStream);
            //3、获取sheet.getSheetAt(0)
            Sheet sheet = workbook.getSheetAt(0);
            //4、根据sheet获取行数
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum <= 1){
                meg.add("请填写数据");
                return meg;
            }
            //5、遍历行
            for (int rowNum = 1; rowNum < lastRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);
                Cell cell = row.getCell(0);
                //6、获取每一行的第一列：一级分类
                if(cell == null ){
                    meg.add("第" + rowNum + "行第1列为空");
                    continue;
                }
                String cellValue = cell.getStringCellValue();
                if(StringUtils.isEmpty(cellValue)){
                    meg.add("第" + rowNum + "行第1列为数据空");
                    continue;
                }

                //7、判断列是否存在，存在获取的数据
                EduSubject subject = this.selectSubjectByName(cellValue);
                String pid = "";
                //8、把这一列中的数据（一级分类）保存到数据库中
                if(subject == null){
                    //9、在保存之前判断此一级分类是否存在，如果在就不再添加；如果不存在就保存数据
                    EduSubject su = new EduSubject();
                    su.setTitle(cellValue);
                    su.setParentId("0");
                    su.setSort(0);
                    baseMapper.insert(su);
                    pid = su.getId();
                } else {
                    pid = subject.getId();
                }

                //10、再获取每一行的第二列
                Cell cell_1 = row.getCell(1);
                //11、获取第二列中的数据（二级分类）
                if(cell_1 == null){
                    meg.add("第" + rowNum + "行第2列为空");
                    continue;
                }
                String stringCellValue = cell_1.getStringCellValue();
                if(StringUtils.isEmpty(stringCellValue)){
                    meg.add("第" + rowNum + "行第2列为数据空");
                    continue;
                }
                //12、判断此一级分类中是否存在此二级分类
                EduSubject subject_1 = this.selectSubjectByNameAndParentId(stringCellValue,pid);
                //13、如果此一级分类中有此二级分类：不保存
                if(subject_1 == null){
                    EduSubject su = new EduSubject();
                    su.setTitle(stringCellValue);
                    su.setParentId(pid);
                    su.setSort(0);
                    baseMapper.insert(su);
                }
                //14、如果没有则保存
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return meg;
    }









    //课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1 查询所有一级分类  parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2 查询所有二级分类  parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3 封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        //封装到要求的list集合里面 List<OneSubject> finalSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) { //遍历oneSubjectList集合
            //得到oneSubjectList每个eduSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);
            //把eduSubject里面值获取出来，放到OneSubject对象里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //eduSubject值复制到对应oneSubject对象里面
            BeanUtils.copyProperties(eduSubject,oneSubject);
            //多个OneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //遍历二级分类list集合
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //获取每个二级分类
                EduSubject tSubject = twoSubjectList.get(m);
                //判断二级分类parentid和一级分类id是否一样
                if(tSubject.getParentId().equals(eduSubject.getId())) {
                    //把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }

    @Override
    public boolean saveLevelOne(EduSubject subject) {

        EduSubject subjectLevelOne = this.selectSubjectByName(subject.getTitle());

        if(subjectLevelOne == null){
            return super.save(subject);
        }

        return false;
    }

    @Override
    public boolean saveLevelTwo(EduSubject subject) {
        //判断此一级分类中是否存在此二级分类的title
        EduSubject sub = this.selectSubjectByNameAndParentId(subject.getTitle(), subject.getParentId());
        if(sub != null){
            //存在
            return false;
        }
        int insert = baseMapper.insert(subject);
        return insert == 1;
    }

    /**
     * 根据课程分类的名字和父类ID查询分类是否存在
     * @param stringCellValue
     * @param pid
     * @return
     */
    private EduSubject selectSubjectByNameAndParentId(String stringCellValue, String pid) {
        QueryWrapper<EduSubject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("title", stringCellValue);
        subjectQueryWrapper.eq("parent_id", pid);
        EduSubject subject = baseMapper.selectOne(subjectQueryWrapper);
        return subject;
    }



    /**
     * 根据课程分类的名字查询分类是否存在
     * @param cellValue
     * @return
     */
    private EduSubject selectSubjectByName(String cellValue) {
        QueryWrapper<EduSubject> subjectQueryWrapper = new QueryWrapper<>();
        subjectQueryWrapper.eq("title", cellValue);
        //subjectQueryWrapper.eq("parent_id", '0');
        EduSubject subject = baseMapper.selectOne(subjectQueryWrapper);
        return subject;
    }
}
