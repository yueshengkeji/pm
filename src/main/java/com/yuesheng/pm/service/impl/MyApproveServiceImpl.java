package com.yuesheng.pm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.MyApprove;
import com.yuesheng.pm.entity.MyApproveAttached;
import com.yuesheng.pm.mapper.MyApproveAttachedMapper;
import com.yuesheng.pm.mapper.MyApproveMapper;
import com.yuesheng.pm.service.MyApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016-08-17.
 */
@Service("myApproveService")
public class MyApproveServiceImpl implements MyApproveService {
    @Autowired
    private MyApproveMapper myApproveMapper;        //【我的审批】mapepr
    @Autowired
    private MyApproveAttachedMapper myApproveAttachedMapper;

    @Override
    public void addMyApprove(MyApprove myApprove, MyApproveAttached myApproveAttached) {
        myApproveMapper.addMyApprove(myApprove);        //添加【我的审批】到数据库
        myApproveAttachedMapper.addApproveAttached(myApproveAttached);      //添加【我的审批】附表对象到数据库
    }

    /**
     * 单独添加【我的审批】主表记录
     * @param myApprove
     */
    @Override
    public void addMyApproveFirst(MyApprove myApprove) {
        myApproveMapper.addMyApprove(myApprove);
    }


    @Override
    public MyApprove getMessageById(String approveId) {
        return myApproveMapper.getMessageById(approveId);
    }

    @Override
    public int deleteById(String id) {
        return myApproveMapper.deleteById(id);
    }

    @Override
    public int deleteByDate(String startDatetime, String endDatetime){
        PageHelper.startPage(1,50,"po00602 asc");
        Page<MyApprove> mas = (Page<MyApprove>) myApproveMapper.queryByParam(startDatetime,endDatetime);
        int pages = mas.getPages();
        for (int x = 2; x <= pages; x++) {
            mas.forEach(item->{
                deleteById(item.getId());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            PageHelper.startPage(1,50,"po00602 asc");
            mas = (Page<MyApprove>) myApproveMapper.queryByParam(startDatetime,endDatetime);
        }
      return 0;
    }
}
