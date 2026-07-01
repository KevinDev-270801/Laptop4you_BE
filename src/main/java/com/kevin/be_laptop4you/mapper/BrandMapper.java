package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.BrandRequest;
import com.kevin.be_laptop4you.dto.response.BrandResponse;
import com.kevin.be_laptop4you.entity.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toEntity(BrandRequest request);

    BrandResponse toResponse(Brand brand);

    List<BrandResponse> toResponseList(List<Brand> brands);
}
