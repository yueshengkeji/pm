package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ExpenseMapper;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName ExpenseServiceImpl
 * @Description
 * @Author ssk
 * @Date 2023/3/16 0016 10:18
 */
@Service("expenseService")
public class ExpenseServiceImpl implements ExpenseService, FileService,FrameStateCheckService {

    @Autowired
    private ExpenseMapper expenseMapper;
    @Autowired
    private ExpenseFileService expenseFileService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ExpenseSubjectService expenseSubjectService;

    @Override
    public int insert(Expense expense) {
        expense.setId(UUID.randomUUID().toString());
        expense.setCreateDate(DateUtil.getNowDate());
        int insert = expenseMapper.insert(expense);
        if (insert != -1){
            insertFile(expense.getExpenseFiles(),expense.getId());
            insertSubject(expense.getExpenseSubjects(),expense.getId(),expense.getStaff());
        }
        return insert;
    }

    @Override
    public int delete(String id) {
        expenseSubjectService.deleteByMark(id);
        return expenseMapper.delete(id);
    }

    @Override
    public List<Expense> list(Map<String, Object> params) {
        return expenseMapper.list(params);
    }

    @Override
    public Expense selectById(String id) {
        return expenseMapper.selectById(id);
    }

    @Override
    public int update(Expense expense) {
        int row = expenseMapper.update(expense);
        insertSubject(expense.getExpenseSubjects(),expense.getId(),expense.getStaff());
        insertFile(expense.getExpenseFiles(),expense.getId());
        return row;
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        List<ExpenseFile> expenseFiles = expenseFileService.selectByMark(moduleId);
        ArrayList<Attach> attaches = new ArrayList<>();
        expenseFiles.forEach(item -> {
            if(!Objects.isNull(item)){
                String url = item.getFileUrl();
                String name = item.getFileName();
                attaches.add(attachService.getById(attachService.findId(name, url)));
            }
        });

        return attaches;
    }

    public void insertFile(List<ExpenseFile> list,String mark){
        if(!Objects.isNull(list)){
            list.forEach(item -> {
                if(Objects.isNull(expenseFileService.selectByUrl(item.getFileUrl()))){
                    item.setMark(mark);
                    expenseFileService.insert(item);
                }
            });
        }
    }

    public void insertSubject(List<ExpenseSubject> list,String mark,Staff staff){
        list.forEach(item -> {
            if(StringUtils.isNotBlank(item.getId())){
                expenseSubjectService.update(item);
            }else{
                item.setMark(mark);
                item.setStaff(staff);
                if (item.getCourse().getId() == null){
                    item.getCourse().setId(AESEncrypt.getRandom8Id());
                    item.getCourse().setIsCompany((byte) 1);
                    item.getCourse().setIsDetail((byte) 1);
                    item.getCourse().setIsProject((byte) 1);
                    item.getCourse().setSeries("1");
                    item.getCourse().setType(-1);
                    item.getCourse().setParentId("");
                    courseService.insert(item.getCourse());
                }
                expenseSubjectService.insert(item);
            }
        });
    }

    @Override
    public void oaSuccessChange(FlowMessage message) {
        Expense expense = selectById(message.getFrameId());
        if(!Objects.isNull(expense)) {
            expense.setStatus(1);
            expenseMapper.updateState(expense);
        }
    }
}
