package com.yuesheng.pm.entity;

import com.yuesheng.pm.annotation.Excel;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

import static com.yuesheng.pm.util.NumberFormat.formatDouble;

/**
 * Created by 96339 on 2017/2/10 采购明细主类 pro_detail.
 *
 * @author XiaoSong
 * @date 2017/02/10
 */
public class ProDetail extends BaseEntity {
    /**
     * 单位名称
     */
    @Excel(name = "供应商名称", width = 9000)
    private String comName;
    /**
     * 单位对象
     */
    private Company company;
    /**
     * 销售人员名称
     */
    @Excel(name = "联系人", width = 3000)
    private String saleName;
    /**
     * 销售人员联系方式
     */
    @Excel(name = "联系人手机号")
    private String tel;
    /**
     * 结账方式
     */
    @Excel(name = "结账方式", width = 3000)
    private String settleType = "";
    /**
     * 税率
     */
    @Excel(name = "税率", width = 3000)
    private Double tax;
    /**
     * 单号
     */
    @Excel(index = true, name = "序号", width = 3000)
    private String series;
    /**
     * 年初欠款
     */
    private Double yearOwe;
    /**
     * 年初欠款金额(自动计算)
     */
    private Double yearOweFinance = 0.0;
    /**
     * 年初欠票金额（自动计算）
     */
    private Double yearBillFinance = 0.0;
    /**
     * 已付款       总数查询
     */
    @Excel(name = "本年已付款", width = 3000)
    private Double yetMoney;
    /**
     * 本年购入款   总数查询
     */
    @Excel(name = "本年购入款", width = 3000)
    private Double yearPro;
    /**
     * 已入库金额   总数查询
     */
    @Excel(name = "已入库金额", width = 3000)
    private Double putMoney;

    @Excel(name = "与采购误差")
    private Double error;
    /**
     * 收票金额     总数查询
     */
    private Double billMoney;
    /**
     * 期末欠款
     */
    @Excel(name = "期末欠款", width = 3000)
    private Double endOwe;
    /**
     * 实际欠款
     */
    private Double trueMoney;
    /**
     * 欠票金额(欠票情况)
     */
    @Excel(name = "欠票情况", width = 3000)
    private Double oweBillMoney;
    /**
     * 单据所属单位
     */
    private int companyBelong;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 账面欠款,根据欠款单据累加
     */
    @Excel(name = "账面欠款", width = 3000)
    private Double paperOwe;
    /**
     * 添加时间
     */
    private String date;
    /**
     * 最后修改时间
     */
    private String lastDate;
    /**
     * 单据创建人
     */
    private Staff staff;
    /**
     * 最后修改人
     */
    private Staff lastStaff;
    /**
     * 供应商类型     0=本地，1=外地
     */
    private int type;
    /**
     * 明细年份，一家供应单位一年只能添加一张，所以单位id或者单位名称，在一年内只能出现一次
     */
    private String year;
    /**
     * 欠款
     */
    @Excel(name = "年初欠款", width = 3000)
    private ProDetailOwe moneyOwe;
    /**
     * 欠票
     */
    private ProDetailOwe billOwe;
    /**
     * 采购与入库明细集合
     */
    private List<ProPutForDetail> ppDetail;
    /**
     * 欠款欠票明细
     */
    private List<ProDetailOwe> oweDetail;
    /**
     * 付款明细集合
     */
    private List<ProDetailPay> payDetail;
    /**
     * 会计科目集合
     */
    private List<ProDetailMoney> subject;
    @Excel(name = "会计科目")
    private String subjectSeries;

    public Double getYearBillFinance() {
        return yearBillFinance;
    }

    public void setYearBillFinance(Double yearBillFinance) {
        this.yearBillFinance = yearBillFinance;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }

    public String getSubjectSeries() {
        return subjectSeries;
    }

    public void setSubjectSeries(String subjectSeries) {
        this.subjectSeries = subjectSeries;
    }

