package com.yuesheng.pm.service.impl;

import com.sun.istack.NotNull;
import com.yuesheng.pm.entity.Flow;
import com.yuesheng.pm.entity.FlowCourse;
import com.yuesheng.pm.entity.Folder;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.FlowMapper;
import com.yuesheng.pm.service.FlowCourseService;
import com.yuesheng.pm.service.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016-08-15 流程对象服务实现.
 */
@Service("flowService")
public class FlowServiceImpl implements FlowService {
    @Autowired
    private FlowMapper flowMapper;
    @Autowired
    @Lazy
    private FlowCourseService courseService;

    @Override
    public List<Flow> getFlowByFrameCoding(String coding) {
        return flowMapper.getFlowByFrameCoding(coding);
    }

    @Override
    public Map<String, String> getFlorderARemarkAName(String flowid) {
        return flowMapper.getFlorderARemarkAName(flowid);
    }

    @Override
    public List<Flow> getFlowByAF(String folderId) {
        return flowMapper.getFlowByAF(folderId);
    }

    @Override
    public Flow getFlowById(String id) {
        return flowMapper.getFlowById(id);
    }

    @Override
    public List<Folder> getRootFolder(@NotNull String parent) {
        return flowMapper.getRootFolder(parent);
    }

    @Override
    public List<Flow> getFlowByFolder(String folderId) {

        return flowMapper.getFlowByFolder(folderId);
    }

    @Override
    public Integer insert(Flow flow, Staff staff) {
        if (flow.getName() == "" || flow.getName() == null) {
            return -1;
        }
        flow.setFrameCoding(flow.getFrameCoding() == null ? "" : flow.getFrameCoding());
        flow.setTemplement(flow.getTemplement() == null ? "" : flow.getTemplement());
        flow.setId(UUID.randomUUID().toString());
        flowMapper.insert(flow);
        Map<String, String> params = new HashMap(16);
        params.put("flowId", flow.getId());
        params.put("staffId", staff.getId());
        params.put("authType", "3");   //人员类型
        params.put("type", "1");     //修改权限
        insertAuth(params);
        return 1;
    }

    @Override
    public void update(Flow flow) {
        flowMapper.update(flow);
    }

    @Override
    public void insertAuth(Map<String, String> params) {
        flowMapper.insertAuth(params);
    }

    @Override
    public List<Flow> getFLowByName(String name) {

        return flowMapper.getFLowByName(name);
    }

    @Override
    public void delete(String id) {
        //删除过程
        List<FlowCourse> fc = courseService.getFlowCourseByFlow(id);
        fc.forEach(item -> {
            courseService.delete(item.getId());
        });
        //删除流程
        flowMapper.delete(id);
    }

    @Override
    public Folder getFolderById(String folderId) {
        return flowMapper.getFolderById(folderId);
    }

    @Override
    public int updateFolder(Folder folder) {
        return flowMapper.updateFolder(folder);
    }

    @Override
    public void insertFolder(Folder folder) {
        flowMapper.insertFolder(folder);
    }

    @Override
    public List<Folder> seekFolder(String str) {
        return flowMapper.seekFolder(str);
    }

    @Override
    public List<Flow> getAllFLow(String name) {
        return flowMapper.getAllFlow(name);
    }

    @Override
    public List<Folder> getFolderAll() {
        return flowMapper.getFolderAll();
    }

    @Override
    public int updateLastUse(String id, String datetime) {
        return flowMapper.updateLastUse(id,datetime);
    }

}
