package com.kevin.be_laptop4you.utils;

import com.kevin.be_laptop4you.entity.Product;
import com.kevin.be_laptop4you.enums.ProductStatus;

public class SyncProductStatus {


    public static void syncProductStatusWithQuantity(Product product) {
        if (product.getProductStatus() == ProductStatus.DISABLED) {
            return;
        }
        if (product.getQuantity() == null || product.getQuantity() <= 0) {
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        } else {
            product.setProductStatus(ProductStatus.ACTIVE);
        }
    }
}
