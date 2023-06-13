package com.obs.sampleproject.service;

import com.obs.sampleproject.model.entity.Item;
import com.obs.sampleproject.model.entity.Order;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import com.obs.sampleproject.model.input.OrderCreateInput;
import com.obs.sampleproject.model.input.OrderUpdateInput;
import com.obs.sampleproject.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ItemService itemService;
    private OrderService orderService;

    @BeforeEach
    void initTestCase() {
        orderService = new OrderService(orderRepository,itemService);
    }

    @Test
    void whenGetAllOrder_ThenReturnAlLOrder(){
        Order order = generateMockOrder(1);
        List<Order> listOrder = Collections.singletonList(order);
        when(orderRepository.findAll()).thenReturn(listOrder);

        assertEquals(listOrder,orderService.getAllOrder());
    }

    @Test
    void whenGetOrder_ThenReturnOrder(){
        Order order = generateMockOrder(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        assertEquals(order, orderService.getOrder(1));
    }

    @Test
    void whenSaveOrder_ThenReturnSavedOrder() {
        OrderCreateInput orderCreateInput = new OrderCreateInput();
        orderCreateInput.setOrderNo("O1");
        orderCreateInput.setItemId(1);
        orderCreateInput.setQty(5);

        Item item = new Item();
        item.setId(1);
        item.setPrice(1000);
        item.setName("test");
        when(itemService.getItem(1)).thenReturn(item);

        Order mockOrder = new Order();
        mockOrder.setOrderNo(orderCreateInput.getOrderNo());
        mockOrder.setItem(itemService.getItem(orderCreateInput.getItemId()));
        mockOrder.setQty(orderCreateInput.getQty());
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        assertThat(orderService.saveOrder(orderCreateInput)).isNotNull();
    }

    @Test
    void whenUpdateOrder_ThenReturnUpdatedOrder() {
        OrderUpdateInput orderUpdateInput = new OrderUpdateInput();
        orderUpdateInput.setId(1);
        orderUpdateInput.setOrderNo("O1");
        orderUpdateInput.setQty(3);
        orderUpdateInput.setItemId(1);

        Order mockOrder = generateMockOrder(1);

        Item item = new Item();
        item.setId(1);
        item.setPrice(1000);
        item.setName("test");

        when(itemService.getItem(1)).thenReturn(item);
        when(orderRepository.findById(1)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any())).thenReturn(mockOrder);

        Order expectedOrder = new Order();
        expectedOrder.setId(1);
        expectedOrder.setOrderNo("O1");
        expectedOrder.setQty(3);
        expectedOrder.setItem(item);

        assertEquals(expectedOrder, orderService.updateOrder(orderUpdateInput));
    }

    @Test
    void whenUpdateOrder_AndOrderNotFound_ThenReturnException() {
        OrderUpdateInput orderUpdateInput = new OrderUpdateInput();
        orderUpdateInput.setId(1);
        orderUpdateInput.setOrderNo("O1");
        orderUpdateInput.setQty(3);
        orderUpdateInput.setItemId(1);

        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(GeneralErrorException.class, () -> {
            orderService.updateOrder(orderUpdateInput);
        });

    }

    @Test
    void whenDeleteOrder_ThenReturnNormal() {
        Order mockOrder = generateMockOrder(1);
        when(orderRepository.findById(1)).thenReturn(Optional.of(mockOrder));

        assertThat(orderService.deleteOrder(1)).isNotNull();
    }

    @Test
    void whenDeleteOrder_AndOrderNotFound_ThenReturnException() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(GeneralErrorException.class, () -> {
            orderService.deleteOrder(1);
        });
    }

    Order generateMockOrder(int id){
        Order order = new Order();
        order.setId(id);
        order.setOrderNo("O1");
        order.setQty(1);
        order.setItem(new Item());
        return order;
    }
}
