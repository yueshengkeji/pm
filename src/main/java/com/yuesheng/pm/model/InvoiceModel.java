package com.yuesheng.pm.model;

import com.yuesheng.pm.entity.Invoice;

public class InvoiceModel {
    private Invoice invoice;
    private int index;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
