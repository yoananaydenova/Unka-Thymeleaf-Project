package com.yoanan.unka.service.impl;

import com.yoanan.unka.model.entity.LogEntity;
import com.yoanan.unka.repository.LogRepository;
import com.yoanan.unka.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private Logger LOGGER = LoggerFactory.getLogger(CarouselServiceImpl.class);

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    @Override
    public void createLog(String requestUrl, Long timeTaken) {
        if (requestUrl != null && timeTaken != null) {
            LocalDateTime localDateTime = LocalDateTime.now();
            LogEntity logEntity = new LogEntity(requestUrl,localDateTime, timeTaken);
            logRepository.save(logEntity);
        }
    }

    @Override
    @Scheduled(cron = "${log-info.clear-cron}")
    public void clearAllLogs() {
        LOGGER.info("Clear all logs info...");
        logRepository.deleteAll();
    }
}
