package com.shop.common.model;

import lombok.Data;
import org.springframework.context.annotation.Import;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;
import java.time.LocalDateTime;

public class TimeTracker {
    private Instant createdAt;
    private Instant updatedAt;
}
