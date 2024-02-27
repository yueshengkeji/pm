package com.yuesheng.pm.util;

import com.alibaba.fastjson.JSONObject;
import com.yuesheng.pm.entity.Attach;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * HttpClient工具类
 *
 * @author lujian
 */
public class NetRequestUtil {
    /**
     * socket最大延时
     */
    private final static int SOCKET_TIMEOUT = 15000;
    /**
     * 连接最大延时
     */
    private final static int CONNECT_TIMEOUT = 6000;
    /**
     * 默认字符
     */
    private final static String DEF_CHATSET = "UTF-8";


    /**
     * 发送get请求，获取响应字符串
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendGetRequest(String url, LinkedHashMap<String, String> params) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        CloseableHttpResponse response = null;
        String rs = null;
        try {
            HttpGet get = null;
            if (params != null) {
                get = new HttpGet(getUrlEncode(url, params));
            } else {
                get = new HttpGet(url);
            }
            response = client.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();
            String statusStr = response.getStatusLine().getReasonPhrase();
            if (statusCode == HttpStatus.SC_OK && statusStr.equals("OK")) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rs = EntityUtils.toString(entity, DEF_CHATSET);
                }
                // System.out.println("respStr = " + rs);
            } else {
//                entity转换
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            rs = e.getMessage();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            rs = e.getMessage();
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }

        return rs;
    }

    /**
     * 发送get请求,获取资源字节
     *
     * @param url
     * @return
     */
    public static byte[] sendGetRequest(String url) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(url);
            response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            String statusStr = response.getStatusLine().getReasonPhrase();
            if (statusCode == HttpStatus.SC_OK && statusStr.equals("OK")) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return IOUtils.toByteArray(entity.getContent());
                }
            } else {
//                entity转换
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return IOUtils.toByteArray(entity.getContent());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage().getBytes();
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
        return null;
    }

    public static String sendPostRequest(String url, LinkedHashMap params, RequestConfig requestConfig) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        CloseableHttpResponse response = null;
        String rs = null;

        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(postUrlEncode(params), DEF_CHATSET));
            response = client.execute(post);

            int statusCode = response.getStatusLine().getStatusCode();
            String statusStr = response.getStatusLine().getReasonPhrase();
            if (statusCode == HttpStatus.SC_OK && statusStr.equals("OK")) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rs = EntityUtils.toString(entity, DEF_CHATSET);
                }
                // System.out.println("respStr = " + rs); Internal Server Error
            } else {
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }

        return rs;
    }


    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String sendPostRequest(String url, LinkedHashMap<String, String> params) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        return sendPostRequest(url, params, config);
    }

    public static String sendPostForm(String url, LinkedHashMap<String, String> params,Header... header){
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build()).build();
        CloseableHttpResponse response = null;
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addPart(key,
                            new StringBody(params.get(key), ContentType.create("text/plain", Consts.UTF_8)));
                }
            }

            HttpEntity reqEntity = builder.build();
            HttpPost post = new HttpPost(url);
            if(!Objects.isNull(header)){
                for (Header header1 : header){
                    post.setHeader(header1);
                }
            }

            post.setEntity(reqEntity);
            Map<String, Object> result = new HashMap<>();
            response = client.execute(post);
            Header[] headers = response.getHeaders("Set-Cookie");
            result.put("header", headers);
            int statusCode = response.getStatusLine().getStatusCode();
            String statusStr = response.getStatusLine().getReasonPhrase();
            String rs = null;
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rs = EntityUtils.toString(entity, DEF_CHATSET);
                }
            } else {
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            return rs;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
        return null;
    }

    public static String sendPostForm(String url, LinkedHashMap<String, String> params) {
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build()).build();
        CloseableHttpResponse response = null;
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addPart(key,
                            new StringBody(params.get(key), ContentType.create("text/plain", Consts.UTF_8)));
                }
            }

            HttpEntity reqEntity = builder.build();
            HttpPost post = new HttpPost(url);
            post.setEntity(reqEntity);
            Map<String, Object> result = new HashMap<>();
            response = client.execute(post);
            Header[] headers = response.getHeaders("Set-Cookie");
            result.put("header", headers);
            int statusCode = response.getStatusLine().getStatusCode();
            String statusStr = response.getStatusLine().getReasonPhrase();
            String rs = null;
            if (statusCode == HttpStatus.SC_OK && statusStr.equals("OK")) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rs = EntityUtils.toString(entity, DEF_CHATSET);
                }
            } else {
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            return rs;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
        return null;
    }

    public static Map<String, Object> sendPostRequest(String url, LinkedHashMap<String, String> params, Map<String, String> header) {
        Map<String, Object> result = new HashMap<>();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build()).build();
        CloseableHttpResponse response = null;
        String rs = null;

        try {
            HttpPost post = new HttpPost(url);
            Iterator<String> iterator = header.keySet().iterator();
            String headName;
            while (iterator.hasNext()) {
                headName = iterator.next();
                post.addHeader(headName, header.get(headName));
            }
            post.setEntity(new UrlEncodedFormEntity(postUrlEncode(params), DEF_CHATSET));
            response = client.execute(post);
            Header[] headers = response.getHeaders("Set-Cookie");
            result.put("header", headers);
            int statusCode = response.getStatusLine().getStatusCode();
            String statusStr = response.getStatusLine().getReasonPhrase();
            if (statusCode == HttpStatus.SC_OK && statusStr.equals("OK")) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rs = EntityUtils.toString(entity, DEF_CHATSET);
                }
            } else {
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
        result.put("response", rs);
        return result;
    }

    public static Map<String, Object> sendPostRequest(String url, LinkedHashMap<String, String> params, Map<String, String> header, Attach attach) {
        Map<String, Object> result = new HashMap<>();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build()).build();
        CloseableHttpResponse response = null;
        String rs = null;
        try {
            HttpPost post = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                    .setContentType(ContentType.create("multipart/form-data", Consts.UTF_8))
                    .setBoundary("----WebKitFormBoundarygrBcuHVTeNQcBtqn")
                    .addBinaryBody(attach.getName(), attach.getFileBytes(), ContentType.create("image/jpeg"), attach.getFileName());
            builder.setCharset(Charset.forName("UTF-8"));
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addPart(key, new StringBody(params.get(key), ContentType.create("text/plain", "UTF-8")));
            }
            Iterator<String> iterator = header.keySet().iterator();
            String headName;
            while (iterator.hasNext()) {
                headName = iterator.next();
                post.addHeader(headName, header.get(headName));
            }
            post.setHeader("Content-Type", "multipart/form-data;boundary=----WebKitFormBoundarygrBcuHVTeNQcBtqn");
            HttpEntity httpEntity = builder.build();
            post.setEntity(httpEntity);
            response = client.execute(post);
            Header[] headers = response.getHeaders("Set-Cookie");
            result.put("header", headers);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rs = EntityUtils.toString(entity, DEF_CHATSET);
                }
            } else {
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
        result.put("response", rs);
        return result;
    }

    /**
     * post发送xml
     *
     * @param url
     * @param xmlData
     * @return
     */
    public static String sendXmlPost(String url, String xmlData) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        CloseableHttpResponse response = null;
        String rs = null;

        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new StringEntity(xmlData, "UTF-8"));
            response = client.execute(post);
            HttpEntity entity = response.getEntity();
            rs = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }

        System.out.println(rs);
        return rs;
    }

    /**
     * 将map型转为请求参数型-get专用
     *
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static URI getUrlEncode(String url, LinkedHashMap<String, String> data)
            throws URISyntaxException {
        List<NameValuePair> list = new LinkedList<>();
        URIBuilder uriBuilder = new URIBuilder(url);
        Set<String> keys = data.keySet();
        for (String key : keys) {
            BasicNameValuePair param = new BasicNameValuePair(key, data.get(key));
            list.add(param);
        }

        uriBuilder.setParameters(list);
        URI uri = uriBuilder.build();
        return uri;
    }

    /**
     * 将map型转为请求参数型-post专用
     *
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static List<NameValuePair> postUrlEncode(LinkedHashMap<String, String> data) {
        List<NameValuePair> list = new LinkedList<>();
        Set<String> keys = data.keySet();
        for (String key : keys) {
            Object value =data.get(key);
            BasicNameValuePair param = new BasicNameValuePair(key,String.valueOf(value));
            list.add(param);
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("parkingId", "2");
        params.put("plateNo", "11");
//        System.out.println(sendGetRequest("https://www.baidu.com", params));
        System.out.println(sendPostRequest("http://ip-api.com/json", params));
    }

    public static String sendPostJsonRequest(String url, JSONObject param,Map<String,String> header) {
        Map<String, Object> result = new HashMap<>();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build()).build();
        CloseableHttpResponse response = null;
        String rs = null;
        try {
            HttpPost post = new HttpPost(url);
            if(!Objects.isNull(header)){
                Iterator<String> iterator = header.keySet().iterator();
                String headName;
                while (iterator.hasNext()) {
                    headName = iterator.next();
                    post.addHeader(headName, header.get(headName));
                }
            }

            StringEntity stringEntity = new StringEntity(param.toString(),"UTF-8");
            post.setEntity(stringEntity);
            response = client.execute(post);
            Header[] headers = response.getHeaders("Set-Cookie");
            result.put("header", headers);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    rs = org.apache.http.util.EntityUtils.toString(entity, DEF_CHATSET);
                }
            } else {
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(client);
        }
        return rs;
    }
}