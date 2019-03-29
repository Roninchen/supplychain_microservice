package com.disanbo.component.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author chauncy
 * @date 2018/5/3
 */
@Slf4j
public final class HttpUtil {

    private static final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).setConnectionRequestTimeout(5000).build();

    /**
     * POST请求
     */
    public static String httpPost(String url, String param) {
        return httpPost(url, param, null);
    }

    /**
     * POST请求 + 请求头
     */
    public static String httpPost(String url, String param, Map<String, String> map) {
        HttpPost httpPost = new HttpPost(url);
        if (map != null) {
            map.forEach((k, v) -> httpPost.setHeader(k, v));
        }
        httpPost.setConfig(requestConfig);
        StringEntity se = new StringEntity(param, StandardCharsets.UTF_8);
        se.setContentType("application/json; charset=UTF-8");
        httpPost.setEntity(se);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            log.error("post error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * Get请求
     */
    public static String httpGet(String url) {
        return httpGet(url, null);
    }

    /**
     * Get请求 + 请求头
     */
    public static String httpGet(String url, Map<String, String> map) {
        HttpGet httpGet = new HttpGet(url);
        if (map != null) {
            map.forEach((k, v) -> httpGet.setHeader(k, v));
        }
        httpGet.setConfig(requestConfig);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            log.error("get error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 从网络Url中下载文件
     *
     * @param urlStr 网络url
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] downLoadByteFromUrl(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为10秒
            conn.setConnectTimeout(30 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            try (InputStream inputStream = conn.getInputStream()) {
                //获取自己数组
                return IOUtils.toByteArray(inputStream);
            }
        } catch (Exception e) {
            return null;
        }

    }
}