    public ProDetailOwe getMoneyOwe() {
        return moneyOwe;
    }

    public void setMoneyOwe(ProDetailOwe moneyOwe) {
        this.moneyOwe = moneyOwe;
    }

    public ProDetailOwe getBillOwe() {
        return billOwe;
    }

    public void setBillOwe(ProDetailOwe billOwe) {
        this.billOwe = billOwe;
    }

    public List<ProDetailMoney> getSubject() {
        return subject;
    }

    public void setSubject(List<ProDetailMoney> subject) {
        this.subject = subject;
    }

    public Double getYearOweFinance() {
        if (yearOweFinance != null) {
            return formatDouble(yearOweFinance);
        }
        return yearOweFinance;
    }

    public void setYearOweFinance(Double yearOweFinance) {
        this.yearOweFinance = yearOweFinance;
    }

    public int getCompanyBelong() {
        return companyBelong;
    }

    public void setCompanyBelong(int companyBelong) {
        this.companyBelong = companyBelong;
    }

    public Double getBillMoney() {
        if (billMoney != null) {
            return formatDouble(billMoney);
        }
        return billMoney;
    }

    public void setBillMoney(Double billMoney) {
        this.billMoney = billMoney;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<ProDetailPay> getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(List<ProDetailPay> payDetail) {
        this.payDetail = payDetail;
    }

    public List<ProDetailOwe> getOweDetail() {
        return oweDetail;
    }

    public void setOweDetail(List<ProDetailOwe> oweDetail) {
        this.oweDetail = oweDetail;
    }

    public List<ProPutForDetail> getPpDetail() {
        return ppDetail;
    }

    public void setPpDetail(List<ProPutForDetail> ppDetail) {
        this.ppDetail = ppDetail;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Double getYearOwe() {
        if (yearOwe != null) {
            return formatDouble(yearOwe);
        }
        return yearOwe;
    }

    public void setYearOwe(Double yearOwe) {
        this.yearOwe = yearOwe;
    }

    public Double getEndOwe() {
        if (endOwe != null) {
            return formatDouble(endOwe);
        }
        return endOwe;
    }

    public void setEndOwe(Double endOwe) {
        this.endOwe = endOwe;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getPaperOwe() {
        if (paperOwe != null) {
            return formatDouble(paperOwe);
        }
        return paperOwe;
    }

    public void setPaperOwe(Double paperOwe) {
        this.paperOwe = paperOwe;
    }

    public Double getYetMoney() {
        if (yetMoney != null) {
            return formatDouble(yetMoney);
        }
        return yetMoney;
    }

    public void setYetMoney(Double yetMoney) {
        this.yetMoney = yetMoney;
    }

    public Double getYearPro() {
        if (yearPro != null) {
            return formatDouble(yearPro);
        }
        return yearPro;
    }

    public void setYearPro(Double yearPro) {
        this.yearPro = yearPro;
    }

    public Double getPutMoney() {
        if (putMoney != null) {
            return formatDouble(putMoney);
        }
        return putMoney;
    }

    public void setPutMoney(Double putMoney) {
        this.putMoney = putMoney;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getLastStaff() {
        return lastStaff;
    }

    public void setLastStaff(Staff lastStaff) {
        this.lastStaff = lastStaff;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Double getTrueMoney() {
        if (trueMoney != null) {
            return formatDouble(trueMoney);
        }
        return trueMoney;
    }

    public void setTrueMoney(Double trueMoney) {
        this.trueMoney = trueMoney;
    }

    public Double getOweBillMoney() {
        if (oweBillMoney != null) {
            return formatDouble(oweBillMoney);
        }
        return oweBillMoney;
    }

    public void setOweBillMoney(Double oweBillMoney) {
        this.oweBillMoney = oweBillMoney;
    }


    @Override
    public ProDetail clone() {
        ProDetail pdClone = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
//            将该对象序列化成流, 因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
//            将流序列化成对象
            ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            pdClone = (ProDetail) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(oos);
        }
        return pdClone;
    }
}
