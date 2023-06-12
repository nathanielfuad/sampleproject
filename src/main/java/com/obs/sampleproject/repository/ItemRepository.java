package com.obs.sampleproject.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.obs.sampleproject.entity.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{


}
