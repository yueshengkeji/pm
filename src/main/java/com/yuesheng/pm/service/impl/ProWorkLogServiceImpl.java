package com.yuesheng.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuesheng.pm.entity.Attach;
import com.yuesheng.pm.entity.ProWorkLog;
import com.yuesheng.pm.entity.ProWorkLogFile;
import com.yuesheng.pm.mapper.ProWorkLogMapper;
import com.yuesheng.pm.service.ProWorkLogFileService;
import com.yuesheng.pm.service.ProWorkLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 工作日志(ProWorkLog)表服务实现类
 *
 * @author xiaoSong
 * @since 2021-06-25 14:52:55
 */
@Service("proWorkLogService")
public class ProWorkLogServiceImpl implements ProWorkLogService {
    @Autowired
    private ProWorkLogMapper proWorkLogMapper;
    @Autowired
    private ProWorkLogFileService logFileService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProWorkLog queryById(Long id) {
        return this.proWorkLogMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProWorkLog> queryAllByLimit(int offset, int limit) {
        return this.proWorkLogMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param log 实例对象
     * @return 实例对象
     */
    @Override
    public ProWorkLog insert(ProWorkLog log) {
        log.setDatetime(new Date());
        if (StringUtils.isBlank(log.getContent())) {
            return null;
        } else if (Objects.isNull(log.getWorkDate())) {
            log.setWorkDate(log.getDatetime());
        }
        if(Objects.isNull(log.getState())){
            log.setState((byte) 0);
        }
        this.proWorkLogMapper.insert(log);
        insertFile(log);
        return log;
    }

    /**
     * 修改数据
     *
     * @param proWorkLog 实例对象
     * @return 实例对象
     */
    @Override
    public ProWorkLog update(ProWorkLog proWorkLog) {
        this.logFileService.deleteById(proWorkLog.getId());
        this.proWorkLogMapper.update(proWorkLog);
        insertFile(proWorkLog);
        return this.queryById(proWorkLog.getId());
    }

    private void insertFile(ProWorkLog proWorkLog) {
        if (!Objects.isNull(proWorkLog.getFiles())) {
            for (Attach file : proWorkLog.getFiles()) {
                ProWorkLogFile f = new ProWorkLogFile();
                f.setLogId(proWorkLog.getId());
                f.setFileId(file.getId());
                this.logFileService.insert(f);
            }
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        this.logFileService.deleteById(id);
        return this.proWorkLogMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProWorkLog> queryByParam(Map<String, Object> param,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return this.proWorkLogMapper.queryAll(param);
    }

    @Override
    public Integer queryByParamCount(Map<String, Object> param) {
        return this.proWorkLogMapper.queryAllCount(param);
    }

    @Override
    public int updateState(ProWorkLog log) {
        return proWorkLogMapper.update(log);
    }

    @Override
    public int updateRemark(ProWorkLog log) {
        return proWorkLogMapper.update(log);
    }

    @Override
    public void updatePerId(Long id, String perId) {
        proWorkLogMapper.updatePerId(id,perId);
    }

    @Override
    public void clearPerId(String perId) {
        proWorkLogMapper.clearPerId(perId);
    }

    @Override
    public void updateScoreId(Long id, String scoreId) {
        proWorkLogMapper.updateScoreId(id, scoreId);
    }

    @Override
    public int clearScore(String scoreId){
        return proWorkLogMapper.clearScore(scoreId);
    }

    @Override
    public void updateScore(ProWorkLog log) {
        proWorkLogMapper.updateScore(log);
    }

    @Override
    public List<ProWorkLog> queryByPerId(String id) {
        return proWorkLogMapper.queryByPerId(id);
    }

    @Override
    public List<ProWorkLog> queryByScoreId(String scoreId) {
        return proWorkLogMapper.queryByScoreId(scoreId);
    }

    @Override
    public void updateEditByPer(String id,Integer type) {
        proWorkLogMapper.updateEditByPer(id,type);
    }

}
