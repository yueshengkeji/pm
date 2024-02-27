package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.BaseEntity;
import com.yuesheng.pm.entity.ProQuote;

import java.util.List;

/**
 * Created by Administrator on 2019-07-04.
 * @author XiaoSong
 * @date 2019/07/04
 */
public class ProQuoteModel extends BaseEntity {
    private List<ProQuote> proQuoteList;

    public List<ProQuote> getProQuoteList() {
        return proQuoteList;
    }

    public void setProQuoteList(List<ProQuote> proQuoteList) {
        this.proQuoteList = proQuoteList;
    }
}
