package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@Setter
@Getter
@RequiredArgsConstructor
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "rate should not be empty")
    @Min(value = 1, message = "Rate should be at least 1")
    @Max(value = 5, message = "Rate should be at most 5")
    @Column(columnDefinition = "decimal not null ")
    private Integer rate ;

    private String review ;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Booking booking;

}
