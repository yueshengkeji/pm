package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.ProWorkLog;

public class WorkLogExportModel {
    private ProWorkLog thanWeekLog;
    private ProWorkLog nextWeekLog;
    private int index;

    public ProWorkLog getThanWeekLog() {
        return thanWeekLog;
    }

    public void setThanWeekLog(ProWorkLog thanWeekLog) {
        this.thanWeekLog = thanWeekLog;
    }

    public ProWorkLog getNextWeekLog() {
        return nextWeekLog;
    }

    public void setNextWeekLog(ProWorkLog nextWeekLog) {
        this.nextWeekLog = nextWeekLog;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
