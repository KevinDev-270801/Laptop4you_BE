package com.kevin.be_laptop4you.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameBrand;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<>();

}
