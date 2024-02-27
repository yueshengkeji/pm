package com.yuesheng.pm.util;

import com.yuesheng.pm.entity.ProWorkCheck;
import com.yuesheng.pm.entity.Staff;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HanWangDeviceUtil {
    private static int TIMEOUT = 500;
    private static HanWangDevice device;
    private static Staff staff;

    private static HanWangDevice.DeviceMessageReceive messageReceive = new HanWangDevice.DeviceMessageReceive() {
        @Override
        public void receive(Object message, String command) {
            staff = new Staff();
            String temp = message.toString();
            staff.setHwDeviceId(getData("id", temp));
            staff.setHead(getData("photo", temp));
            staff.setName(getData("name", temp));
        }
    };

    static {
        device = new HanWangDevice(messageReceive);
    }

    public static synchronized List<ProWorkCheck> getWorkCheckData(String ip, String passwd, Integer port, String start, String end) throws InterruptedException {
        final ArrayList<ProWorkCheck> proWorkChecks = new ArrayList<>();
        HanWangDevice.DeviceMessageReceive messageReceive = new HanWangDevice.DeviceMessageReceive() {
            @Override
            public void receive(Object message, String commad) {
                BufferedReader br = new BufferedReader(new InputStreamReader(IOUtils.toInputStream(message.toString())));
                try {
                    String temp = null;
                    String datetime = null;
                    ProWorkCheck workCheck = null;
                    while ((temp = br.readLine()) != null) {
                        datetime = getData("time", temp);
                        if (datetime != null) {
                            workCheck = new ProWorkCheck();
                            workCheck.setTime(datetime.substring(11, 19));
                            workCheck.setDate(datetime.substring(0, 10));
                            workCheck.setType((byte) 5);
                            workCheck.setStaffId(getData("id", temp));
                            workCheck.setStaffName(getData("name", temp));
                            proWorkChecks.add(workCheck);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }


            }
        };
        HanWangDevice device = new HanWangDevice(messageReceive, ip, passwd, port);
        device.sendCommand(new String[]{"GetRecord(start_time=\"" + start + "\"end_time=\"" + end + "\")"});
        Thread.sleep(TIMEOUT);
        int time = 0;
        while (device.getMessageNumber() > 0) {
            Thread.sleep(TIMEOUT);
            time++;
            if (time == (TIMEOUT / 100)) {
                break;
            }
        }
        return proWorkChecks;
    }

    /**
     * 读取汉王考勤机设备用户集合
     *
     * @param ip     设备ip
     * @param passwd 设备通讯密码
     * @param port   端口号
     * @return
     * @throws InterruptedException
     */
    public static synchronized List<Staff> getUsers(String ip, String passwd, Integer port) throws InterruptedException {
        ArrayList<Staff> arrayList = new ArrayList();
//        读取所有用户id
        List<String> userIds = getUserIds(ip, passwd, port);
        //读取用户名称和头像
        for (String id : userIds) {
            arrayList.add(getUserInfo(ip, passwd, port, id));
        }
        return arrayList;
    }

    /**
     * 读取汉王考勤机设备中用户id集合
     *
     * @param ip     设备ip
     * @param passwd 设备通讯密码
     * @param port   端口号
     * @return
     */
    public static synchronized List<String> getUserIds(String ip, String passwd, Integer port) throws InterruptedException {
        final ArrayList<String> userIds = new ArrayList();
        HanWangDevice.DeviceMessageReceive messageReceive = new HanWangDevice.DeviceMessageReceive() {
            @Override
            public void receive(Object message, String commad) {
                String[] ids = message.toString().split(" ");
                if (ids != null && ids.length > 1) {
                    for (int x = 1; x < ids.length; x++) {
                        userIds.add(ids[x].substring(ids[x].indexOf("\"") + 1, ids[x].lastIndexOf("\"")));
                    }
                }
            }
        };
        HanWangDevice device = new HanWangDevice(messageReceive, ip, passwd, port);
        device.sendCommand(new String[]{"GetEmployeeID()"});
        Thread.sleep(TIMEOUT);
        int time = 0;
        while (device.getMessageNumber() > 0) {
            Thread.sleep(TIMEOUT);
            time++;
            if (time == 20) {
                break;
            }
        }

        return userIds;
    }


    /**
     * 获取用户信息
     *
     * @param ip     设备ip
     * @param passwd 设备通讯密码
     * @param port   设备端口号
     * @param id     用户id
     * @return
     * @throws InterruptedException
     */
    public static synchronized Staff getUserInfo(String ip, String passwd, Integer port, String id) throws InterruptedException {
        device.setIp(ip);
        device.setPasswd(passwd);
        device.setPort(port);
        device.sendCommand(new String[]{"GetEmployee(id=\"" + id + "\")"});
        Thread.sleep(TIMEOUT);
        int time = 0;
        while (device.getMessageNumber() > 0) {
            Thread.sleep(TIMEOUT);
            time++;
            if (time == (TIMEOUT / 100)) {
                System.out.println("数据接收超时：" + id);
                break;
            }
        }
        return staff;
    }

    private static String getData(String paramName, String message) {
        int i = message.indexOf(paramName);
        if (i != -1) {
            return message.substring(i + paramName.length() + 2, message.indexOf("\"", i + paramName.length() + 3));
        } else {
            return null;
        }
    }
}
