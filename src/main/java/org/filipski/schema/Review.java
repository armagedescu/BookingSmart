package org.filipski.schema;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Review")
public class Review {
    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    @JoinColumn(name="registry", nullable = false)
    private SmartphoneRegistry registry;
    private String comment;
    private int rate = 1;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name="reviewer", nullable = false)
    private
    Tester reviewer;
    @OneToOne
    @JoinColumn(name="schedule")
    private Schedule schedule;



    public Review(){}
    public Review(SmartphoneRegistry registry, String comment, int rate, LocalDateTime date, Tester reviewer, Schedule schedule)
    {
        this(registry, comment, rate, date, reviewer);
        setSchedule(schedule);
    }
    public Review(SmartphoneRegistry registry, String comment, int rate, LocalDateTime date, Tester reviewer)
    {
        setRegistry(registry);
        setComment(comment);
        setRate(rate);
        setDate(date);
        setReviewer(reviewer);
    }

    public SmartphoneRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(SmartphoneRegistry registry) {
        this.registry = registry;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Tester getReviewer() {
        return reviewer;
    }

    public void setReviewer(Tester reviewer) {
        this.reviewer = reviewer;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

}
