
package com.supplytracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplytracker.Entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findBySupplierId(long userId);
}
