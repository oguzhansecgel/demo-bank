package com.bank.bank_demo.mapper;

import com.bank.bank_demo.dto.request.account.CreateAccountRequest;
import com.bank.bank_demo.dto.response.account.GetAllAccountResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountBalanceResponse;
import com.bank.bank_demo.dto.response.account.GetByAccountWithCustomerId;
import com.bank.bank_demo.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(source = "bankId",target = "bank.id")
    @Mapping(source = "accountHolderId",target = "accountHolder.id")
    Account createAccount(CreateAccountRequest createAccountRequest);

    @Mapping(source = "balance",target = "balance")
    GetByAccountBalanceResponse getByAccountBalance(Account account);

    @Mapping(source = "bank.id",target = "bankId")
    @Mapping(source = "accountHolder.id",target = "accountHolderId")
    @Mapping(source = "id",target = "id")
    GetByAccountWithCustomerId getByAccountWithCustomerId(Account account);
    List<GetByAccountWithCustomerId> getByAccountWithCustomerIdToList(List<Account> account);

    @Mapping(source = "bank.id",target = "bankId")
    @Mapping(source = "accountHolder.id",target = "accountHolderId")
    @Mapping(source = "id",target = "id")
    GetAllAccountResponse getAllAccountResponse(Account account);
    List<GetAllAccountResponse> getAllAccountToListResponse(List<Account> accounts);
}
