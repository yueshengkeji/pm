package com.yuesheng.pm.mapper;

import com.yuesheng.pm.entity.Count;
import com.yuesheng.pm.entity.MaterOut;
import com.yuesheng.pm.model.OutMaterData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by 宋正根 on 2016/8/30 出库单mapper.
 * @author XiaoSong
 * @date 20116/08/30
 */
@Mapper
public interface OutMaterMapper {
    /**
     * 获取最新添加的出库单
     *
     * @param staffName 职员名称
     * @param data 更新日期
     * @return 出库单对象
     */
    MaterOut getNowOutMater(@Param("name") String staffName, @Param("data") String data);

    /**
     * 添加入库单到数据库
     *
     * @param materOut 入库单对象
     * @return 影响的行数
     */
    Integer addOutMater(MaterOut materOut);

    /**
     * 获取指定时间内的出库单集合
     *
     * @param params 开始和结束时间{start,end}
     * @return 出库单集合
     */
    List<MaterOut> getOutMaterList(Map<String, Object> params);

    /**
     * 获取领料人最多的20个集合
     *
     * @return count对象集合{id,使用次数}
     */
    List<Count> getCountForStaff();

    /**
     * 更新出库单状态
     *
     * @param materOut
     */
    void updateState(MaterOut materOut);

    /**
     * 听过出库单id获取出库单对象
     *
     * @param outId 出库单id
     * @return 出库单对象
     */
    MaterOut getOutMaterById(String outId);

    /**
     * 通过项目id获取出库单集合
     *
     * @param projectId 项目id
     * @param searchStr 检索字符串
     * @return 出库单集合
     */
    List<MaterOut> getOutMaterByProjectId(@Param("projectId") String projectId, @Param("searchStr") String searchStr);

    /**
     * 获取材料出库单集合
     *
     * @param params {materId:材料id ,... :开始时间,结束时间，其他暂定}
     * @return
     */
    List<MaterOut> getOutMaterByMaterId(Map<Object, Object> params);

    /**
     * 修改出库单信息
     *
     * @param materOut 出库单对象
     * @return
     */
    int updateMaterOut(MaterOut materOut);

    /**
     * 删除出库单和材料集合
     *
     * @param outId 出库单id
     * @return
     */
    Integer deleteOut(@Param("id") String outId);

    /**
     * 获取出库单总数
     *
     * @param params {start 开始时间
     *               end 结束时间
     *               type 类型{0=未审核，1=已审核}
     *               staffCoding:审核职员编号
     *               }
     * @return
     */
    int getOutSum(Map<String, Object> params);

    /**
     * 根据参数获取数据总数
     *
     * @param params 参见getOutMaterList()
     * @return
     */
    int getOutSumByParam(Map<String, Object> params);

    /**
     * 获取领用材料最多的项目集合
     *
     * @param year       开始查询日期
     * @return
     */
    List<Map<String, Object>> getOutMaxByProject(@Param("year") String year);

    /**
     * 根据指定日期，获取领用材料集合
     *
     * @param param {start:开始日期,end:截止日期}
     * @return
     */
    List<OutMaterData> getOutMaterialMoney(Map<String, Object> param);

    /**
     * 查询出库单
     *
     * @param outNumber 出库单编号
     * @return
     */
    MaterOut getOutMaterByNumber(@Param("outNumber") String outNumber);

    /**
     * 查询最新一条出库单号
     * @param date 指定日期
     * @return
     */
    String getMaxOutNumber(@Param("date") String date);
}
