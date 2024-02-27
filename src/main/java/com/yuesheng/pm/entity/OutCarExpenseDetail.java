package com.yuesheng.pm.entity;


/**
 * (OutCarExpenseDetail)实体类
 *
 * @author xiaosong
 * @since 2023-04-10 10:41:10
 */
public class OutCarExpenseDetail extends BaseEntity {
    private static final long serialVersionUID = 478295269926770237L;
    /**
     * id
     */
    private String id;
    
    private String outHistoryId;
    
    private String outCarExpenseId;

    private OutCarHistory history;

    private OutCarExpense expense;

    public OutCarHistory getHistory() {
        return history;
    }

    public void setHistory(OutCarHistory history) {
        this.history = history;
    }

    public OutCarExpense getExpense() {
        return expense;
    }

    public void setExpense(OutCarExpense expense) {
        this.expense = expense;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutHistoryId() {
        return outHistoryId;
    }

    public void setOutHistoryId(String outHistoryId) {
        this.outHistoryId = outHistoryId;
    }

    public String getOutCarExpenseId() {
        return outCarExpenseId;
    }

    public void setOutCarExpenseId(String outCarExpenseId) {
        this.outCarExpenseId = outCarExpenseId;
    }

}

