package com.mic.model;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.mic.utils.OppPriority;
import com.mic.utils.OppSource;
import com.mic.utils.OppState;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "opportunities")
public class Opportunity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "opp_seq")
    @SequenceGenerator(name="opp_seq", sequenceName = "opp_seq", allocationSize = 1)
    private Long id;

    @Column(name = "public_id", updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private String publicId;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(length = 640)
    private String description;

    //definimos que este campo será por defecto el enum NUEVA en formato STRING
    //los valores por defecto solo se setean a la entity cuando se usa new Entity().
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OppState state = OppState.NUEVA;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private OppPriority priority = OppPriority.MEDIA;

    @Column(nullable = false)
    private Double amount = 0.0;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    private OffsetDateTime startDate;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    private OffsetDateTime endDate = null;

    @Column(name = "customer_id", nullable = false)
    private long customerId;

    @Column(name= "contact_id", nullable = false)
    private long contactId;

    @Column(name = "assigned_user_id", nullable = false)
    private String assignedUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private OppSource source;

    @Column(length = 2048)
    private String notes;

    public Opportunity(){

    }

    public Opportunity(String title, String description, OppState state, OppPriority priority, Double amount, OffsetDateTime startDate, OffsetDateTime endDate, long customerId, long contactId) {
        this.publicId = NanoIdUtils.randomNanoId();
        this.title = title;
        this.description = description;
        this.state = state;
        this.priority = priority;
        this.amount = amount != null ? amount : 0.0;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.contactId = contactId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OffsetDateTime getEnd_date() {
        return endDate;
    }

    public void setEnd_date(OffsetDateTime end_date) {
        this.endDate = end_date;
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

    @PrePersist
    public void prePersist() {
        if (this.publicId == null) {
            this.publicId = NanoIdUtils.randomNanoId();
        }
    }

}
