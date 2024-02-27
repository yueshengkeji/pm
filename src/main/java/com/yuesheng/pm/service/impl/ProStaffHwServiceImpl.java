package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.ProStaffHw;
import com.yuesheng.pm.entity.ProWorkCheckShow;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.ProStaffHwMapper;
import com.yuesheng.pm.service.ProStaffHwService;
import com.yuesheng.pm.service.ProWorkCheckShowService;
import com.yuesheng.pm.service.StaffService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 汉王考勤机用户信息(ProStaffHw)表服务实现类
 *
 * @author makejava
 * @since 2020-05-06 10:19:45
 */
@Service("proStaffHwService")
public class ProStaffHwServiceImpl implements ProStaffHwService {
    @Autowired
    private ProStaffHwMapper proStaffHwDao;
    @Autowired
    private ProWorkCheckShowService proWorkCheckShowService;
    @Autowired
    @Lazy
    private StaffService staffService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProStaffHw queryById(Integer id) {
        return this.proStaffHwDao.queryById(id);
    }

    @Override
    public ProStaffHw queryById(String staffId) {
        return proStaffHwDao.queryByIdV2(staffId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProStaffHw> queryAllByLimit(int offset, int limit) {
        return this.proStaffHwDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proStaffHw 实例对象
     * @return 实例对象
     */
    @Override
    public ProStaffHw insert(ProStaffHw proStaffHw) {
        this.proStaffHwDao.insert(proStaffHw);
        return proStaffHw;
    }

    /**
     * 修改数据
     *
     * @param proStaffHw 实例对象
     * @return 实例对象
     */
    @Override
    public ProStaffHw update(ProStaffHw proStaffHw) {
        this.proStaffHwDao.update(proStaffHw);
        if (proStaffHw.getId() != null) {
            return this.queryById(Integer.parseInt(proStaffHw.getId()));
        } else {
            return null;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.proStaffHwDao.deleteById(id) > 0;
    }

    @Override
    public String queryHeadByStaffId(String staffId) {
        PageHelper.startPage(1,1);
        return this.proStaffHwDao.queryHeadByStaffId(staffId);
    }

    @Override
    public void insertRelation(Staff s) {
        ProStaffHw staffHw = queryById(s.getHwDeviceId());
        if (staffHw == null) {
            ProStaffHw hw = new ProStaffHw();
            hw.setHead(s.getHead());
            hw.setId(s.getHwDeviceId());
            Staff isExists = staffService.login(s.getName());
            if (isExists != null) {
                hw.setStaffId(isExists.getId());
                try {
                    if (hw.getHead() == null) {
                        hw.setHead("");
                    }
//                    添加用户绑定
                    insert(hw);
//                    添加显示数据
                    ProWorkCheckShow show = new ProWorkCheckShow();
                    show.setStaffId(hw.getStaffId());
                    show.setIsShow(1);
                    proWorkCheckShowService.insert(show);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                LogManager.getLogger("mylog").error("用户名不存在：" + s.getName() + "," + hw.getId());
            }

        }
    }

    @Override
    public ProStaffHw queryByStaffId(String staffId) {
        PageHelper.startPage(1,1);
        return proStaffHwDao.queryByStaffId(staffId);
    }

    @Override
    public List<ProStaffHw> queryListByStaffId(String staffId) {
        return proStaffHwDao.queryListByStaffId(staffId);
    }

}