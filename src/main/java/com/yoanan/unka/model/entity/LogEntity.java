package com.yoanan.unka.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="log_time")
public class LogEntity extends BaseEntity{

    private String requestUrl;
    private LocalDateTime dateTime;
    private Long timeTaken;


    public LogEntity(String requestUrl, LocalDateTime dateTime, Long timeTaken) {
        this.requestUrl = requestUrl;
        this.dateTime = dateTime;
        this.timeTaken = timeTaken;
    }

    public LogEntity() {
    }

    @Column(name="request_url")
    public String getRequestUrl() {
        return requestUrl;
    }

    public LogEntity setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    @Column(name="date_time")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LogEntity setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Column(name="time_taken")
    public Long getTimeTaken() {
        return timeTaken;
    }

    public LogEntity setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
        return this;
    }
}
