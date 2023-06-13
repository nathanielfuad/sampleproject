package com.obs.sampleproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obs.sampleproject.config.ExceptionHandlerConfig;
import com.obs.sampleproject.dto.OrderDto;
import com.obs.sampleproject.repository.ItemRepository;
import com.obs.sampleproject.repository.OrderRepository;
import com.obs.sampleproject.service.OrderService;
import com.obs.sampleproject.util.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        com.obs.sampleproject.dto.OrderDto orderDto = new com.obs.sampleproject.dto.OrderDto();
        orderDto.setItemId((int) 1L);
        orderDto.setOrderNo(anyString());
        orderDto.setQty((int) 1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writer().writeValueAsString(orderDto);

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
        OrderDto orderDto = new OrderDto();
        orderDto.setItemId((int) 1L);
        orderDto.setOrderNo(anyString());
        orderDto.setQty((int) 1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writer().writeValueAsString(orderDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/order")
                                .param("id","1")
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
