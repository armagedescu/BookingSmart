package org.filipski.schema;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Smartphone")
public class Smartphone {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="registry", nullable = false)
    private SmartphoneRegistry registry;

    @Transient
    private boolean availableNow = false;
    @Transient
    private int schedulesCount = 0;
    @Transient
    private Integer availableDays = null;

    @SuppressWarnings("unused")
    public Smartphone(){}
    public Smartphone(SmartphoneRegistry registry){this.registry = registry;}

    public SmartphoneRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(SmartphoneRegistry registry) {
        this.registry = registry;
    }

    public boolean isAvailableNow() {
        return availableNow;
    }

    public void setAvailableNow(boolean availableNow) {
        this.availableNow = availableNow;
    }
    public String getName() {
        return registry.getName();
    }

    public Long getId() {
        return id;
    }

    public int getSchedulesCount() {
        return schedulesCount;
    }

    public void setSchedulesCount(int schedulesCount) {
        this.schedulesCount = schedulesCount;
    }

    //if not limited by upper date, use some big number
    public Integer getAvailableDays() {
        if (!isAvailableNow()) return 0;
        if (availableDays == null) return 1000;
        return availableDays;
    }

    public void setAvailableDays(Integer availableDays) {
        this.availableDays = availableDays;
    }
}
