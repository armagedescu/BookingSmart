package org.filipski.schema;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "Schedule")
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    @Column (nullable = false)
    private
    LocalDateTime start;
    @Column (nullable = true)
    private
    LocalDateTime finish;

    @ManyToOne
    @JoinColumn(name="smartphone", nullable = false)
    private Smartphone smartphone;
    @ManyToOne
    @JoinColumn(name="reviewer", nullable = false)
    private
    Tester reviewer;

    @Transient
    private boolean unallocated = false;

    public Schedule(){}
    public Schedule(Smartphone smartphone, Tester reviewer, LocalDateTime start, LocalDateTime finish)
    {
        setSmartphone(smartphone);
        setReviewer(reviewer);
        setStart(start);
        setFinish(finish);
    }
    public Schedule(Smartphone smartphone, Tester reviewer, LocalDateTime start, int days)
    {
        setSmartphone(smartphone);
        setReviewer(reviewer);
        setStart(start);
        setFinish(start.plusDays(days));
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public Smartphone getSmartphone() {
        return smartphone;
    }

    public void setSmartphone(Smartphone smartphone) {
        this.smartphone = smartphone;
    }

    public Tester getReviewer() {
        return reviewer;
    }

    public void setReviewer(Tester reviewer) {
        this.reviewer = reviewer;
    }

    public boolean isUnallocated() {
        return unallocated;
    }

    public void setUnallocated(boolean unallocated) {
        this.unallocated = unallocated;
    }
    public int getMaxDays()
    {
        if (getFinish() == null) return 1000;
        return (int) ChronoUnit.DAYS.between(getStart(), getFinish());
    }
}
