package com.kakkerla.spring.intg.demo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String type;
    private String amount;

    public String toString() {
        return "{id=" + id + ", type=" + type + ", amount=" + amount + "}";
    }
}
