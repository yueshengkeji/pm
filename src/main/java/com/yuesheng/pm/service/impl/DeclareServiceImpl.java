package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.sun.istack.NotNull;
import com.yuesheng.pm.entity.Course;
import com.yuesheng.pm.entity.Declare;
import com.yuesheng.pm.entity.DeclareDetail;
import com.yuesheng.pm.entity.Project;
import com.yuesheng.pm.mapper.DeclareDetailMapper;
import com.yuesheng.pm.mapper.DeclareMapper;
import com.yuesheng.pm.model.CourseModel;
import com.yuesheng.pm.service.CourseService;
import com.yuesheng.pm.service.DeclareService;
import com.yuesheng.pm.service.ProjectService;
import com.yuesheng.pm.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 96339 on 2016/12/26.
 */
@Service("declareService")
public class DeclareServiceImpl implements DeclareService {
    @Autowired
    private DeclareMapper declareMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private DeclareDetailMapper declareDetailMapper;

    @Override
    public Declare getById(String id) {
        return declareMapper.getById(id);
    }

    @Override
    public Declare getDetailById(String id) {
        return declareMapper.getDetailById(id);
    }

    @Override
    public List<Declare> getByProId(String projectId) {
        return getByProId(projectId);
    }

    @Override
    public List<Declare> getByData(String start, String end) {
        return getByData(start, end);
    }

    @Override
    public List<DeclareDetail> getByCourse(String course, String start, String end) {
        Map<String, String> params = new HashMap(16);
        ;
        params.put("courseId", course);
        params.put("begin", start);
        params.put("end", end);
        return declareDetailMapper.getByCourse(params);
    }

    @Override
    public List<Declare> queryByParams(Map<String, Object> params) {
        PageHelper.startPage(Integer.parseInt(params.get("pageNumber").toString()),Integer.parseInt(params.get("pageSize").toString()));
        return declareMapper.queryByParams(params);
    }

    @Override
    public int queryByParamsCount(Map<String, Object> params) {
        return declareMapper.queryByParamCount(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(@NotNull Declare declare1) {
        declareMapper.insert(declare1);
        List<DeclareDetail> declareDetails = declare1.getDetails();
        for (DeclareDetail dd : declareDetails) {
            dd.setId(UUID.randomUUID().toString());
            dd.setDeclareId(declare1.getId());
            dd.getStaff().setSection(declare1.getStaff().getSection());
            declareDetailMapper.insert(dd);
        }
    }

    @Override
    public void delete(String id) {
        declareMapper.delete(id);
        declareDetailMapper.delete(id);
    }

    @Override
    public Map<Project, Double> getMoneyByProject(String year,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String, Object>> result = declareMapper.getMoneyByProject(year);
        Map<Project, Double> cr = new HashMap<>(16);
        Project p = null;
        for (Map o : result) {
            Object pid = o.get("projectId");
            if (pid != null) {
                p = projectService.getProjectByid(pid.toString());
                if (p != null) {
                    try {
                        cr.put(p, ((BigDecimal) o.get("money")).doubleValue());
                    } catch (Exception e) {
                        cr.put(p, 0.0);
                    }
                }
            }
        }
        return cr;
    }

    @Override
    public Map<String, Object> getMoneyByDate(String year) {
        Map<String, Object> result = declareMapper.getMoneyByDate(year);
        result.put("year", year);
        return result;
    }

    @Override
    public List<CourseModel> getMoneyByCourse(String start, String end, Integer size, Integer number, String courseId) {

        PageHelper.startPage(number, size,false);
        List<Map<String, Object>> result = declareDetailMapper.getMoneyByCourse(start, end, courseId);
        List<CourseModel> cmLisst = new ArrayList<>();
        CourseModel cm;
        for (Map<String, Object> so : result) {
            cm = new CourseModel();
            cm.setMoney(Constant.bigToDouble(so.get("money")));
            try {
                cm.setCourse(courseService.queryById(so.get("courseId").toString()));
            } catch (Exception e) {
                Course c = new Course();
                c.setId("");
                c.setName("-");
                cm.setCourse(c);
            }
            cmLisst.add(cm);
        }

        return cmLisst;
    }
}
