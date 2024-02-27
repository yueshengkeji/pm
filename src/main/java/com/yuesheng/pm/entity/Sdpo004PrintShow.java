package com.yuesheng.pm.entity;

/**
 * Created by Administrator on 2016-08-19 004我的发起附表关系表 sdpo004_PrintShow.
 *
 * @author XiaoSong
 * @date 2016/08/09
 */
public class Sdpo004PrintShow extends BaseEntity {
    /**
     * sdpo004表主键
     */
    private String printShow02;
    private byte printShow03;

    public Sdpo004PrintShow(String s, String id, byte show3) {
        this.setId(s);
        this.printShow02 = id;
        this.printShow03 = show3;
    }

    public String getPrintShow02() {
        return printShow02;
    }

    public void setPrintShow02(String printShow02) {
        this.printShow02 = printShow02;
    }

    public byte getPrintShow03() {
        return printShow03;
    }

    public void setPrintShow03(byte printShow03) {
        this.printShow03 = printShow03;
    }
}
