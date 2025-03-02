package com.bank.bank_demo.mapper;

import com.bank.bank_demo.dto.request.bank.CreateBankRequest;
import com.bank.bank_demo.dto.request.bank.UpdateBankRequest;
import com.bank.bank_demo.dto.response.bank.GetAllBankResponse;
import com.bank.bank_demo.dto.response.bank.GetByIdBankResponse;
import com.bank.bank_demo.entity.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankMapper {

    BankMapper INSTANCE = Mappers.getMapper(BankMapper.class);

    Bank createBank(CreateBankRequest request);
    Bank updateBank(UpdateBankRequest request, @MappingTarget Bank bank);
    GetByIdBankResponse getByIdBank(Bank bank);

    GetAllBankResponse getAllBank(Bank bank);
    List<GetAllBankResponse> getAllBanksToList(List<Bank> banks);

}
