package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.ExpenseSubModel;

/**
 * @ClassName ExpenseSubjectModel
 * @Description
 * @Author ssk
 * @Date 2023/3/21 0021 10:17
 */
public class ExpenseSubjectModel {
    private int index;
    private ExpenseSubModel expenseSubModel;

    public ExpenseSubModel getExpenseSubModel() {
        return expenseSubModel;
    }

    public void setExpenseSubModel(ExpenseSubModel expenseSubModel) {
        this.expenseSubModel = expenseSubModel;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
