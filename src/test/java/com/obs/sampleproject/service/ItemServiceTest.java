package com.obs.sampleproject.service;

import com.obs.sampleproject.model.entity.Item;
import com.obs.sampleproject.model.entity.Order;
import com.obs.sampleproject.model.exception.GeneralErrorException;
import com.obs.sampleproject.model.input.ItemCreateInput;
import com.obs.sampleproject.model.input.ItemUpdateInput;
import com.obs.sampleproject.model.input.OrderCreateInput;
import com.obs.sampleproject.model.input.OrderUpdateInput;
import com.obs.sampleproject.repository.ItemRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        ItemCreateInput itemCreateInput = new ItemCreateInput();
        itemCreateInput.setPrice(1000);
        itemCreateInput.setName("test");

        Item mockItem = new Item();
        mockItem.setPrice(1000);
        mockItem.setName("test");

        when(itemRepository.save(mockItem)).thenReturn(mockItem);

        assertThat(itemService.saveItem(itemCreateInput)).isNotNull();
    }

    @Test
    void whenUpdateItem_ThenReturnUpdatedItem() {
        ItemUpdateInput itemUpdateInput = new ItemUpdateInput();
        itemUpdateInput.setId((int) 1L);
        itemUpdateInput.setName("testChange");
        itemUpdateInput.setPrice(2000);

        Item mockItem = generateMockItem((int) 1L);

        when(itemRepository.findById(1)).thenReturn(Optional.of(mockItem));
        when(itemRepository.save(any())).thenReturn(mockItem);

        assertEquals(Optional.of(mockItem), itemRepository.findById((int) 1L));

        Item expectedItem = new Item();
        expectedItem.setId((int) 1L);
        expectedItem.setPrice(2000);
        expectedItem.setName("testChange");

        assertEquals(expectedItem, itemService.updateItem(itemUpdateInput));
    }

    @Test
    void whenUpdateItem_AndItemNotFound_ThenReturnException() {
        ItemUpdateInput itemUpdateInput = new ItemUpdateInput();
        itemUpdateInput.setId((int) 1L);
        itemUpdateInput.setName("testChange");
        itemUpdateInput.setPrice(2000);

        when(itemRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(GeneralErrorException.class, () -> {
            itemService.updateItem(itemUpdateInput);
        });

    }

    @Test
    void whenUpdateItemWithoutInputName_ThenUpdateItemWithoutUpdateTheName() {
        ItemUpdateInput itemUpdateInput = new ItemUpdateInput();
        itemUpdateInput.setId((int) 1L);
        itemUpdateInput.setPrice(2000);

        Item mockItem = generateMockItem((int) 1L);

        when(itemRepository.findById(1)).thenReturn(Optional.of(mockItem));
        when(itemRepository.save(any())).thenReturn(mockItem);

        assertEquals(Optional.of(mockItem), itemRepository.findById((int) 1L));

        Item expectedItem = new Item();
        expectedItem.setId((int) 1L);
        expectedItem.setPrice(2000);
        expectedItem.setName("test");

        assertEquals(expectedItem, itemService.updateItem(itemUpdateInput));
    }

    @Test
    void whenUpdateItemWithoutInputPrice_ThenUpdateItemWithoutUpdateThePrice() {
        ItemUpdateInput itemUpdateInput = new ItemUpdateInput();
        itemUpdateInput.setId((int) 1L);
        itemUpdateInput.setName("testChange");

        Item mockItem = generateMockItem((int) 1L);

        when(itemRepository.findById(1)).thenReturn(Optional.of(mockItem));
        when(itemRepository.save(any())).thenReturn(mockItem);

        assertEquals(Optional.of(mockItem), itemRepository.findById((int) 1L));

        Item expectedItem = new Item();
        expectedItem.setId((int) 1L);
        expectedItem.setPrice(1000);
        expectedItem.setName("testChange");

        assertEquals(expectedItem, itemService.updateItem(itemUpdateInput));
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
