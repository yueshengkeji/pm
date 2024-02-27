package com.yuesheng.pm.util;

import com.yuesheng.pm.entity.Attach;

import java.io.ByteArrayInputStream;

public class FileCatchRunnable implements Runnable {

    /**
     * 要压缩的附件
     */
    private Attach attach;
    private int width;
    private int height;

    public FileCatchRunnable(Attach attach) {
        this.attach = attach;
        this.width = 200;
        this.height = 50;
    }

    public FileCatchRunnable(Attach attach, int width, int height) {
        this.attach = attach;
        this.width = width;
        this.height = height;
    }

    @Override
    public void run() {
        ImageCompress ic = ImageCompress.instanceImage();
        ic.setWidthAndHeight(this.width, this.height);
        ic.compressMaxPic(attach.getId(), new ByteArrayInputStream(attach.getFileBytes()));
    }
}
