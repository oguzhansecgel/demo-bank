package com.bank.bank_demo.mapper;

import com.bank.bank_demo.dto.request.accountHolder.CreateAccountHolderRequest;
import com.bank.bank_demo.dto.request.accountHolder.UpdateAccountHolderRequest;
import com.bank.bank_demo.dto.response.accountHolder.GetAllAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.GetByIdAccountHolderResponse;
import com.bank.bank_demo.entity.AccountHolder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountHolderMapper {

    AccountHolderMapper INSTANCE = Mappers.getMapper(AccountHolderMapper.class);

    AccountHolder createAccountHolder(CreateAccountHolderRequest request);
    AccountHolder updateAccountHolder(UpdateAccountHolderRequest request,@MappingTarget AccountHolder accountHolder);
    GetByIdAccountHolderResponse getByIdAccountHolder(AccountHolder accountHolder);

    GetAllAccountHolderResponse getAllAccountHolder(AccountHolder accountHolder);
    List<GetAllAccountHolderResponse> getAllAccountHoldersToList(List<AccountHolder> accountHolders);
}
