package com.obs.sampleproject.service;

import com.obs.sampleproject.dto.ItemDto;
import com.obs.sampleproject.entity.Item;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import com.obs.sampleproject.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private OrderService orderService;
    private ItemService itemService;

    @BeforeEach
    void initTestCase() {
        itemService = new ItemService(itemRepository);
    }

    @Test
    void whenGetAllItem_ThenReturnAlLItem(){
        Item item = generateMockItem((int) 1L);
        List<Item> itemList = Collections.singletonList(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        assertEquals(itemList,itemService.getAllItem());
    }

    @Test
    void whenGetItem_ThenReturnItem(){
        Item item = generateMockItem((int) 1L);
        when(itemRepository.findById((int) 1L)).thenReturn(Optional.of(item));

        assertEquals(item, itemService.getItem((int) 1L));
    }

    @Test
    void whenSaveItem_ThenReturnSavedItem() {
        com.obs.sampleproject.dto.ItemDto itemDto = new com.obs.sampleproject.dto.ItemDto();
        itemDto.setPrice(1000);
        itemDto.setName("test");

        Item mockItem = new Item();
        mockItem.setPrice(1000);
        mockItem.setName("test");

        when(itemRepository.save(mockItem)).thenReturn(mockItem);

        assertThat(itemService.saveItem(itemDto)).isNotNull();
    }

    @Test
    void whenUpdateItem_ThenReturnUpdatedItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("testChange");
        itemDto.setPrice(2000);

        Item mockItem = generateMockItem((int) 1L);

        when(itemRepository.findById(1)).thenReturn(Optional.of(mockItem));
        when(itemRepository.save(any())).thenReturn(mockItem);

        assertEquals(Optional.of(mockItem), itemRepository.findById((int) 1L));

        Item expectedItem = new Item();
        expectedItem.setId((int) 1L);
        expectedItem.setPrice(2000);
        expectedItem.setName("testChange");

        assertEquals(expectedItem, itemService.updateItem((int) 1L, itemDto));
    }

    @Test
    void whenUpdateItem_AndItemNotFound_ThenReturnException() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("testChange");
        itemDto.setPrice(2000);

        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(GeneralErrorException.class, () -> {
            itemService.updateItem((int) 1L, itemDto);
        });

    }

    @Test
    void whenUpdateItemWithoutInputName_ThenUpdateItemWithoutUpdateTheName() {
        ItemDto itemDto = new ItemDto();
        itemDto.setPrice(2000);

        Item mockItem = generateMockItem((int) 1L);

        when(itemRepository.findById(1)).thenReturn(Optional.of(mockItem));
        when(itemRepository.save(any())).thenReturn(mockItem);

        assertEquals(Optional.of(mockItem), itemRepository.findById((int) 1L));

        Item expectedItem = new Item();
        expectedItem.setId((int) 1L);
        expectedItem.setPrice(2000);
        expectedItem.setName("test");

        assertEquals(expectedItem, itemService.updateItem((int) 1L, itemDto));
    }

    @Test
    void whenUpdateItemWithoutInputPrice_ThenUpdateItemWithoutUpdateThePrice() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("testChange");

        Item mockItem = generateMockItem((int) 1L);

        when(itemRepository.findById(1)).thenReturn(Optional.of(mockItem));
        when(itemRepository.save(any())).thenReturn(mockItem);

        assertEquals(Optional.of(mockItem), itemRepository.findById((int) 1L));

        Item expectedItem = new Item();
        expectedItem.setId((int) 1L);
        expectedItem.setPrice(1000);
        expectedItem.setName("testChange");

        assertEquals(expectedItem, itemService.updateItem((int) 1L, itemDto));
    }

    @Test
    void whenDeleteItem_ThenReturnNormal() {
        Item mockItem = generateMockItem(1);
        when(itemRepository.findById(1)).thenReturn(Optional.of(mockItem));

        assertThat(itemService.deleteItem(1)).isNotNull();
    }

    @Test
    void whenDeleteItem_AndItemNotFound_ThenReturnException() {
        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(GeneralErrorException.class, () -> {
            itemService.deleteItem(1);
        });
    }

    Item generateMockItem(int id){
        Item item = new Item();
        item.setId(id);
        item.setName("test");
        item.setPrice(1000);
        return item;
    }
}
