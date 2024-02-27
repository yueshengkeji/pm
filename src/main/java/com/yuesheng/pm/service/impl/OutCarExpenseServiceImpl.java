package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.FlowMessage;
import com.yuesheng.pm.entity.OutCarExpense;
import com.yuesheng.pm.mapper.OutCarExpenseMapper;
import com.yuesheng.pm.model.OutCarExpenseCount;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * (OutCarExpense)表服务实现类
 *
 * @author xiaosong
 * @since 2023-04-10 10:41:10
 */
@Service("outCarExpenseService")
public class OutCarExpenseServiceImpl implements OutCarExpenseService, FileService, FrameStateCheckService {
    @Autowired
    private OutCarExpenseMapper outCarExpenseMapper;

    @Autowired
    private OutCarExpenseDetailService detailService;

    @Autowired
    private AttachService attachService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public OutCarExpense queryById(String id) {
        return this.outCarExpenseMapper.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param outCarExpense 筛选条件
     * @return 查询结果
     */
    @Override
    public List<OutCarExpense> queryByPage(OutCarExpense outCarExpense) {
        return outCarExpenseMapper.queryAllByLimit(outCarExpense);
    }

    /**
     * 新增数据
     *
     * @param outCarExpense 实例对象
     * @return 实例对象
     */
    @Override
    public OutCarExpense insert(OutCarExpense outCarExpense) {
        if(StringUtils.isBlank(outCarExpense.getId())){
            outCarExpense.setId(UUID.randomUUID().toString());
        }
        if(StringUtils.isBlank(outCarExpense.getDatetime())){
            outCarExpense.setDatetime(DateUtil.getDatetime());
        }
        this.outCarExpenseMapper.insert(outCarExpense);
        detailService.insertBatch(outCarExpense.getDetail(),outCarExpense.getId());
        return outCarExpense;
    }

    /**
     * 修改数据
     *
     * @param outCarExpense 实例对象
     * @return 实例对象
     */
    @Override
    public OutCarExpense update(OutCarExpense outCarExpense) {
        this.outCarExpenseMapper.update(outCarExpense);
        return this.queryById(outCarExpense.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        detailService.deleteByExpense(id);
        return this.outCarExpenseMapper.deleteById(id) > 0;
    }

    @Override
    public Map<String, String> queryMoneyCount(OutCarExpense carExpense) {
        return this.outCarExpenseMapper.queryMoneyCount(carExpense);
    }

    @Override
    public Double queryInputMoneyByProject(String projectId) {
        OutCarExpenseCount result = this.detailService.queryMoneyByProject(projectId);
        Double money = 0.0;
        if(!Objects.isNull(result) && !Objects.isNull(result.getInputKm())){
            money = result.getInputKm();
        }
        return money;
    }
    @Override
    public Double querySystemMoneyByProject(String projectId) {
        OutCarExpenseCount result = this.detailService.queryMoneyByProject(projectId);
        Double money = 0.0;
        if(!Objects.isNull(result) && !Objects.isNull(result.getSystemKm())){
            money = result.getSystemKm();
        }
        return money;
    }

    @Override
    public List<Attach> getByService(String moduleId) {
        OutCarExpense expense = outCarExpenseMapper.queryById(moduleId);
        List<Attach> files = new ArrayList<>();
        if(!Objects.isNull(expense))
        {
            String fileIds = expense.getFiles();
            String[] fileIdArray = StringUtils.split(fileIds,";");
            for (int i = 0; i < fileIdArray.length; i++) {
                files.add(attachService.getById(fileIdArray[i]));
            }
        }
        return files;
    }

    @Override
    public void oaSuccessChange(FlowMessage message) {
        OutCarExpense oce = new OutCarExpense();
        oce.setId(message.getFrameId());
        oce.setState(1);
        this.outCarExpenseMapper.updateState(oce);
    }
}
