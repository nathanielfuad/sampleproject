package com.obs.sampleproject.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.obs.sampleproject.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{


}
