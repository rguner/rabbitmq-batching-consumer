package com.guner.consumer.queue;

import com.guner.consumer.entity.ChargingRecord;
import com.guner.consumer.service.ChargingRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

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
    /*
    @RabbitListener(queues = {"${batch-consumer.queue.name.batch-queue}"})
    public void receiveMessage(ChargingRecord chargingRecord) {
        log.debug("Charging: Received <{} {}> , thread: {}", chargingRecord.getSourceGsm(), chargingRecord.getTargetGsm(), Thread.currentThread().getName());
        chargingRecordService.createChargingRecord(chargingRecord);
    }
     */

    /**
     * with rabbitBatchListenerContainerFactory, it consumes messages as batch
     */
    @RabbitListener(queues = "${batch-consumer.queue.name.batch-queue}", containerFactory = "rabbitBatchListenerContainerFactory")
    public void listenBatch(List<ChargingRecord> listChargingRecord) {
        log.debug("Charging List: Received <{} {}> , thread: {}", listChargingRecord.size(), Thread.currentThread().getName());
        listChargingRecord.forEach(chargingRecord -> chargingRecordService.createChargingRecord(chargingRecord));
    }


    /**
     * with rabbitListenerContainerFactory, it consumes message one by one
     */
    /*
    @RabbitListener(queues = {"${batch-consumer.queue.name.batch-queue}"}, containerFactory = "rabbitListenerContainerFactory")
    public void receiveMessage(ChargingRecord chargingRecord) {
        log.debug("Charging: Received <{} {}> , thread: {}", chargingRecord.getSourceGsm(), chargingRecord.getTargetGsm(), Thread.currentThread().getName());
        chargingRecordService.createChargingRecord(chargingRecord);
    }
     */

    /*
    uygulama hata veriyor,  rabbitBatchListenerContainerFactory  factory.setBatchListener(true); olduğu için
     */
    /*
    @RabbitListener(queues = "${batch-consumer.queue.name.batch-queue}", containerFactory = "rabbitBatchListenerContainerFactory")
    public void listenBatchAsDebatch(ChargingRecord chargingRecord) {
        log.debug("Charging: Received as Debatched <{} {}> , thread: {}", chargingRecord.getSourceGsm(), chargingRecord.getTargetGsm(), Thread.currentThread().getName());
        chargingRecordService.createChargingRecord(chargingRecord);
    }

     */

}
