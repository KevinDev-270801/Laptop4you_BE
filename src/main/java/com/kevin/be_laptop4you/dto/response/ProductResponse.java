package com.kevin.be_laptop4you.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductResponse {

    private Long id;

    private String name;

    private Integer ram;

    private Integer storage;

    private String cpu;

    private String graphicsCard;

    private Double screenSize;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private Boolean active;

    private Long brandId;

    private String brandName;

    private Long promotionId;

    private String promotionName;

    private List<String> images;
}
