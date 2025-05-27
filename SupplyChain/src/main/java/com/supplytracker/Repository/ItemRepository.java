
package com.supplytracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supplytracker.Entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
