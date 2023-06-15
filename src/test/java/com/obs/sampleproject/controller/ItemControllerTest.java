package com.obs.sampleproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obs.sampleproject.config.ExceptionHandlerConfig;
import com.obs.sampleproject.dto.ItemDto;
import com.obs.sampleproject.repository.ItemRepository;
import com.obs.sampleproject.repository.OrderRepository;
import com.obs.sampleproject.service.ItemService;
import com.obs.sampleproject.util.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {ItemController.class}
)
@Import({ExceptionHandlerConfig.class, ResponseUtil.class})
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private OrderRepository orderRepository;

    @Test
    void whenGetItems_ThenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/item"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void whenCreateItem_ThenStatus200() throws Exception {
        com.obs.sampleproject.dto.ItemDto itemDto = new com.obs.sampleproject.dto.ItemDto();
        itemDto.setName(anyString());
        itemDto.setPrice((int) 1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writer().writeValueAsString(itemDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void whenUpdateItem_ThenStatus200() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setName(anyString());
        itemDto.setPrice((int) 1L);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writer().writeValueAsString(itemDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/item")
                                .param("id","1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void whenDeleteItem_ThenStatus200() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/item")
                                .param("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }


    @Test
    void whenGetItemDetails_ThenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/item/details"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
