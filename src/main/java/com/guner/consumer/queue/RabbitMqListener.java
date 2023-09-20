package com.guner.consumer.queue;

import com.guner.consumer.entity.ChargingRecord;
import com.guner.consumer.service.ChargingRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * https://docs.spring.io/spring-amqp/docs/current/reference/html/#receiving-batch
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqListener {

    private final ChargingRecordService chargingRecordService;

    /**
     * without any containerFactory, it consumes message one by one
     */


    @RabbitListener(queues = {"${batch-consumer.queue.name.batch-queue}"})
    public void receiveMessage(ChargingRecord chargingRecord) {
        log.debug("Charging: Received <{} {}> , thread: {}", chargingRecord.getSourceGsm(), chargingRecord.getTargetGsm(), Thread.currentThread().getName());
        chargingRecordService.createChargingRecord(chargingRecord);
    }


    /*
    @RabbitListener(queues = {"${batch-consumer.queue.name.batch-queue}"}, containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(ChargingRecord chargingRecord) {
        log.debug("Charging: Received <{} {}> , thread: {}", chargingRecord.getSourceGsm(), chargingRecord.getTargetGsm(), Thread.currentThread().getName());
        chargingRecordService.createChargingRecord(chargingRecord);
    }

     */


}
