package com.obs.sampleproject.service;

import com.obs.sampleproject.dto.OrderDto;
import com.obs.sampleproject.entity.Item;
import com.obs.sampleproject.entity.Order;
import com.obs.sampleproject.model.exception.GeneralErrorException;
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
        Order order = generateMockOrder((int) 1L);
        List<Order> listOrder = Collections.singletonList(order);
        when(orderRepository.findAll()).thenReturn(listOrder);

        assertEquals(listOrder,orderService.getAllOrder());
    }

    @Test
    void whenGetOrder_ThenReturnOrder(){
        Order order = generateMockOrder((int) 1L);
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(order));

        assertEquals(order, orderService.getOrder((int) 1L));
    }

    @Test
    void whenSaveOrder_ThenReturnSavedOrder() {
        com.obs.sampleproject.dto.OrderDto orderDto = new com.obs.sampleproject.dto.OrderDto();
        orderDto.setOrderNo("O1");
        orderDto.setItemId((int) 1L);
        orderDto.setQty(5);

        Item item = new Item();
        item.setId((int) 1L);
        item.setPrice(1000);
        item.setName("test");
        when(itemService.getItem((int) 1L)).thenReturn(item);

        Order mockOrder = new Order();
        mockOrder.setOrderNo(orderDto.getOrderNo());
        mockOrder.setItem(itemService.getItem(orderDto.getItemId()));
        mockOrder.setQty(orderDto.getQty());
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        assertThat(orderService.saveOrder(orderDto)).isNotNull();
    }

    @Test
    void whenUpdateOrder_ThenReturnUpdatedOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNo("O2");
        orderDto.setQty(3);
        orderDto.setItemId((int) 1L);

        Order mockOrder = generateMockOrder((int) 1L);

        Item item = new Item();
        item.setId((int) 1L);
        item.setPrice(1000);
        item.setName("test");

        when(itemService.getItem((int) 1L)).thenReturn(item);
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any())).thenReturn(mockOrder);

        assertEquals(Optional.of(mockOrder), orderRepository.findById((int) 1L));

        Order expectedOrder = new Order();
        expectedOrder.setId((int) 1L);
        expectedOrder.setOrderNo("O2");
        expectedOrder.setQty(3);
        expectedOrder.setItem(item);

        assertEquals(expectedOrder, orderService.updateOrder((int) 1L, orderDto));
    }

    @Test
    void whenUpdateOrder_AndOrderNotFound_ThenReturnException() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNo("O1");
        orderDto.setQty(3);
        orderDto.setItemId((int) 1L);

        when(orderRepository.findById((int) 1L)).thenReturn(Optional.empty());

        assertThrows(GeneralErrorException.class, () -> {
            orderService.updateOrder((int) 1L, orderDto);
        });

    }

    @Test
    void whenUpdateOrderWithoutInputOrderNo_ThenUpdateOrderWithoutUpdateTheOrderNo() {
        OrderDto orderDto = new OrderDto();
        orderDto.setQty(3);
        orderDto.setItemId((int) 1L);

        Order mockOrder = generateMockOrder((int) 1L);

        Item item = new Item();
        item.setId((int) 1L);
        item.setPrice(1000);
        item.setName("test");

        when(itemService.getItem((int) 1L)).thenReturn(item);
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any())).thenReturn(mockOrder);

        assertEquals(Optional.of(mockOrder), orderRepository.findById((int) 1L));

        Order expectedOrder = new Order();
        expectedOrder.setId((int) 1L);
        expectedOrder.setOrderNo("O1");
        expectedOrder.setQty(3);
        expectedOrder.setItem(item);

        assertEquals(expectedOrder, orderService.updateOrder((int) 1L, orderDto));
    }

    @Test
    void whenUpdateOrderWithoutInputQty_ThenUpdateOrderWithoutUpdateTheQty() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNo("O2");
        orderDto.setItemId((int) 1L);

        Order mockOrder = generateMockOrder((int) 1L);

        Item item = new Item();
        item.setId((int) 1L);
        item.setPrice(1000);
        item.setName("test");

        when(itemService.getItem((int) 1L)).thenReturn(item);
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any())).thenReturn(mockOrder);

        assertEquals(Optional.of(mockOrder), orderRepository.findById((int) 1L));

        Order expectedOrder = new Order();
        expectedOrder.setId((int) 1L);
        expectedOrder.setOrderNo("O2");
        expectedOrder.setQty(1);
        expectedOrder.setItem(item);

        assertEquals(expectedOrder, orderService.updateOrder((int) 1L, orderDto));
    }


    @Test
    void whenUpdateOrderWithoutInputItemId_ThenUpdateOrderWithoutUpdateTheItem() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNo("O2");
        orderDto.setQty(3);

        Order mockOrder = generateMockOrder((int) 1L);

        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(mockOrder));
        when(orderRepository.save(any())).thenReturn(mockOrder);

        assertEquals(Optional.of(mockOrder), orderRepository.findById((int) 1L));

        Order expectedOrder = new Order();
        expectedOrder.setId((int) 1L);
        expectedOrder.setOrderNo("O2");
        expectedOrder.setQty(3);
        expectedOrder.setItem(new Item());

        assertEquals(expectedOrder, orderService.updateOrder((int) 1L, orderDto));
    }

    @Test
    void whenDeleteOrder_ThenReturnNormal() {
        Order mockOrder = generateMockOrder((int) 1L);
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(mockOrder));

        assertThat(orderService.deleteOrder((int) 1L)).isNotNull();
    }

    @Test
    void whenDeleteOrder_AndOrderNotFound_ThenReturnException() {
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.empty());

        assertThrows(GeneralErrorException.class, () -> {
            orderService.deleteOrder((int) 1L);
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
