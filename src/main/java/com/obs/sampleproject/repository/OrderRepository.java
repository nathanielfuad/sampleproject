package com.obs.sampleproject.repository;

import com.obs.sampleproject.entity.ItemOrderedDetailsInterface;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.obs.sampleproject.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    @Query("select item.id as itemId, count(1) as numberOfOrders, SUM(qty) as totalQtyOrdered from Order group by item.id")
    List<ItemOrderedDetailsInterface> findSumQtyOfEachItem();
}
