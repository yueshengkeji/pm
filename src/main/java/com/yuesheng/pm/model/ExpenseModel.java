package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Expense;

/**
 * @ClassName ExpenseModel
 * @Description
 * @Author ssk
 * @Date 2023/3/21 0021 9:58
 */
public class ExpenseModel {
    private Expense expense;
    private int index;

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
