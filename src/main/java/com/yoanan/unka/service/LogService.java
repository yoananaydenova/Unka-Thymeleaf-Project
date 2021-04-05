package com.yoanan.unka.service;

public interface LogService {

    void createLog(String requestUrl, Long timeTaken);

    void clearAllLogs();
}
