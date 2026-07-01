package com.kevin.be_laptop4you.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255)
    private String name;

    @Positive(message = "RAM phải lớn hơn 0")
    private Integer ram;

    @Positive(message = "Storage phải lớn hơn 0")
    private Integer storage;

    @NotBlank(message = "CPU không được để trống")
    private String cpu;

    @NotBlank(message = "Card đồ họa không được để trống")
    private String graphicsCard;

    @Positive(message = "Kích thước màn hình phải lớn hơn 0")
    private Double screenSize;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @NotNull(message = "Số lượng không được để trống")
    @PositiveOrZero(message = "Số lượng không được âm")
    private Integer quantity;

    @NotNull
    private Boolean active;

    @NotNull(message = "Brand không được để trống")
    private Long brandId;

    private Long promotionId;
}
