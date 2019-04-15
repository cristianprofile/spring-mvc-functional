package com.cromero;

import java.time.LocalDateTime;

public class Transaction {

    private Long id;
    private Integer amount;
    private LocalDateTime createdAt;
    private String concept;

    public Transaction() { }

    public Transaction(Long id, Integer amount, LocalDateTime createdAt, String concept) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.concept = concept;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("amount=").append(amount);
        sb.append(", concept='").append(concept).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
