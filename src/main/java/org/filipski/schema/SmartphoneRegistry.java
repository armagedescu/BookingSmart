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
    @SuppressWarnings("unused")
    public SmartphoneRegistry(){}
    public SmartphoneRegistry(String name){this.setName(name);}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
