package com.yuesheng.pm.util;


import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;

/**
 * Created by Administrator on 2017-03-14.
 * @author XiaoSong
 * @date 2017/03/14
 */
public class AsyncListenerImpl implements AsyncListener {

    public void onComplete(AsyncEvent asyncEvent){
        System.out.println("async complete for song:"+asyncEvent);
    }

    public void onTimeout(AsyncEvent asyncEvent){
        System.out.println("async time out for song:"+asyncEvent);
    }

    public void onError(AsyncEvent asyncEvent){
        System.out.println("async error for song:"+asyncEvent);
    }

    public void onStartAsync(AsyncEvent asyncEvent){
        System.out.println("async start for song:"+asyncEvent);
    }
}
