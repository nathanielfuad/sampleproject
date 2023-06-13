package com.obs.sampleproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obs.sampleproject.config.ExceptionHandlerConfig;
import com.obs.sampleproject.model.entity.Order;
import com.obs.sampleproject.model.input.OrderCreateInput;
import com.obs.sampleproject.model.input.OrderUpdateInput;
import com.obs.sampleproject.repository.ItemRepository;
import com.obs.sampleproject.repository.OrderRepository;
import com.obs.sampleproject.service.OrderService;
import com.obs.sampleproject.util.ResponseUtil;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = {OrderController.class}
)
@Import({ExceptionHandlerConfig.class, ResponseUtil.class})
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ItemRepository itemRepository;

    @Test
    void whenGetOrders_ThenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/order"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void whenCreateOrder_ThenStatus200() throws Exception {
        OrderCreateInput orderCreateInput = new OrderCreateInput();
        orderCreateInput.setItemId((int) 1L);
        orderCreateInput.setOrderNo(anyString());
        orderCreateInput.setQty((int) 1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writer().writeValueAsString(orderCreateInput);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void whenUpdateOrder_ThenStatus200() throws Exception {
        OrderUpdateInput orderUpdateInput = new OrderUpdateInput();
        orderUpdateInput.setItemId((int) 1L);
        orderUpdateInput.setOrderNo(anyString());
        orderUpdateInput.setQty((int) 1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writer().writeValueAsString(orderUpdateInput);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void whenDeleteOrder_ThenStatus200() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/order")
                                .param("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
