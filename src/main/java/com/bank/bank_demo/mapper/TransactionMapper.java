package com.bank.bank_demo.mapper;

import com.bank.bank_demo.dto.request.transaction.TransactionDepositRequest;
import com.bank.bank_demo.dto.request.transaction.TransactionWithdrawalRequest;
import com.bank.bank_demo.dto.response.transaction.TransactionDepositResponse;
import com.bank.bank_demo.dto.response.transaction.TransactionHistoryResponse;
import com.bank.bank_demo.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(source = "accountId",target = "account.id")
    Transaction transactionDeposit(TransactionDepositRequest request);

    @Mapping(source = "accountId",target = "account.id")
    Transaction transactionWithdrawal(TransactionWithdrawalRequest request);

    @Mapping(source = "account.id",target = "accountId")
    List<TransactionHistoryResponse> transactionHistory(List<Transaction> transaction);

}
