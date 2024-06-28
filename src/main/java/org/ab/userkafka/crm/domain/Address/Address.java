package org.ab.userkafka.crm.domain.Address;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Address implements Serializable {
    @Id
    private Long id;
    private String street;
    private String city;
    private String state;

}
