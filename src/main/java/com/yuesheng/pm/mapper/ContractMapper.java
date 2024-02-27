package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Contract;
import com.yuesheng.pm.entity.ContractType;
import com.yuesheng.pm.entity.Project;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-08-11 合同mapper.
 * @author XiaoSong
 * @date 2016/08/11
 */
@Mapper
public interface ContractMapper {
    /**
     * 根据供应单位id获取采购合同集合
     * @param params companyId 单位id,isPay:是否付款完毕
     * @return 采购合同集合
     */
    List<Contract> getContractByCompany(Map<String, Object> params);

    /**
     * 获取采购合同集合
     * @return 合同集合
     */
    List<Contract> getContracts();
    /**
     * 检索合同
     * @param str 检索字符串
     *            @param type 合同类型
     * @return
     */
    List<Contract> seek(@Param("str") String str,@Param("type") ContractType type);

    /**
     * 通过合同id获取合同对象
     * @param id 合同id
     * @return
     */
    Contract getContractById(String id);
    /**
     * 根据时间获取合同集合
     * @param params {
     *  start 开始时间
     *  end 结束时间
     * }
     * @return 合同集合
     */
    List<Contract> getContractByParam(Map<String,Object> params);
    /**
     * 添加合同
     * @param contract 合同对象
     * @return 影响的行数
     */
    int addContract(Contract contract);

    /**
     * 获取合同中的项目集合
     * @param id 合同id
     * @return
     */
    List<Project> getProjectByContract(@Param("getProjectsByContract") String id);

    /**
     * 删除合同
     * @param id 合同主键
     * @return
     */
    int delete(String id);

    /**
     * 获取合同总数
     * @param params 参见getContractByParam()
     * @return
     */
    int getContractCount(Map<String, Object> params);

    /**
     * 获取拟定合同对象
     * @param id 拟定合同主键
     * @return
     */
    Contract queryProtocol(String id);

    /**
     * 获取项目集合
     * @param id 拟定合同id
     * @return
     */
    List<Project> queryProjectByProtocol(String id);

    /**
     * 审核|反审核合同
     *
     * @param id
     * @param state
     * @param coding
     * @param date
     */
    void approve(@Param("id") String id, @Param("state") int state, @Param("coding") String coding, @Param("date") String date);

    /**
     * 获取合同总金额
     *
     * @param param {companyId:单位id,start:开始日期,end:截止日期,isPay:支付状态筛选}
     * @return
     */
    Map<String, Double> getContractMoneyByCompany(Map<String, Object> param);

    /**
     * 获取合同已付款总金额
     *
     * @param param {companyId:单位id,start:开始日期,end:截止日期,isPay:支付状态筛选}
     * @return
     */
    Double getContranctYetMoneyByCompany(Map<String, Object> param);

    /**
     * 失效合同
     *
     * @param id 合同id
     * @return
     */
    int lose(String id);

    /**
     * 获取合同列表
     *
     * @param params {start：数据开始日期，end：数据截止日期，str:检索字符串，staffId：合同登记员工id，payStateSql：支付状态比较sql}
     * @return
     */
    List<Contract> getContractByParamV2(Map<String, Object> params);

    /**
     * 获取指定日期内合同总金额（已审核）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    Double getMoneyByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 更新合同状态
     * @param id
     * @param state
     * @param coding
     */
    void updateState(@Param("id") String id,@Param("state") int state,@Param("coding") String coding);

    /**
     * 更新合同申请中的付款金额
     * @param id
     * @param applyMoney
     * @return
     */
    int updateApplyMoney(@Param("id") String id,@Param("applyMoney") Double applyMoney);

    /**
     * 更新合同累计付款金额
     * @param id
     * @param money
     * @return
     */
    int updateYetPayMoney(@Param("id") String id,@Param("money") Double money);

    /**
     * 查询合同列表(不包含项目信息)
     * @param params {companyId:供应单位id}
     * @return
     */
    List<Contract> getContractByCompanyV2(Map<String, Object> params);

    /**
     * 添加合同与项目绑定
     * @param id 合同id
     * @param projectId 项目id
     * @return
     */
    int addProject(@Param("id") String id,@Param("projectId") String projectId);

    /**
     * 删除合同与项目绑定
     * @param id
     * @return
     */
    int deleteProject(String id);
}
