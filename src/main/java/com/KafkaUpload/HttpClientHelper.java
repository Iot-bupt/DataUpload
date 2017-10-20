package com.KafkaUpload;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by tangjialiang on 2017/10/19.
 */
public class HttpClientHelper {

    // todo 保持一个链接


    public static String sendGet(String address, Map<String, String> request_headers) {
        String result = "";
        BufferedReader in = null;
        try {

            URL realUrl = new URL(address);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();

            // 设置请求头的各种属性
            for(Map.Entry<String, String> pair : request_headers.entrySet()) {
                connection.setRequestProperty(pair.getKey(), pair.getValue());
            }

            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    /**
     *
     * @param address
     * @param request_headers
     * @param msg
     * @return
     */
    public static String sendPost(String address, Map<String, String> request_headers, JSONObject msg) {
        String response = null ;

        try {
            //创建连接
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);

            for(Map.Entry<String, String> pair : request_headers.entrySet()) {
                connection.setRequestProperty(pair.getKey(), pair.getValue());
            }

            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.writeBytes(msg.toString());
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            response = sb.toString();
            reader.close();

            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }


}
