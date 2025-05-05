package com.mic.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "activity_logs")
public class ActivityLog {

    @MongoId
    public String id;
    public String activityType;
    public String sourceActivityType;
    public String data;
    private LocalDateTime timestamp;
    private long creatorUserId;

}
