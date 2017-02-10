package client;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Janter on 2015/4/11.
 */
public class HttpUtil {
    public static final String BASE_URL = "http://10.0.2.2:8080/supplychainsimulation";

    public static String postRequest(String url, Map<String, String>params)
    {
        List<NameValuePair> requestParams = new ArrayList<NameValuePair>(params.size());
        for (String key : params.keySet())
        {
            requestParams.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            // 设置请求参数
            post.setEntity(new UrlEncodedFormEntity(
                    requestParams, HTTP.UTF_8));
            // 发送POST请求
            HttpResponse httpresponse = httpClient
                    .execute(post);
            // 如果服务器成功地返回响应
            if (httpresponse.getStatusLine()
                    .getStatusCode() == HttpStatus.SC_OK) {
                String msg = EntityUtils.toString(httpresponse.getEntity());
                return msg;
            }
            else return "Server can not response";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
