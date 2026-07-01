package com.kevin.be_laptop4you.mapper;

import com.kevin.be_laptop4you.dto.request.AccountRequest;
import com.kevin.be_laptop4you.dto.response.AccountResponse;
import com.kevin.be_laptop4you.entity.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountRequest request);

    AccountResponse toResponse(Account account);

    List<AccountResponse> toResponseList(List<Account> accounts);
}
