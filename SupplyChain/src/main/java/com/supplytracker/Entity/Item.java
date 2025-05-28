package com.supplytracker.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name="items")
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User supplier;
    private Date createdDate;

    public Item(String name, String category, User supplier){
        this.name=name;
        this.category=category;
        this.supplier=supplier;
        this.createdDate=new Date();
    }

    public String getCategory() {
        return category;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public User getSupplier() {
        return supplier;
    }
}
