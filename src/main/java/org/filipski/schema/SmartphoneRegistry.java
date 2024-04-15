package org.filipski.schema;

import jakarta.persistence.*;

/**
 * This is the registry of valid smartphone names
 */
@Entity
@Table(name = "SmartphoneRegistry")
public class SmartphoneRegistry {

    @Id
    @Column(unique = true, nullable = false)
    private String name;

    @Transient
    private int deviceCount;
    @Transient
    private int totalReviews;
    @Transient
    private float avgRating;
    @SuppressWarnings("unused")
    public SmartphoneRegistry(){}
    public SmartphoneRegistry(String name){this.setName(name);}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }
}
