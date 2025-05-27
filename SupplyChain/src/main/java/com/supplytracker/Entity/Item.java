package com.supplytracker.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name="items")

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String category;
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "supplier_id", nullable = false)
    // for foreign key
    private long supplierId;//fk
    private Date createdDate;

    public Item(String name, String category, long supplierId){
        this.name=name;
        this.category=category;
        this.supplierId=supplierId;
        this.createdDate=new Date();
    }
}
