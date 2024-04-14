package org.filipski.schema;

import jakarta.persistence.*;

@Entity
@Table(name = "Smartphone")
public class Smartphone {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="registry", nullable = false)
    private SmartphoneRegistry registry;

    @SuppressWarnings("unused")
    public Smartphone(){}
    public Smartphone(SmartphoneRegistry registry){this.registry = registry;}

    public SmartphoneRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(SmartphoneRegistry registry) {
        this.registry = registry;
    }
    public String getName() {
        return registry.getName();
    }

}
