package com.yuesheng.pm.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

/**
 * Created by Administrator on 2016-08-20.
 *
 * @author XiaoSong
 * @date 2016/08/20
 * 密码解密&编码工具
 */
public class AESEncrypt {


    /**
     * BASE64解密
     *
     * @param key = 需要解密的密码字符串
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (Base64.getDecoder()).decode(key);
    }

    /**
     * BASE64加密
     *
     * @param key = 需要加密的字符数组
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        String ep = Base64.getEncoder().encodeToString(key);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(ep)) {
            ep = org.apache.commons.lang3.StringUtils.replaceChars(ep, "\n", "");
            ep = org.apache.commons.lang3.StringUtils.replaceChars(ep, "\t", "");
            ep = org.apache.commons.lang3.StringUtils.replaceChars(ep, "\r", "");
        }
        return ep/*.replaceAll("[\\s*\t\n\r]","")*/;
    }


    public synchronized static String loginPost(String urlPath, String param) throws IOException {
        URL url = new URL(urlPath);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
//        向页面传递数据。post的关键所在！
        out.write(param);
        out.flush();
        out.close();
//        一旦发送成功，用以下方法就可以得到服务器的回应：
        String sCurrentLine;
        String sTotalString;
        sCurrentLine = "";
        sTotalString = "";
        InputStream l_urlStream;
        l_urlStream = connection.getInputStream();
//        传说中的三层包装阿！
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(
                l_urlStream));
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString += sCurrentLine + "\r\n";
        }
        return sTotalString;
    }

    /**
     * 生成角色编码
     *
     * @return
     */
    public static String getRandom8Id() {
        int maxNum = 40;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '#', '&', '@', '$'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < 8) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }

    private static char[] base64EncodeChars = new char[]{'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/',};

    private static byte[] base64DecodeChars = new byte[]{-1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,
            60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,
            -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
            38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
            -1, -1};

    public static byte[] decode(String str) {
        return decode(str, base64DecodeChars);
    }

    public static byte[] decode(String aesStr, String aesKey) {
        return decode(aesStr, aesKey.getBytes());
    }

    /**
     * 解密key
     *
     * @param str 待解密的串
     * @return
     */
    public static byte[] decode(String str, byte[] keyArray) {
        byte[] data = str.getBytes();
        int len = data.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        int b1, b2, b3, b4;

        while (i < len) {
            do {
                b1 = keyArray[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) {
                break;
            }

            do {
                b2 = keyArray[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) {
                break;
            }
            buf.write((b1 << 2) | ((b2 & 0x30) >>> 4));

            do {
                b3 = data[i++];
                if (b3 == 61) {
                    return buf.toByteArray();
                }
                b3 = keyArray[b3];
            } while (i < len && b3 == -1);

            if (b3 == -1) {
                break;
            }
            buf.write(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2));

            do {
                b4 = data[i++];
                if (b4 == 61) {
                    return buf.toByteArray();
                }
                b4 = keyArray[b4];
            } while (i < len && b4 == -1);

            if (b4 == -1) {
                break;
            }
            buf.write(((b3 & 0x03) << 6) | b4);
        }
        return buf.toByteArray();
    }


    /**
     * 返回长度为【strLength】的随机数，在前面补0
     */
    public static String getFixLenthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(2, strLength + 1);
    }

    /**
     * 返回指定长度的数字字符串，number不足指定长度时补零
     *
     * @param number 字符串
     * @param len    字符串长度
     * @return
     */
    public static String getFixLengthString(int number, int len) {
        StringBuffer temp = new StringBuffer();
        temp.append(number);
        if (temp.toString().length() >= len) {
            return temp.toString();
        }
        for (int x = 0; x < len; x++) {
            temp.insert(0, "0");
            if (temp.toString().length() >= len) {
                break;
            }
        }
        return temp.toString();

    }

    public static void main(String[] args) throws Exception {
//        String latitude = "31.95301514882906";
//        String longitude = "120.9289273069784";
//        String url = "https://apis.map.qq.com/ws/geocoder/v1/?location="+latitude+","+longitude+"&key=KWKBZ-I2TRO-XWBWI-SAF4L-SNQYO-JNFUW&get_poi=1";
//        String res = NetRequestUtil.sendGetRequest(url,null);
//        JSONObject jo = ((JSONObject) JSON.parse(res)).getJSONObject("result").getJSONObject("address_component");
//        System.out.println(jo);
//        INSERT INTO target (ey00305) VALUES ('MTU5MzU3
//');INSERT INTO target (ey00305) VALUES ('MTM1  691001
//');
//        String ep = encryptBASE64("zm123".getBytes());
//        if(StringUtils.isNotBlank(ep)){QDEyM0AxMjM=
//            ep = StringUtils.replaceChars(ep,"\n","");
//        }
//        System.out.println(ep);
        String test = "123";
        test = org.apache.commons.lang3.StringUtils.substring(test,test.length(),test.length());
        System.out.println();
//        System.out.println(new String(decode("QDEyM0AxMjM=")));
//        Double s = 45271.52957175926;
//        Date t = convertNumericToTimestamp(s);
//        System.out.println(t);
//        String cid = "4400";
//        System.out.println(new String(cid.getBytes("UTF-8"),"GBK"));
//        Date testSd = DateUtil.getMonthStartTime(DateUtil.parse("2023-02-11 00:00:00"));
//        Date testEd = DateUtil.getMonthEndTime(DateUtil.parse("2023-02-11 00:00:00"));
//        System.out.println(DateUtil.format(testSd));
//        System.out.println(DateUtil.format(testEd));
//        System.out.println(new RSAEncrypt().decrypt("MzAzMzAwMzAz"));
//        System.out.println("123abc啊".getBytes().length);length
//        System.out.println(getSha1("13726231491409659813QDG6eKRypEvHKD8QQKFhvQ6QleEB4J58tiPdvo+rtK1I9qca6aM/wvqnLSV5zEPeusUiX5L5X/0lWfrf0QADHHhGd3QczcdCUpj911L3vg3W/sYYvuJTs3TUUkSUXxaccAS0qhxchrRYt66wiSpGLYL42aM6A8dTT+6k4aSknmPj48kzJs8qLjvd4Xgpue06DOdnLxAUHzM6+kDZ+HMZfJYuR+LtwGc2hgf5gsijff0ekUNXZiqATP7PF5mZxZ3Izoun1s4zG4LUMnvw2r+KqCKIw+3IQH03v+BCA9nMELNqbSf6tiWSrXJB3LAVGUcallcrw8V2t9EL4EhzJWrQUax5wLVMNS0+rUPA3k22Ncx4XXZS9o0MBH27Bo6BpNelZpS+/uh9KsNlY6bHCmJU9p8g7m3fVKn28H3KDYA5Pl/T8Z1ptDAVe0lXdQ2YoyyH2uyPIGHBZZIs2pDBS8R07+qN+E7Q=="));

//        StringBuffer sb = new StringBuffer("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAAGQKADAAQAAAABAAADhAAAAAD/7QA4UGhvdG9zaG9wIDMuMAA4QklNBAQAAAAAAAA4QklNBCUAAAAAABDUHYzZjwCyBOmACZjs+EJ+/8AAEQgDhAZAAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVV");
//        String test = sb.substring(0,50);
//        int idx = test.indexOf("base64");
//        if (idx != -1) {
//            sb = sb.delete(0, idx + 7);
//        }
//        System.out.println(sb.toString());
//                         32060256521218001102
//        String deviceId = "32060256521328001102";
//        System.out.println(deviceId.substring(0,10)+"121"+deviceId.substring(13));
    }

    private static Date convertNumericToTimestamp(double numericDate) {
        long timeInMillis = (long) ((numericDate - 25569) * 24 * 60 * 60 * 1000);
        // 应用时区调整
        return new Date(timeInMillis - TimeZone.getTimeZone("Asia/Shanghai").getRawOffset());
    }

    public static String getSha1(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
        byte[] digest = md.digest();

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
    }

    public static String AESDecode(String encryptedData, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {

        String algorithm = "AES/CBC/PKCS7Padding";

        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        IvParameterSpec ivSpec = new IvParameterSpec(Arrays.copyOfRange(key.getBytes(), 0, 16));

        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

        return decryptedText;
    }
}
