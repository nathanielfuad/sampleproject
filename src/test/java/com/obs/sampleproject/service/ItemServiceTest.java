package com.obs.sampleproject.service;

import com.obs.sampleproject.dto.ItemDto;
import com.obs.sampleproject.entity.Item;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;
    private ItemService itemService;
    @Mock
    OrderRepository orderRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void initTestCase() {
        modelMapper = new ModelMapper();
        itemService = new ItemService(itemRepository, orderRepository, modelMapper);
    }

    @Test
    void whenGetAllItem_ThenReturnAlLItem(){
        Item item = generateMockItem((int) 1L);
        List<Item> itemList = Collections.singletonList(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        List<ItemDto> itemDtoList = itemService.getAllItem();
        assertThat(itemDtoList).isNotNull();
        assertThat(itemDtoList.size()).isEqualTo(itemList.size());
        assertThat(itemDtoList.get(0).getId()).isEqualTo(item.getId());
    }

    @Test
    void whenGetItem_ThenReturnItem(){
        Item item = generateMockItem((int) 1L);
        when(itemRepository.findById((int) 1L)).thenReturn(Optional.of(item));

        assertThat(itemService.getItem((int) 1L)).isNotNull();
    }

    @Test
    void whenSaveItem_ThenReturnSavedItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setPrice(1000);
        itemDto.setName("test");

        Item mockItem = new Item();
        mockItem.setId((int) 1L);
        mockItem.setPrice(1000);
        mockItem.setName("test");

        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        ItemDto itemDtoResult = itemService.saveItem(itemDto);
        assertThat(itemDtoResult.getId()).isEqualTo((int) 1L);
        assertThat(itemDtoResult).isNotNull();
    }

    @Test
    void whenUpdateItem_ThenReturnUpdatedItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("testChange");
        itemDto.setPrice(2000);

        Item mockItem = generateMockItem((int) 1L);
        when(itemRepository.findById((int) 1L)).thenReturn(Optional.of(mockItem));
        assertEquals(Optional.of(mockItem), itemRepository.findById((int) 1L));

        Item updatedItem = new Item();
        updatedItem.setId(mockItem.getId());
        updatedItem.setPrice(itemDto.getPrice());
        updatedItem.setName(itemDto.getName());
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        ItemDto expectedItemDto = new ItemDto();
        expectedItemDto.setId(updatedItem.getId());
        expectedItemDto.setPrice(itemDto.getPrice());
        expectedItemDto.setName(itemDto.getName());

        ItemDto itemDtoResult = itemService.updateItem((int) 1L, itemDto);
        assertThat(itemDtoResult).isNotNull();
        assertThat(itemDtoResult.getId()).isEqualTo(updatedItem.getId());
        assertThat(itemDtoResult.getPrice()).isEqualTo(updatedItem.getPrice());
        assertThat(itemDtoResult.getName()).isEqualTo(updatedItem.getName());
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
        when(itemRepository.findById((int) 1L)).thenReturn(Optional.of(mockItem));
        assertEquals(Optional.of(mockItem), itemRepository.findById((int) 1L));

        Item updatedItem = new Item();
        updatedItem.setId(mockItem.getId());
        updatedItem.setPrice(itemDto.getPrice());
        updatedItem.setName(mockItem.getName());
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        ItemDto itemDtoResult = itemService.updateItem((int) 1L, itemDto);

        assertThat(itemDtoResult).isNotNull();
        assertThat(itemDtoResult.getId()).isEqualTo(updatedItem.getId());
        assertThat(itemDtoResult.getPrice()).isEqualTo(updatedItem.getPrice());
        assertThat(itemDtoResult.getName()).isEqualTo(updatedItem.getName());
    }

    @Test
    void whenUpdateItemWithoutInputPrice_ThenUpdateItemWithoutUpdateThePrice() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("testChange");

        Item mockItem = generateMockItem((int) 1L);
        when(itemRepository.findById((int) 1L)).thenReturn(Optional.of(mockItem));
        assertEquals(Optional.of(mockItem), itemRepository.findById((int) 1L));

        Item updatedItem = new Item();
        updatedItem.setId(mockItem.getId());
        updatedItem.setPrice(mockItem.getPrice());
        updatedItem.setName(itemDto.getName());
        when(itemRepository.save(any(Item.class))).thenReturn(updatedItem);

        ItemDto itemDtoResult = itemService.updateItem((int) 1L, itemDto);

        assertThat(itemDtoResult).isNotNull();
        assertThat(itemDtoResult.getId()).isEqualTo(updatedItem.getId());
        assertThat(itemDtoResult.getPrice()).isEqualTo(updatedItem.getPrice());
        assertThat(itemDtoResult.getName()).isEqualTo(updatedItem.getName());
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
