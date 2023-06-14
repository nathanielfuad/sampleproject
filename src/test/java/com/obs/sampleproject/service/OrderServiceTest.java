package com.obs.sampleproject.service;

import com.obs.sampleproject.dto.OrderDto;
import com.obs.sampleproject.entity.Item;
import com.obs.sampleproject.entity.Order;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import com.obs.sampleproject.repository.ItemRepository;
import com.obs.sampleproject.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
    private ItemRepository itemRepository;
    private OrderService orderService;
    private ModelMapper modelMapper;

    @BeforeEach
    void initTestCase() {
        modelMapper = new ModelMapper();
        orderService = new OrderService(orderRepository,itemRepository, modelMapper);
    }

    @Test
    void whenGetAllOrder_ThenReturnAlLOrder(){
        Order order = generateMockOrder((int) 1L);
        List<Order> orderList = Collections.singletonList(order);
        when(orderRepository.findAll()).thenReturn(orderList);

        List<OrderDto> orderDtoList = orderService.getAllOrder();

        assertThat(orderDtoList).isNotNull();
        assertThat(orderDtoList.size()).isEqualTo(orderList.size());
        assertThat(orderDtoList.get(0).getId()).isEqualTo(order.getId());
    }

    @Test
    void whenGetOrder_ThenReturnOrder(){
        Order order = generateMockOrder((int) 1L);
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(order));

        assertThat(orderService.getOrder((int) 1L)).isNotNull();
    }

    @Test
    void whenSaveOrder_ThenReturnSavedOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNo("O1");
        orderDto.setItemId((int) 1L);
        orderDto.setQty(5);

        Item item = new Item();
        item.setId((int) 1L);
        item.setPrice(1000);
        item.setName("test");
        when(itemRepository.findById((int) 1L)).thenReturn(Optional.of(item));

        Order mockOrder = new Order();
        mockOrder.setId((int) 1L);
        mockOrder.setOrderNo(orderDto.getOrderNo());
        mockOrder.setItem(itemRepository.findById(orderDto.getItemId()).orElse(null));
        mockOrder.setQty(orderDto.getQty());
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderDto orderDtoResult = orderService.saveOrder(orderDto);
        assertThat(orderDtoResult).isNotNull();
        assertThat(orderDtoResult.getId()).isEqualTo(mockOrder.getId());
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

        when(itemRepository.findById((int) 1L)).thenReturn(Optional.of(item));
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(mockOrder));
        assertEquals(Optional.of(mockOrder), orderRepository.findById((int) 1L));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderDto orderDtoResult = orderService.updateOrder((int) 1L, orderDto);
        assertThat(orderDtoResult).isNotNull();
        assertThat(orderDtoResult.getId()).isEqualTo((int) 1L);
        assertThat(orderDtoResult.getItemId()).isEqualTo(orderDto.getItemId());
        assertThat(orderDtoResult.getOrderNo()).isEqualTo(orderDto.getOrderNo());
        assertThat(orderDtoResult.getQty()).isEqualTo(orderDto.getQty());
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

        when(itemRepository.findById((int) 1L)).thenReturn(Optional.of(item));
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(mockOrder));
        assertEquals(Optional.of(mockOrder), orderRepository.findById((int) 1L));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderDto orderDtoResult = orderService.updateOrder((int) 1L, orderDto);
        assertThat(orderDtoResult).isNotNull();
        assertThat(orderDtoResult.getId()).isEqualTo((int) 1L);
        assertThat(orderDtoResult.getItemId()).isEqualTo(orderDto.getItemId());
        assertThat(orderDtoResult.getOrderNo()).isEqualTo(generateMockOrder((int) 1L).getOrderNo());
        assertThat(orderDtoResult.getQty()).isEqualTo(orderDto.getQty());
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

        when(itemRepository.findById((int) 1L)).thenReturn(Optional.of(item));
        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(mockOrder));
        assertEquals(Optional.of(mockOrder), orderRepository.findById((int) 1L));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderDto orderDtoResult = orderService.updateOrder((int) 1L, orderDto);
        assertThat(orderDtoResult).isNotNull();
        assertThat(orderDtoResult.getId()).isEqualTo((int) 1L);
        assertThat(orderDtoResult.getItemId()).isEqualTo(orderDto.getItemId());
        assertThat(orderDtoResult.getOrderNo()).isEqualTo(orderDto.getOrderNo());
        assertThat(orderDtoResult.getQty()).isEqualTo(generateMockOrder((int) 1L).getQty());
    }


    @Test
    void whenUpdateOrderWithoutInputItemId_ThenUpdateOrderWithoutUpdateTheItem() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderNo("O2");
        orderDto.setQty(3);

        Order mockOrder = generateMockOrder((int) 1L);

        Item item = new Item();
        item.setId((int) 1L);
        item.setPrice(1000);
        item.setName("test");

        when(orderRepository.findById((int) 1L)).thenReturn(Optional.of(mockOrder));
        assertEquals(Optional.of(mockOrder), orderRepository.findById((int) 1L));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderDto orderDtoResult = orderService.updateOrder((int) 1L, orderDto);
        assertThat(orderDtoResult).isNotNull();
        assertThat(orderDtoResult.getId()).isEqualTo((int) 1L);
        assertThat(orderDtoResult.getItemId()).isEqualTo(generateMockOrder((int) 1L).getItem().getId());
        assertThat(orderDtoResult.getOrderNo()).isEqualTo(orderDto.getOrderNo());
        assertThat(orderDtoResult.getQty()).isEqualTo(orderDto.getQty());
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
