package com.tal.wangxiao.conan.common.service.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tal.wangxiao.conan.common.kafaka.TaskMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

/**
 * kafka相关的消息服务
 *
 * @author mtx
 * @date 2021/12/15
 **/
@Service
@Slf4j
public class KafkaMessageService {

    @Resource
    private KafkaTemplate<String, String> producer;


    /**
     * 发送kafka消息
     * @param taskMessage 消息体
     * @param topic 消息topic
     */
    public void sendKafkaMessage(TaskMessage taskMessage, String topic ){
        log.info("消息写入:"+taskMessage.getData().toString());
        ListenableFuture<SendResult<String, String>> future = null;
        try {
            future = producer.send(topic, new ObjectMapper().writeValueAsString(taskMessage));
        } catch (JsonProcessingException e) {
            log.info("数据格式异常，无法转化成json串");
        }catch (NullPointerException e){
            log.error("kafka对象为null 请检查");
        }
    }


}
