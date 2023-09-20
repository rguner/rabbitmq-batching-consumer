package com.guner.consumer.queue;

import com.guner.consumer.entity.ChargingRecord;
import com.guner.consumer.service.ChargingRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqListener {

    private final ChargingRecordService chargingRecordService;

    /**
     * inserts charging table
     * if exception occurs message is rollbacked
     * @param chargingRecord
     */
    @RabbitListener(queues = {"${batch-consumer.queue.name.batch-queue}"})
    public void receiveMessage(ChargingRecord chargingRecord) {
        log.debug("Charging: Received <{} {}>", chargingRecord.getSourceGsm(), chargingRecord.getTargetGsm());
        chargingRecordService.createChargingRecord(chargingRecord);
    }
}
