package com.challenge.technicalChallenge.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "call_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private String httpMethod;
    private String endpoint;

    @Column(columnDefinition = "TEXT")
    private String params;

    private int statusCode;

    @Column(columnDefinition = "TEXT")
    private String responseBody;

    private String errorMessage;
}
