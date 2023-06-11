package com.example.DoAnWebJava.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "tb_ReplyRated")
@RequiredArgsConstructor
@AllArgsConstructor
public class ReplyRated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ratedId", referencedColumnName = "id", insertable = false, updatable = false)
    private Rated rated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}

