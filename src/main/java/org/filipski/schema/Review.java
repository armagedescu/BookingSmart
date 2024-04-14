package org.filipski.schema;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Review")
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    LocalDateTime date;
    int rate = 1;

    @ManyToOne
    @JoinColumn(name="registry", nullable = false)
    private SmartphoneRegistry registry;
    @ManyToOne
    @JoinColumn(name="reviewer", nullable = false)
    Tester reviewer;

    public Review(){}
}
