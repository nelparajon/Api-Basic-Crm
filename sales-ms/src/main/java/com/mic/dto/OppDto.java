package com.mic.dto;

import com.mic.utils.OppPriority;
import com.mic.utils.OppSource;
import com.mic.utils.OppState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.OffsetDateTime;

public class OppDto {

    private String publicId;
    private String title;
    private String description;
    //definimos que este campo será por defecto el enum NUEVA en formato STRING
    private OppState state;
    private OppPriority priority;
    private Double amount;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private long customerId;
    private long contactId;
    private String assignedUserId;
    private OppSource source;
    private String notes;

    public OppDto() {
    }

    public OppDto(String title, String description, OppState state, OppPriority priority, Double amount, OffsetDateTime startDate, OffsetDateTime endDate, long customerId, long contactId, String assignedUserId, OppSource source, String notes) {
        this.publicId = publicId;
        this.title = title;
        this.description = description;
        this.state = state;
        this.priority = priority;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.contactId = contactId;
        this.assignedUserId = assignedUserId;
        this.source = source;
        this.notes = notes;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OppState getState() {
        return state;
    }

    public void setState(OppState state) {
        this.state = state;
    }

    public OppPriority getPriority() {
        return priority;
    }

    public void setPriority(OppPriority priority) {
        this.priority = priority;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(String assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public OppSource getSource() {
        return source;
    }

    public void setSource(OppSource source) {
        this.source = source;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
