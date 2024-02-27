package com.yuesheng.pm.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypt {
    private String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGQuyeg+AiUKioPlljH8PJXvukr90RtkgdfV0OFOcc112BsHMiACHyXLalmDMj7uYm2dxVhsO5Z1UdOo3mtp1v8bft3xaesl7ByU1cQY93InCC8/uHfE4JxFa3ssIeymVUGsTJxonqL1BMhorTZp8gdFNqIHTpJ5Yl29u7UHBynwIDAQAB";
    private String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMZC7J6D4CJQqKg+WWMfw8le+6Sv3RG2SB19XQ4U5xzXXYGwcyIAIfJctqWYMyPu5ibZ3FWGw7lnVR06jea2nW/xt+3fFp6yXsHJTVxBj3cicILz+4d8TgnEVreywh7KZVQaxMnGieovUEyGitNmnyB0U2ogdOknliXb27tQcHKfAgMBAAECgYAPsQ5PQXrE3INsxXqHqOTzFCxwjWa+1yGADAMIcUkVdEo3UILcRwxoxUPDeXdaubbHjmis8DyfePk4lgDTKNLu/GOtrh+NJEctD56uM7q2WDX6Wj1mnxwfps90KsDeFQeh/NZJljxvv7WtEN/Dl/XwnWdfL1oQser4QYN9hoGqyQJBAPpDk+xbzgW5gHkRKIp/F1DcjrmlnhKXV+Niqb0cvR1rAWeJ4zgN45a4FdedJirc4zMJSb3PBzmBXvFD3Z9hvB0CQQDKzjjKvdAKjQFo7MzKnMiAq6piIJEX2QH72J1fXGUWER+xr8AVd4kIKdHGubuoju6RSxWjyHo83e+cFVKDWJTrAkA7z9DXqW1Iwvfod2hYCV3aLoxR5DouOUuBBcCJeQfdswhH5ZWPfQpKqD5YJK/NiLMoLG0aVxNj8/5r7QtXnzUlAkAaGi6/EW0cXoem7ItzLHi17uJQHObvzdNuibO/mVG9ZFjS8s7jNG5jqZZBYJhhmnvkIWjYmq349YuoaColk5ofAkAtcqeBeo7KlHJH4mV/5zKopANdk9NMoUJyO0NCwC2Vp71go7QYhtMa2Wh63emPnGeOehBzfly6L+lRDQIcSUQP";
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
//        genKeyPair();
        //加密字符串
        String message = "zmkj1998";
        String messageEn = "IEnoC3bY8BnEPGDc9oHpz20NOvdOfFMrGtTwZJY1xof2myFsPsqu8GtWHSXGT0PPxsdTeCMmWP0R5mpWHSiabo5NqeVw3y/EdqzU7H3OpxT7eVq4S9x9/HvUX/4QX9Vdz36Ud/DWNrZ2FLZ1Ne9Cu7gnkinzMOmCELp1hy1CAM0=";
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = new RSAEncrypt().decrypt(messageEn);
        System.out.println("还原后的字符串为:" + messageDe);
    }

    /**
     * 解密字符串
     *
     * @param encryptMsg
     * @return
     * @throws Exception
     */
    public String decrypt(String encryptMsg) throws Exception {
        return this.decrypt(encryptMsg, PRIVATE_KEY);
    }

    /**
     * 加密字符串
     *
     * @param decrypt
     * @return
     * @throws Exception
     */
    public String encrypt(String decrypt) throws Exception {
        return this.encrypt(decrypt, PUBLIC_KEY);
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

}

