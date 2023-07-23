package com.eaxmple.chatbot.api.domain.ai;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public interface IOpenAI {
    String doChatGPT(String question) throws IOException;
}
