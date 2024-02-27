package com.yuesheng.pm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.MyApproveMain;
import com.yuesheng.pm.mapper.MyApproveMainMapper;
import com.yuesheng.pm.service.MyApproveMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016-08-18 审批信息实现.
 */
@Service("myApproveMainService")
public class MyApproveMainServiceImpl implements MyApproveMainService {
    @Autowired
    MyApproveMainMapper myApproveMainMapper;

    @Override
    public void addApproveMain(MyApproveMain main) {
        myApproveMainMapper.addApproveMain(main);
    }


    @Override
    public MyApproveMain getMainByMsgId(String msgId) {
        return myApproveMainMapper.getMainByMsgId(msgId);
    }

    @Override
    public int deleteByDate(String startDatetime, String endDatetime) {
        PageHelper.startPage(1,50,"po10004 asc");
        Page<MyApproveMain> mms = (Page<MyApproveMain>) myApproveMainMapper.queryByParam(startDatetime,endDatetime);
        int pages = mms.getPages();
        StringBuffer sb = new StringBuffer();
        for (int x = 2; x <= pages; x++) {
            mms.forEach(item->{
                sb.append("'");
                sb.append(item.getId());
                sb.append("',");
            });
            if (sb.length() > 0) {
                myApproveMainMapper.deleteByIds(sb.substring(0,sb.length()-1));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PageHelper.startPage(1,50,"po10004 asc");
                mms = (Page<MyApproveMain>) myApproveMainMapper.queryByParam(startDatetime,endDatetime);
            }
        }
        return 0;
    }
}
