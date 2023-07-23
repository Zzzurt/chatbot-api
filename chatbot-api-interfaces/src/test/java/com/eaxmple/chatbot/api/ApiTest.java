package com.eaxmple.chatbot.api;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/*
单元测试
 */
public class ApiTest {
    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/15552115811152/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie", "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22189575c2f44123b-02e2389ba302e12-7e565470-1327104-189575c2f45e97%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg5NTc1YzJmNDQxMjNiLTAyZTIzODliYTMwMmUxMi03ZTU2NTQ3MC0xMzI3MTA0LTE4OTU3NWMyZjQ1ZTk3In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%2C%22%24device_id%22%3A%22189575c2f44123b-02e2389ba302e12-7e565470-1327104-189575c2f45e97%22%7D; zsxq_access_token=72810061-86C8-4551-F3C8-5E205C3119CF_F63569A784B4A578; zsxqsessionid=f453dba45806b38a522e60182234e968; abtest_env=product");
        get.addHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/211858121211511/answer");
        post.addHeader("cookie", "sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22189575c2f44123b-02e2389ba302e12-7e565470-1327104-189575c2f45e97%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%2C%22%24latest_referrer%22%3A%22%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg5NTc1YzJmNDQxMjNiLTAyZTIzODliYTMwMmUxMi03ZTU2NTQ3MC0xMzI3MTA0LTE4OTU3NWMyZjQ1ZTk3In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%2C%22%24device_id%22%3A%22189575c2f44123b-02e2389ba302e12-7e565470-1327104-189575c2f45e97%22%7D; zsxq_access_token=72810061-86C8-4551-F3C8-5E205C3119CF_F63569A784B4A578; zsxqsessionid=f453dba45806b38a522e60182234e968; abtest_env=product");
        post.addHeader("Content-Type", "application/json;charset=utf8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"我之前有说，自己去看！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setProxy(new HttpHost("127.0.0.1", 10809)) // 设置代理
//                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build()) // 禁用SSL证书验证
//                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE) // 禁用主机名验证
//                .build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
//        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization","Bearer asdasd0GYqgbIT3asdaszdElTgFX2SOX5nyrjCG");

        String paramJson = "{\n" +
                "     \"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"写一个java的冒泡排序!\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";
//        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}
