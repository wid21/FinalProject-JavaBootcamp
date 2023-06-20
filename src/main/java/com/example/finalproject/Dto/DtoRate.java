package com.example.finalproject.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoRate {
    private Integer bookingId;
    private Integer customerId;
    @NotNull(message = "rate should not be empty")
    @Min(value = 1, message = "Rate should be at least 1")
    @Max(value = 5, message = "Rate should be at most 5")
    @Column(columnDefinition = "integer not null ")
    private Integer rate ;
    private String review ;
}
