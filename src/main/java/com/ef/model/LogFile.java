package com.ef.model;

import javax.persistence.*;
import java.util.Date;

/**
 * LogFile entity
 * name: LogFile.java
 * date: Oct 24, 2018
 *
 * @author Fernando Molina
 * @version 1.0
 */
@Entity
@Table(name = "log_file")
public class LogFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "ip")
    private String ip;
    @Column(name = "request")
    private String request;
    @Column(name = "status")
    private Integer status;
    @Column(name = "user_agent")
    private String userAgent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder("Log File: {");
        info.append("date:");
        info.append(this.date);
        info.append(",");
        info.append("ip:");
        info.append(this.ip);
        info.append(",");
        info.append("request:");
        info.append(this.request);
        info.append(",");
        info.append("userAgent:");
        info.append(this.userAgent);
        info.append("}");

        return info.toString();
    }
}
