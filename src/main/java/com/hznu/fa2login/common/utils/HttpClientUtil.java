package com.hznu.fa2login.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 利用HttpClient进行post请求的工具类
 *
 * @since modify 2017-10-18 16:12:38 Cliven
 */
public class HttpClientUtil {


    /**
     * 发起 HTTPs POST 请求
     * <p>
     * An example usage is:
     * <code>
     * try{
     * MultipartEntityBuilder builder = MultipartEntityBuilder.create();
     * builder.addTextBody("appid", "appid", ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8));
     * builder.addTextBody("userid", "userid", ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8));
     * builder.addTextBody("password", "password", ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8));
     * builder.addBinaryBody("file", new File("test.txt"),
     * JSONObject jsonObject = HttpRequestUtils.httpsPost("localhost:8080", builder);
     * }catch(Exception e){
     * // do something...
     * }
     * </code>
     *
     * @param url     请求地址
     * @param builder 请求多类型参数构造器
     * @return 响应的JSON 字符串
     * @throws IOException 请求失败异常
     * @author Cliven
     * @since 2017-10-18 15:57:51
     */
    public static JSONObject httpsPost(String url, MultipartEntityBuilder builder) throws IOException {
        JSONObject responseBean = null;

        if (StringUtils.isEmpty(url))
            throw new IllegalArgumentException("url 为空");

        Assert.notNull(builder, "MultipartEntityBuilder 不能为空");

        // SSL START
        HttpClient httpClient = new SSLClient();
        // 设置请求地址为UTF-8 字符集
        url = URLDecoder.decode(url, "UTF-8");

        // 创建Post 对象
        HttpPost request = new HttpPost(url);


        //SSL END
        request.setEntity(builder.build());
        HttpResponse response = httpClient.execute(request);

        /**
         * 请求发送成功，并得到响应
         */
        // 响应状态码
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK)
            throw new IOException("请求失败 HTTP status Code：" + statusCode);

        // 将响应的内容转化为JSON对象
        responseBean = analysisResponseAsJSONObject(response);

        return responseBean;
    }


    /**
     * 发起Post Https请求
     *
     * @param url        请求地址
     * @param map        请求键值对
     * @param charsetArg 请求参数使用的字符集（可选，默认 UTF-8）
     * @return 请求结果字符串（一般情况下是一个JSON类型的字符串）
     * @since modify 2017-10-18 14:18:29 Cliven
     */
    public static JSONObject httpsPost(String url, Map<String, String> map, String... charsetArg) throws IOException {
        HttpClient httpClient = null;   // http Client
        HttpPost httpPost = null;       // post 方法实现
        JSONObject result = null;           // 存放响应的结果
        String charset = null;          // 使用的字符集

        // 如果没有传递参数，则直接使用默认字符集 UTF-8
        if (charsetArg.length != 0)
            charset = charsetArg[0];
        else
            charset = "UTF-8";

        httpClient = new SSLClient();
        httpPost = new HttpPost(url);

        // 设置请求参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        // map中键值对迭代器
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            // 遍历迭代器，获取每个Key-pair
            Entry<String, String> elem = (Entry<String, String>) iterator.next();
            // 取出key-pair 内容加入到请求参数中列表
            list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
        }
        if (list.size() > 0) {
            // 将请求参数列表换成 html 表单模式的编码
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
            // 设置Post 请求的实体（也就是请求参数）
            httpPost.setEntity(entity);
        }

        // 执行发起请求之后，得到的响应
        HttpResponse response = httpClient.execute(httpPost);
        Assert.notNull(response, "响应为空null");
        // 获取响应的实体内容
        HttpEntity resEntity = response.getEntity();
        Assert.notNull(resEntity, "响应实体为空....");
        // 将响应实体中的内容解析为字符串
        result = (JSONObject) JSONObject.parse(EntityUtils.toString(resEntity, charset));

        return result;
    }


    /***
     * 把HTTP响应的内容解析为JSONObject
     *
     * @param response HTTP 响应
     * @return response 中的JSONObject
     * @throws IOException 解析时异常
     * @throws IllegalArgumentException 参数为空
     * @author Cliven
     * @since 2017-7-12 18:53:38
     */
    public static JSONObject analysisResponseAsJSONObject(HttpResponse response) throws IOException {
        if (response == null)
            throw new IllegalArgumentException("HttpResponse参数 不能为空");
        JSONObject jsonResult = null;

        // 读取服务器返回过来的json字符串数据
        String str = EntityUtils.toString(response.getEntity());

        // 把json字符串转换成json对象
        jsonResult = JSON.parseObject(str);

        return jsonResult;
    }
}  