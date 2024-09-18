package com.agentsflex.llm.ollama;

import com.agentsflex.core.document.Document;
import com.agentsflex.core.llm.Llm;
import com.agentsflex.core.llm.response.AiMessageResponse;
import com.agentsflex.core.llm.response.FunctionMessageResponse;
import com.agentsflex.core.message.AiMessage;
import com.agentsflex.core.prompt.FunctionPrompt;
import com.agentsflex.core.prompt.ImagePrompt;
import com.agentsflex.core.store.VectorData;
import org.junit.Test;

public class OllamaLlmTest {

    @Test
    public void testChat() {
        OllamaLlmConfig config = new OllamaLlmConfig();
        config.setEndpoint("http://localhost:11434");
        config.setModel("llama3");
        config.setDebug(true);

        Llm llm = new OllamaLlm(config);
        String chat = llm.chat("who are your");
        System.out.println(chat);
    }


    @Test
    public void testChatStream() throws InterruptedException {
        OllamaLlmConfig config = new OllamaLlmConfig();
        config.setEndpoint("http://localhost:11434");
        config.setModel("llama3");
        config.setDebug(true);

        Llm llm = new OllamaLlm(config);
        llm.chatStream("who are your", (context, response) -> System.out.println(response.getMessage().getContent()));

        Thread.sleep(20000);
    }


    @Test
    public void testEmbedding() {
        OllamaLlmConfig config = new OllamaLlmConfig();
        config.setEndpoint("http://localhost:11434");
        config.setModel("llama3.1");
        config.setDebug(true);

        Llm llm = new OllamaLlm(config);
        VectorData vectorData = llm.embed(Document.of("hello world"));
        System.out.println(vectorData);
    }


    @Test
    public void testFunctionCall() throws InterruptedException {
        OllamaLlmConfig config = new OllamaLlmConfig();
        config.setEndpoint("http://localhost:11434");
        config.setModel("llama3.1");
        config.setDebug(true);

        Llm llm = new OllamaLlm(config);


        FunctionPrompt prompt = new FunctionPrompt("What's the weather like in Beijing?", WeatherFunctions.class);
        FunctionMessageResponse response = llm.chat(prompt);

        Object result = response == null ? null : response.getFunctionResult();

        System.out.println(result);
    }


    @Test
    public void testVisionModel() {
        OllamaLlmConfig config = new OllamaLlmConfig();
        config.setEndpoint("http://localhost:11434");
        config.setModel("llava");
        config.setDebug(true);

        Llm llm = new OllamaLlm(config);

        ImagePrompt imagePrompt = new ImagePrompt("What's in the picture?", "https://agentsflex.com/assets/images/logo.png");

        AiMessageResponse response = llm.chat(imagePrompt);
        AiMessage message = response == null ? null : response.getMessage();
        System.out.println(message);
    }

}
