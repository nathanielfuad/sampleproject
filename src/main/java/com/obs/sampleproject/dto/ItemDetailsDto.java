package com.obs.sampleproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDetailsDto extends ItemDto {
    private Details details;

    @Getter
    @Setter
    public static class Details {
        private Long numberOfOrders;
        private Long totalQtyOrdered;
    }
}
