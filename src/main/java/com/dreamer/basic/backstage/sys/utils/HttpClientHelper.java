package com.dreamer.basic.backstage.sys.utils;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class HttpClientHelper {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);



    private static final String APPLICATION_JSON = "application/json";

    public static String postJson(String httpUrl, StringEntity stringEntity) {
        HttpPost httpPost = new HttpPost(httpUrl);
        httpPost.setEntity(stringEntity);
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        return sendHttpPost(httpPost);
    }


//http://APPSERVERNOAUTHNEW/app/appserver/validate/checkEdAppl
    public static String get(String url, List<NameValuePair> params) {
        String body = null;
        int statusCode = 0;
        try {
            HttpClient hc = new DefaultHttpClient();
            // Get请求
            HttpGet httpget = new HttpGet(url);
            // 设置参数
            String str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(4000).build();
            httpget.setConfig(requestConfig);
            // 发送请求
            HttpResponse httpresponse = hc.execute(httpget);
            statusCode = httpresponse.getStatusLine().getStatusCode();
            logger.info("statusCode-->" + statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("HTTP" + "  " + "HttpMethod failed: " + httpresponse.getStatusLine());
            }
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity);

            if (entity != null) {
                entity.consumeContent();
            }
        } catch (Exception e) {
            logger.error("http的get方法" + e.toString());
            throw new RuntimeException(e);
        }
        return String.valueOf(body);
    }


    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(600000)
            .setConnectTimeout(600000)
            .setConnectionRequestTimeout(600000)
            .build();

    /**
     * 发送 post请求
     *
     * @param httpUrl 地址
     * @param params  参数
     */
    public static String post(String httpUrl, List<NameValuePair> params) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    private static String sendHttpPost(HttpPost httpPost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("statusCode-->" + statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("HTTP" + "  " + "HttpMethod failed: " + response.getStatusLine());
            }
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    /*//邮件发送
    public static void main(String[] argv) {
        BASE64Encoder encoder = new BASE64Encoder();
        String url = "http://localhost:/ccbsmsp/ccb/email/getEmailData";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String emaildata = encoder.encode("sunrui.qd@ccb.com|testTitle测试|testContent测试".getBytes());
        params.add(new BasicNameValuePair("orign", "BDMS"));
        params.add(new BasicNameValuePair("type", "E1"));
        params.add(new BasicNameValuePair("emailData", emaildata));
        String resultCode = get(url, params);
        if ("0000".equals(resultCode)) {
            logger.info("发送成功！");
        } else {
            logger.info("发送失败！！！！");
        }
    }*/

    //单条短信 发送
    public static void mainiiiiii(String[] argv) {
        /*BASE64Encoder encoder = new BASE64Encoder();
        String url = "http://localhost:/ccbsmsp/ccb/sms/getSmsData";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String messageData = encoder.encode("13577778888|messagedata短信。。。。。|371000000".getBytes());
        params.add(new BasicNameValuePair("orign", "BDMS"));
        params.add(new BasicNameValuePair("type", "M1"));
        params.add(new BasicNameValuePair("messageData", messageData));*/


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity("http://APPSERVERNOAUTHNEW/app/appserver/validate/checkEdAppl", Map.class);
        System.out.print(responseEntity.getBody());

/*
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userId", "18865916397"));
        params.add(new BasicNameValuePair("idNo", ""));
        params.add(new BasicNameValuePair("idTyp", ""));
        String resultCode = get("http://APPSERVERNOAUTHNEW/app/appserver/validate/checkEdAppl", params);
        System.out.print(resultCode);
        if ("0000".equals(resultCode)) {
            logger.info("发送成功！");
        } else {
            logger.info("发送失败！！！！");
        }*/
    }
    //群发短信 发送
    /*public static void main(String[] argv) {
        Gson gson = new Gson();
        BASE64Encoder encoder = new BASE64Encoder();
        *//*String url = "http://localhost:8080/haierpromtn/ccb/sms/getSmsData";*//*
        String url = "http://10.164.22.251:8080/haierpromtn/h5/getLoginData";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        List<String> smsList = new ArrayList<>();
       *//* String smsStr1 = "15192050050|截止01月05日08时您的电费[0619931290]已欠费115.40元。" +
                "如使用账户[9388]缴费，请回复\"33#48\"" +
                "|6371012012017032409000001|ZHBD0|371000000||||||";
        String smsStr2 ="13061263307|截止01月05日08时您的电费[0087992188]已欠费41.27元。" +
                "如使用账户[5658]缴费，请回复\"33#45\"" +
                "|6371012012017032409000002|ZHBD0|371000000||||||";*//*
        String smsStr1 = "15192050050|截止01月05日08时您的电费[0619931290]已欠费115.40元。" +
                "如使用账户[9388]缴费，请回复\"33#48\"|371000000";
        String smsStr2 ="13061263307|截止01月05日08时您的电费[0087992188]已欠费41.27元。" +
                "如使用账户[5658]缴费，请回复\"33#45\"|371000000";
        smsList.add(smsStr1);
        smsList.add(smsStr2);
        String smsJson = gson.toJson(smsList);
        String messageData = encoder.encode(smsJson.getBytes());
        params.add(new BasicNameValuePair("orign", "BDMS"));
        params.add(new BasicNameValuePair("type", "M2"));
        params.add(new BasicNameValuePair("messageData", messageData));
        String resultCode = get(url, params);
        if ("0000".equals(resultCode)) {
            logger.info("发送成功！");
        } else {
            logger.info("发送失败！！！！");
        }
    }*/
}
