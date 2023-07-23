package com.eaxmple.chatbot.api.domain.ai.service;

import com.eaxmple.chatbot.api.domain.ai.IOpenAI;
import com.eaxmple.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.eaxmple.chatbot.api.domain.ai.model.vo.Choices;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class OpenAi implements IOpenAI {

    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;

    @Override
    public String doChatGPT(String question) throws IOException {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setProxy(new HttpHost("127.0.0.1", 10809)) // 设置代理
//                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build()) // 禁用SSL证书验证
//                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE) // 禁用主机名验证
//                .build();
        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
//        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization","Bearer " + openAiKey);

        String paramJson = "{\n" +
                "     \"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \""+question+"\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";
//        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getText());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
        }

    }
}
