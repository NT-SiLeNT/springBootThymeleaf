package com.wx.springBoot.demo01.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author wangxin
 * @Title
 * @Description
 * @date 2019-10-29 10:36
 */
public class HttpSend {
    public static String sendPost(String url, File file) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String boundary = Long.toHexString(System.currentTimeMillis());
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=--------------------------"+boundary);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Access-Control-Allow-Origin","*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //Content-Type: multipart/form-data; boundary=--------------------------020702777376791490805094
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流

            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            /*Map<String,Object> map = new HashMap<>();
            map.put("FILES",file);*/
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("files",file);
            String s = jsonObject.toJSONString();
            out.write(s);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
//            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void uploadFile(String strUrl, String filePath) {
        try {

            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "========7d4a6d158c9";
            // 服务器的域名
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary---------------" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // 上传文件
            File file = new File(filePath);
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition: form-data;name=\"photo\";filename=\"" + file.getName()
                    + "\"" + newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine);
            sb.append(newLine);

            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(
                    file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            // 每次读1KB数据,并且将文件数据写入到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();

            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                    .getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    conn.getInputStream()));
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }

        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
    }

    private ResponseHandler<String> responseHandler = response -> {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            throw new ClientProtocolException("Unexpected response code : " + status);
        }
    };

    public void doMultipart(String url,String filePath) {
        CloseableHttpClient closeableHttpClient = null;
        try {
            closeableHttpClient = HttpClients.createDefault();
            File file = new File(filePath);
            String message = "文本部分";
            HttpEntity entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("file", file, ContentType.DEFAULT_BINARY, file.getName())
                    .addTextBody("message", message)
                    .build();
            HttpUriRequest request = RequestBuilder.post(url)
                    .setEntity(entity)
                    .build();
            System.out.println("Executing request " + request.getRequestLine());
            String responseBody = closeableHttpClient.execute(request, responseHandler);
            System.out.println("=================================================================");
            System.out.println(responseBody);
            System.out.println("=================================================================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (closeableHttpClient != null) {
                try {
                    closeableHttpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        String url = "http://httpbin.org/post";
        /*// 表单提交
        formExample.postForm(url);
        // 重定向
        url = "http://httpbin.org/redirect/3";
        formExample.redirectHandling(url);
        // 自定义请求头
        url = "http://httpbin.org/headers";
        formExample.setHeaders(url);
        // 分段请求上传文件*/
        url = "http://localhost:8000/api/v1/workorder/accept";

    }

    /*public static void main(String[] args) {
        String url = "http://192.168.47.1:8000/api/v1/workorder/accept";
        File file = new File("E:\\11\\111.txt");
        sendPost(url,file);
        //uploadFile("http://localhost:8000/api/v1/workorder/accept","E:\\\\11\\\\111.txt");

    }*/
}
