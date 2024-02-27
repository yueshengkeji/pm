package com.yuesheng.pm.service.impl;

import com.yuesheng.pm.entity.Article;
import com.yuesheng.pm.entity.Company;
import com.yuesheng.pm.entity.ProBid;
import com.yuesheng.pm.entity.Staff;
import com.yuesheng.pm.mapper.ProBidMapper;
import com.yuesheng.pm.model.ProBidCount;
import com.yuesheng.pm.restapi.ProBidApi;
import com.yuesheng.pm.service.ArticleService;
import com.yuesheng.pm.service.CompanyService;
import com.yuesheng.pm.service.ProBidService;
import com.yuesheng.pm.service.StaffService;
import com.yuesheng.pm.util.Constant;
import com.yuesheng.pm.util.DateFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.poi.EncryptedDocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 投标盖章申请信息表(ProBid)表服务实现类
 *
 * @author xiaoSong
 * @since 2022-05-16 14:41:21
 */
@Service("proBidService")
public class ProBidServiceImpl implements ProBidService {
    @Autowired
    private ProBidMapper proBidMapper;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private StaffService staffService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ProBid queryById(String id) {
        return this.proBidMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ProBid> queryAllByLimit(int offset, int limit) {
        return this.proBidMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param proBid 实例对象
     * @return 实例对象
     */
    @Override
    public ProBid insert(ProBid proBid) {
        if (Objects.isNull(proBid.getDatetime())) {
            proBid.setDatetime(DateFormat.getDateTime());
        }
        if (Objects.isNull(proBid.getDate())) {
            proBid.setDate(DateFormat.getDate());
        }
        if(Objects.isNull(proBid.getId())){
            proBid.setId(UUID.randomUUID().toString());
        }
        if(StringUtils.length(proBid.getProjectSource()) > 20){
            proBid.setProjectSource(proBid.getProjectSource().substring(0,20));
        }
        setCompany(proBid);
        try{
            this.proBidMapper.insert(proBid);
        }catch(Exception e){
            e.printStackTrace();
        }
        return proBid;
    }

    private void setCompany(ProBid proBid) {
        Company company = proBid.getCompany();
        if (!Objects.isNull(company)
                && !Objects.isNull(proBid.getStaff())
                && StringUtils.isNotBlank(proBid.getStaff().getId())
                && (StringUtils.isBlank(company.getId()) || StringUtils.equals(company.getId(), "-1"))) {
            try{
                companyService.insert(company, proBid.getStaff());
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        String biddingDlCo = proBid.getBiddingDlCo();
        if(StringUtils.isNotBlank(biddingDlCo)){
            Company company1 = new Company();
            company1.setName(biddingDlCo);
            companyService.insert(company1,proBid.getStaff());
        }
    }

    /**
     * 修改数据
     *
     * @param proBid 实例对象
     * @return 实例对象
     */
    @Override
    public ProBid update(ProBid proBid) {
        setCompany(proBid);
        this.proBidMapper.update(proBid);
        return this.queryById(proBid.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.proBidMapper.deleteById(id) > 0;
    }

    @Override
    public List<ProBid> query(ProBid bid) {
        return this.proBidMapper.queryAll(bid);
    }

    @Override
    public int updateState(String id, Integer state) {
        ProBid proBid = new ProBid();
        proBid.setId(id);
        proBid.setState(state);
        update(proBid);
        return 1;
    }

    @Override
    public int updateState(String id) {
        return updateState(id,1);
    }

    @Override
    public int parse(String begin, String end) {
        List<Article> articles = articleService.getArticleByFD(Constant.BID_FOLDER, begin, end);
        ProBid bid = null;
        for (Article article : articles) {
            if (queryById(article.getId()) != null) {     //已存在，无须重新转换添加
                continue;
            }
            bid = getOvertimeByArticle(article);
            if (bid == null || bid.getStaff() == null) {
                continue;
            }
            insert(bid);
        }
        return 0;
    }

    @Override
    public ProBidCount queryCountInfo(ProBidCount count) {
        return proBidMapper.queryCountInfo(count);
    }

    private ProBid getOvertimeByArticle(Article article) {
        ProBid proBid = new ProBid();
        Element node;

        Document document;
        try {
            try {
                String html = articleService.docToHtml(article.getId(),null);
                document = Jsoup.parse(html);
            } catch (EncryptedDocumentException e) {        //转换失败

                return queryById(article.getId());
            }
            Elements ns = document.getElementsByTag("table");
            node = ns.get(0).child(0);
            proBid.setProjectName(getTableCellText(node, 1, 1));
            String companyName = getTableCellText(node, 2, 1);
            Company company = new Company();
            company.setName(companyName);
            proBid.setCompany(company);
            proBid.setBiddingDlCo(getTableCellText(node, 3, 1));
            proBid.setProjectSource(getTableCellText(node, 4, 1));
            proBid.setFeeNote(getTableCellText(node, 5, 1));
            String staffName = getTableCellText(node, 6, 1);
            Staff staff = staffService.getStaffByUserName(StringUtils.replace(staffName, " ", ""));
            if (Objects.isNull(staff)) {
                staff = new Staff();
                staff.setName(staffName);
            }
            if (StringUtils.indexOf(getTableCellText(node, 7, 1), "是") != -1) {
                proBid.setSelf((byte) 1);
            } else {
                proBid.setSelf((byte) 0);
            }
            proBid.setCoordinateName(getTableCellText(node, 7, 3));
            proBid.setCooperate(getTableCellText(node, 8, 1));
            Double bidMoney = getBidMoney(node);
            proBid.setBidMoney(bidMoney);
            proBid.setAddress(getTableCellText(node, 9, 3));
            proBid.setDate(getTableCellText(node, 10, 1));
            proBid.setType(getTableCellText(node, 10, 3));
            proBid.setRemark(getTableCellText(node, 11, 1));
            proBid.setBusPerson(staff);
            proBid.setStaff(article.getStaff());
            proBid.setDatetime(article.getDate());
            proBid.setState(1);
            if (StringUtils.indexOf(proBid.getType(), "电子") == -1) {
                proBid.setType("纸质标");
            } else {
                proBid.setType("电子标");
            }
            proBid.setId(article.getId());
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.getLogger(ProBidApi.class).error(e.getLocalizedMessage());
            return null;
        }
        return proBid;
    }

    private Double getBidMoney(Element node) {
        Double bidMoney = 0.0;
        try {
            String money = getTableCellText(node, 9, 1);
            if (StringUtils.indexOf(money, "万") != -1) {
                int f = -1;
                if ((f = StringUtils.indexOf(money, "\\.")) != -1) {
                    String temp = StringUtils.substring(money, 0, f) + "0000";
                    money = temp + StringUtils.substring(money, f);
                } else {
                    money = StringUtils.replaceChars(money, "万", "") + "0000";
                }
                bidMoney = Double.valueOf(money);
            }else{
                bidMoney = Double.valueOf(money);
            }
        } catch (NumberFormatException e) {
            return 0.0;
        }
        return bidMoney;
    }

    private String getTableCellText(Element node, int rowIndex, int cellIndex) {
        Element child = (Element) node.child(rowIndex).child(cellIndex);
        String row = StringUtils.replaceChars(child.text()," ","");
        row = StringUtils.replaceChars(row,"　","");
        return row;
    }
}