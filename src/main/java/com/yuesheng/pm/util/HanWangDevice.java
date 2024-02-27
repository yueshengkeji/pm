package com.yuesheng.pm.util;

import Com.FirstSolver.Security.Utils;
import Com.FirstSolver.Splash.FaceIdProtocolCodecFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

/**
 * 汉王考勤机设备通讯
 */
public class HanWangDevice extends IoHandlerAdapter {
    private final static String DEVICE_CHARSET = "GBK";
    private String ip;
    private String passwd;
    private Integer port;
    private IoConnector tcpClient;
    private int messageNumber;
    private DeviceMessageReceive deviceMessageReceive;
    private ConnectFuture connFuture;

    /**
     * 创建汉王设备通讯实例
     *
     * @param deviceMessageReceive
     */
    public HanWangDevice(DeviceMessageReceive deviceMessageReceive, String ip, String passwd, Integer port) {
        this.ip = ip;
        this.passwd = passwd;
        this.port = port;
        this.deviceMessageReceive = deviceMessageReceive;
    }

    public HanWangDevice(DeviceMessageReceive messageReceive) {
        this.deviceMessageReceive = messageReceive;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 发送指令
     *
     * @param commad
     * @throws InterruptedException
     */
    public void sendCommand(String[] commad) {
        messageNumber = 0;
        for (String cmd : commad) {
            messageNumber++;
            try {
                if (!sendCommand(cmd)) {
                    System.out.println("设备连接失败...");
                    messageNumber--;
                }
            } catch (Exception e) {
                messageNumber--;
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送指令到设备
     */
    private boolean sendCommand(String commad) throws InterruptedException {
        tcpClient = new NioSocketConnector();
        tcpClient.getFilterChain().addLast("codec", new ProtocolCodecFilter(new FaceIdProtocolCodecFactory(DEVICE_CHARSET, false, false)));
        tcpClient.setHandler(this);

        // 设置读超时等待时间为60秒
        tcpClient.getSessionConfig().setReaderIdleTime(2);

        // 建立连接
        connFuture = tcpClient.connect(new InetSocketAddress(ip, port));
        connFuture.await(500);

        // 发送查询命令
        if (connFuture.isConnected()) {   // 连接设备成功
            connFuture.getSession().setAttribute("commad", commad);
            connFuture.getSession().write(commad);
            connFuture.getSession().getCloseFuture().await(500);
            return true;
        } else {   // 连接设备失败
            return false;
        }
    }


    @Override
    public void sessionOpened(IoSession session) {
        if (!Utils.IsNullOrEmpty(this.passwd)) {   // 设置通信密钥
            FaceIdProtocolCodecFactory.setEncoderKey(session, this.passwd);
            FaceIdProtocolCodecFactory.setDecoderKey(session, this.passwd);
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        // 显示消息内容
        try {
            deviceMessageReceive.receive(message, session.getAttribute("commad").toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.closeNow();
            connFuture.cancel();
            tcpClient.dispose();
            messageNumber--;
        }
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public interface DeviceMessageReceive {
        /**
         * 接收到消息的回调函数
         *
         * @param message 消息对象
         * @param command 指令类型
         */
        void receive(Object message, String command);
    }


    public static void main(String[] args) throws InterruptedException {
//        List<Staff> staffList = HanWangDeviceUtil.getUsers("192.168.2.3","2018",9922);
//        for (Staff staff : staffList){
//            System.out.println("用户名="+staff.getName()+",id="+staff.getHwDeviceId()+",用户头像="+staff.getHead());
//        }
        HanWangDeviceUtil.getWorkCheckData("192.168.2.3", "2018", 9922, "2020-04-30 00:00:00", "2020-04-30 24:00:00");
    }
}
