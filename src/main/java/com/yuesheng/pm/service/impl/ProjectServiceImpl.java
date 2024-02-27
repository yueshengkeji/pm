package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.*;
import com.yuesheng.pm.mapper.ProjectMapper;
import com.yuesheng.pm.model.ProjectMaterial;
import com.yuesheng.pm.service.*;
import com.yuesheng.pm.util.AESEncrypt;
import com.yuesheng.pm.util.DateFormat;
import com.yuesheng.pm.util.DateUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2016-08-06.
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private AttachService attachService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private FlowMessageService flowMessageService;
    @Autowired
    private ApplyService applyService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private ProMaterialHistoryService historyService;
    @Autowired
    private ProjectTaskService taskService;
    @Autowired
    private FlowNotifyService notifyService;
    @Autowired
    private PlanMaterialService planService;
    @Autowired
    private ApplyMaterialService applyMaterialService;
    @Autowired
    private ProcurementMaterService proMaterService;
    @Autowired
    private PutStorageMaterialService putService;
    @Autowired
    private OutMaterChildService outService;

    @Override
    public Project getProjectByid(String id) {
        return projectMapper.getProjectByid(id);
    }

    @Override
    public List<Project> getProjectByLaately() {
        List<Count> temp = projectMapper.getOutPrijectMax();
        if (temp.size() <= 0) {
            Count c = new Count();
            c.setMyCount(10);
            temp.add(c);
        }
        return projectMapper.getProjectByLately(temp);
    }

    @Override
    public List<Count> getOutPrijectMax() {
        return projectMapper.getOutPrijectMax();
    }

    @Override
    public List<Project> seek(String str) {
        return projectMapper.seek(str);
    }

    @Override
    public List<Project> getProjectByCon(String contractId) {
        return projectMapper.getProjectByCon(contractId);
    }

    @Override
    public List<ProjectMaterial> getMaterUseMsg(String id, int type) {
        if (type == 1) {
            //查询计划单材料
            return planService.getProjectMaterial(id);
        } else if (type == 2) {
            //查询申请单材料
            return applyMaterialService.getProjectMaterial(id);
        } else if (type == 3) {
            //查询采购单
            return proMaterService.getProjectMaterial(id);
        } else if (type == 4) {
            //查询入库单
            return putService.getProjectMaterial(id);
        } else if (type == 5) {
            //查询出库单
            return outService.getProjectMaterial(id);
        }
        return new ArrayList<>();
    }

    @Override
    public void insert(Project p) throws Exception {
        if (!Objects.isNull(p)) {
            if (Objects.isNull(getProjectByName(p.getName()))) {
                projectMapper.insert(p);
            } else {
                throw new Exception("项目名称已经存在，添加失败");
            }
        }
    }


    @Override
    public int update(Project project) throws Exception {
        Project p = getProjectByName(project.getName());
        if (Objects.isNull(p) || p.getId().equals(project.getId())) {
            if (StringUtils.isBlank(project.getAdd())) {
                project.setAdd("");
            }
            return projectMapper.update(project);
        } else {
            throw new Exception("项目名称已经存在，修改失败");
        }
    }

    @Override
    public Project getProjectByName(String name) {
        return projectMapper.getProjectByName(name);
    }

    @Override
    public int updateApprove(String id) {
        return updateApprove(id, "1");
    }

    @Override
    public int updateApprove(String frameId, String state) {
        return projectMapper.updateApprove(frameId, state, DateUtil.getDate());
    }

    @Override
    public List<ProjectMaterial> syncData(String id) {

        Integer[] loadTypes = new Integer[]{1, 2, 3, 4, 5, 6};
        int type = 6;
        List<ProjectMaterial> result = new ArrayList<>();
        HashMap<String, ProjectMaterial> pmMap = new HashMap<>(16);

        if (loadTypes.length > 0) {
            int startIndex = loadTypes[0];
            for (int i = startIndex; i <= type; i++) {
                if (ArrayUtils.indexOf(loadTypes, i) == -1) {
                    continue;
                }
                StopWatch watch = new StopWatch();
                watch.start("first");
                List<ProjectMaterial> materialList = getMaterUseMsg(id, i);
                watch.stop();
                if (i == startIndex) {
                    watch.start("type=1");
                    for (int j = 0; j < materialList.size(); j++) {
                        ProjectMaterial m1 = materialList.get(j);
                        pmMap.put(m1.getId(), m1);
                        result.add(m1);
                    }
                    watch.stop();
                } else if (i == 3) {
                    watch.start("type=" + i);
                    for (int j = 0; j < materialList.size(); j++) {
                        ProjectMaterial m2 = materialList.get(j);

                        ProjectMaterial pm2 = pmMap.get(m2.getId());
                        if (pm2 == null) {
                            //不存在，添加新的
                            // m2.setProcurementList(procurements);
                            result.add(m2);
                            pmMap.put(m2.getId(), m2);
                            // setMaterial(m2, materialService.getMaterialByid(m2.getId()));
                        } else {
                            pm2.setProSum(m2.getProSum());
                            pm2.setProMoney(m2.getProMoney());
                            // pm2.setCompanyNames(m2.getCompanyNames());
                            // pm2.setProcurementList(procurements);
                        }
                        // proMoneys = proMoneys.add(new BigDecimal(m2.getProMoney()));
                    }
                    watch.stop();
                } else {
                    watch.start("type=" + i);
                    for (int j = 0; j < materialList.size(); j++) {
                        ProjectMaterial m3 = materialList.get(j);
                        ProjectMaterial pm2 = pmMap.get(m3.getId());
                        if (pm2 == null) {
                            //不存在，添加新的
                            result.add(m3);
                            pmMap.put(m3.getId(), m3);
                            // setMaterial(m3, materialService.getMaterialByid(m3.getId()));
                        } else {
                            if (m3.getType() == 2) {
                                //申请数量
                                pm2.setApplySum(m3.getApplySum());
                            } else if (m3.getType() == 4) {
                                //入库数量
                                pm2.setPutSum(m3.getPutSum());
                            } else if (m3.getType() == 6) {
                                //退料数量
                                pm2.setBackSum(m3.getBackSum());
                            } else {
                                //出库数量 、 出库价格
                                pm2.setOutSum(m3.getOutSum());
                                pm2.setMoney(m3.getMoney());
                            }
                        }
                        // outMoneys = outMoneys.add(BigDecimal.valueOf(m3.getMoney()));
                        // putMoneys = putMoneys.add(BigDecimal.valueOf(m3.getPutMoney()));
                        // backMoneys = backMoneys.add(BigDecimal.valueOf(m3.getBackMoney()));
                    }
                    watch.stop();
                }
                System.out.println(watch.prettyPrint());
            }
        }

        /**
         * 将数据添加到记录表中
         */
        result.forEach(item -> {
            ProMaterialHistory history = new ProMaterialHistory();
            BeanUtils.copyProperties(item, history);
            if (!Objects.isNull(item.getProMoney()) && !Objects.isNull(item.getProSum()) && item.getProMoney() != 0) {
                try {
                    BigDecimal bd = new BigDecimal(item.getProMoney() / item.getProSum()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    history.setProPrice(bd.doubleValue());
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            }
            history.setMaterialId(item.getId());
            history.setProjectId(id);
            historyService.insert(history);
        });
        return result;
    }

    @Override
    public void notifyUser(String projectId, String name, String taskId) {
        Map<String, Object> param = new HashMap<>();
        ProjectTask task = taskService.queryById(taskId);
        Project p = getProjectByid(projectId);
        if (!Objects.isNull(task) && !Objects.isNull(p)) {
            ArrayList<Staff> ns = new ArrayList<>();
            ns.add(task.getStaff());
            param.put("title", "任务提醒");
            param.put("mTitle", p.getName());
            param.put("content", task.getName());
            param.put("url", "");
            notifyService.msgNotify(ns, param);
            task.setState(1);
            taskService.update(task);
        }
    }

    @Override
    public Folder getFolderById(String folderId) {
        return projectMapper.getFolderById(folderId);
    }

    @Override
    public List<Folder> queryFolders(String str) {
        return projectMapper.queryFolder(str);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectMapper.getAllProjects();
    }

    @Override
    public int getProjectsCount(Map<String, Object> params) {
        return projectMapper.getProjectsCount(params);
    }

    @Override
    public List<Project> getProjects(Map<String, Object> params, Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,false);
        return projectMapper.getProjects(params);
    }

    @Override
    public List<Map<String, Object>> getMaterialTopTen(String beginDate, String endDate,Integer pageNum,Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("beginDate", beginDate);
        map.put("endDate", endDate);
        PageHelper.startPage(pageNum,pageSize,false);
        return projectMapper.getMaterialTopTen(map);
    }

    @Override
    public void insert(Project p, String[] aArray, Staff staff) throws Exception {
        if (verify(p, staff)) {
            if (p.getId() == null || "".equals(p.getId())) {
                p.setId(UUID.randomUUID().toString());
            }
            //项目审批时间
            p.setApproveDate("");
            p.setStaff(staff);
            insert(p);
            if (aArray != null && aArray.length > 0) {
                //添加附件
                Map<String, String> ap = new HashMap<>(16);
                ap.put("table", "sdpa001FJ");
                //主键列
                ap.put("field", "pa001FJ01");
                //附件主表列
                ap.put("field2", "pa001FJ02");
                ap.put("moduleId", p.getId());
                for (String id : aArray) {
                    if (id != null && !"".equals(id)) {
                        ap.put("id", id);
                        attachService.addAttachRelation(ap);
                    }
                }
            }
        }
    }

    @Override
    public int delete(String id) {
        Project p = getProjectByid(id);
        if (!Objects.isNull(p) && p.getState() == 0) {
            flowMessageService.deleteMessage(id);
            return projectMapper.delete(id);
        }
        return 0;
    }

    @Override
    public List<Staff> getProjectManager(String id) {
        List<Apply> applies = applyService.getApplyGroupByProject(id);
        ArrayList<Staff> staffList = new ArrayList<>();
        HashMap<String, Staff> temp = new HashMap<>();
        applies.forEach(item -> {
            Staff s = item.getStaff();
            if (!temp.containsKey(s.getId())) {
                temp.put(s.getId(), item.getStaff());
                staffList.add(s);
            }
        });
        return staffList;
    }

    @Override
    public List<Project> seekByApprove(String str) {
        return projectMapper.seekByApprove(str);
    }

    /**
     * 验证项目是否合法
     *
     * @param p 项目对象
     * @return
     */
    private boolean verify(Project p, Staff staff) {
        if (p == null) {
            return false;
        }
        if (p.getFolder() == null) {
            Folder f = getFolderById(p.getFolderId());
            if (Objects.isNull(f)) {
                return false;
            } else {
                p.setFolder(f);
            }
        } else if (StringUtils.isBlank(p.getFolder().getId())) {
            Folder folder = insertFolder(p.getFolder().getName());
            p.setFolder(folder);
        }
        if (p.getName() == null) {
            return false;
        }
        if (p.getoOwner() == null) {
            return false;
        } else if (StringUtils.isBlank(p.getoOwner().getId()) || StringUtils.equals(p.getoOwner().getId(), "-1")) {
            //根据单位名称获取单位对象
            List<Company> companies = companyService.seekAll(p.getoOwner().getName());
            if (companies.size() > 0) {
                p.setoOwner(companies.get(0));
                p.setOwner(companies.get(0).getId());
            } else {
                //添加单位对象
                p.getoOwner().setIsClient((byte) 1);
                p.getoOwner().setIsSupply((byte) 0);
//                业主单位
                Folder f = new Folder();
                f.setId("D0&0P&ST");
                p.getoOwner().setoFolder(f);
                Company company = companyService.insert(p.getoOwner(), staff);
                if (company.getId() != null) {
                    p.setoOwner(company);
                } else {
                    return false;
                }
            }
        }
        if (p.getoConstruction() == null) {
            return false;
        } else if (StringUtils.isBlank(p.getoConstruction().getId()) || StringUtils.equals(p.getoConstruction().getId(), "-1")) {
            List<Company> companies = companyService.seekAll(p.getoConstruction().getName());
            if (companies.size() > 0) {
                p.setoConstruction(companies.get(0));
                p.setConstruction(companies.get(0).getId());
            } else {
                //添加单位对象
                p.getoConstruction().setIsClient((byte) 1);
                p.getoConstruction().setIsSupply((byte) 0);
                Folder f = new Folder();
                f.setId("D0&0P&ST");
                p.getoConstruction().setoFolder(f);
                Company company = companyService.insert(p.getoConstruction(), staff);
                if (company.getId() != null) {
                    p.setoConstruction(company);
                } else {
                    return false;
                }
            }
        }
        if (p.getDate() == null) {
            p.setDate(DateFormat.getDate());
        }
        if (p.getPaDate() == null) {
            p.setDate(DateFormat.getDate());
        }
        if (p.getSeries() == null) {
//            生成项目编号
            p.setSeries("");
        }
        if (p.getManagerName() == null) {
            p.setManagerName("");
        }
        if (p.getRemark() == null) {
            p.setRemark("");
        }
        if (p.getExplain() == null) {
            p.setExplain("");
        }
        Region region = p.getCity();
        if (region == null || region.getId() == null) {
            Region c = new Region();
            c.setId("");
            p.setCity(c);
        } else if (StringUtils.equals(region.getId(), "-1")) {
            regionService.insert(region);
            p.setCity(region);
        }
        if (p.getAdd() == null) {
            p.setAdd("");
        }

        return true;
    }

    private Folder insertFolder(String name) {
        Folder folder = getFolderByName(name);
        if (Objects.isNull(folder) && StringUtils.isNotBlank(name)) {
            if (StringUtils.length(name) > 30) {
                name = name.substring(0, 29);
            }
            folder = new Folder();
            folder.setName(name);
            folder.setId(AESEncrypt.getRandom8Id());
            folder.setRootId(folder.getId());
            folder.setCode("");
            folder.setParent("");
            projectMapper.insertFolder(folder);
        } else {
            folder = new Folder();
            folder.setId("");
        }
        return folder;
    }

    private Folder getFolderByName(String name) {
        return projectMapper.getFolderByName(name);
    }

    @Override
    public int insertProjectAuth(ProjectAuth auth) {
        ProjectAuth pa = projectMapper.getProjectAuthByPs(auth.getProject().getId(),auth.getStaff().getId());
        if(Objects.isNull(pa)){
            return projectMapper.insertProjectAuth(auth);
        }else {
            return -1;
        }
    }

    @Override
    public int deleteProjectAuth(String id) {
        return projectMapper.deleteProjectAuth(id);
    }

    @Override
    public List<ProjectAuth> getProjectAuth(String projectId) {
        return projectMapper.getProjectAuth(projectId);
    }

    @Override
    public List<ProjectAuth> getProjectAuthByStaff(String staffId) {
        return projectMapper.getProjectAuthByStaff(staffId);
    }

    @Override
    public List<Project> seek(String str, String staffId) {
        return projectMapper.seekByAuth(str,staffId);
    }
}
