/*
 *  Copyright (c) 2023-2025, Agents-Flex (fuhai999@gmail.com).
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.agentsflex.store.redis.test;

import com.agentsflex.core.document.Document;
import com.agentsflex.core.llm.Llm;
import com.agentsflex.core.store.SearchWrapper;
import com.agentsflex.llm.spark.SparkLlm;
import com.agentsflex.llm.spark.SparkLlmConfig;
import com.agentsflex.store.redis.RedisVectorStore;
import com.agentsflex.store.redis.RedisVectorStoreConfig;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        SparkLlmConfig sparkLlmConfig = new SparkLlmConfig();
        sparkLlmConfig.setAppId("****");
        sparkLlmConfig.setApiKey("****");
        sparkLlmConfig.setApiSecret("****");

        Llm llm = new SparkLlm(sparkLlmConfig);

        RedisVectorStoreConfig config = new RedisVectorStoreConfig();
        config.setUri("redis://localhost:6379");
        config.setDefaultCollectionName("test005");

        RedisVectorStore store = new RedisVectorStore(config);
        store.setEmbeddingModel(llm);

        Document document = new Document();
        document.setContent("你好");
        document.setId(1);
        store.store(document);


        Document document2 = new Document();
        document2.setContent("Agents-Flex 是一个优雅的 LLM（大语言模型）应用开发框架");
        document2.setId(2);
        store.store(document2);


        Document document3 = new Document();
        document3.setContent("明天不下雨");
        document3.setId(3);
        store.store(document3);

        SearchWrapper sw = new SearchWrapper();
        sw.setText("明天下雨吗");

        List<Document> search = store.search(sw);
        System.out.println(search);


//        StoreResult result = store.delete("1");
//        LogUtil.println("-------delete-----" + result);
//        search = store.search(sw);
//        System.out.println(search);
    }
}
