package com.bank.bank_demo.service;

import com.bank.bank_demo.dto.request.bank.CreateBankRequest;
import com.bank.bank_demo.dto.request.bank.UpdateBankRequest;
import com.bank.bank_demo.dto.response.bank.CreateBankResponse;
import com.bank.bank_demo.dto.response.bank.UpdateBankResponse;
import com.bank.bank_demo.entity.Bank;
import com.bank.bank_demo.mapper.BankMapper;
import com.bank.bank_demo.repository.BankRepository;
import com.bank.bank_demo.service.impl.BankServiceImpl;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @InjectMocks
    private BankServiceImpl bankService;

    @Mock
    private BankRepository bankRepository;

    @Mock
    private BankMapper bankMapper;

    private CreateBankRequest createBankRequest;
    private Bank bank;
    private Bank savedBank;
    private UpdateBankRequest updateBankRequest;
    private Bank existingBank;
    @BeforeEach
    void setUp()
    {
        ReflectionTestUtils.setField(bankService, "log", LoggerFactory.getLogger(BankServiceImpl.class));


        createBankRequest = new CreateBankRequest();
        createBankRequest.setName("Garanti Bankası");
        createBankRequest.setCode("GB-011");



        bank = new Bank();
        bank.setName("Garanti Bankası");
        bank.setCode("GB-011");

        savedBank = new Bank();
        savedBank.setId(1L);
        savedBank.setName("Garanti Bankası");
        savedBank.setCode("GB-011");
    }

    @Test
    public void testCreateBank_success()
    {
        // mock
        when(bankMapper.createBank(any(CreateBankRequest.class))).thenReturn(bank);
        when(bankRepository.save(any(Bank.class))).thenReturn(savedBank);


        // act gerçek işlem
        CreateBankResponse response =  bankService.createBank(createBankRequest);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Garanti Bankası", response.getName());
        assertEquals("GB-011", response.getCode());
        // verify doğrulama
        verify(bankMapper,times(1)).createBank(createBankRequest);
        verify(bankRepository,times(1)).save(bank);

    }
    @Test
    public void testUpdateBank_success() {
        updateBankRequest = new UpdateBankRequest();
        updateBankRequest.setName("Garanti Bankası");
        updateBankRequest.setCode("GB-022");

        existingBank = new Bank();
        existingBank.setId(1L);
        existingBank.setCode("GB-021");
        existingBank.setName("Garanti");

        Bank updatedBank = new Bank();
        updatedBank.setId(1L);
        updatedBank.setCode("GB-022");
        updatedBank.setName("Garanti Bankası");

        long id = 1L;

        when(bankRepository.findById(id)).thenReturn(Optional.of(existingBank));

        when(bankMapper.updateBank(any(UpdateBankRequest.class), any(Bank.class))).thenReturn(updatedBank);

        doReturn(updatedBank).when(bankRepository).save(any(Bank.class));

        UpdateBankResponse response = bankService.updateBank(updateBankRequest, id);

        assertNotNull(response);
        assertEquals(updatedBank.getId(), response.getId());
        assertEquals(updateBankRequest.getName(), response.getName());
        assertEquals(updateBankRequest.getCode(), response.getCode());

        verify(bankRepository).findById(id);
        verify(bankMapper).updateBank(updateBankRequest, existingBank);
        verify(bankRepository).save(updatedBank);
    }
    @Test
    public void testDeleteBank_success()
    {
        long id = 1L;

        when(bankRepository.findById(id)).thenReturn(Optional.of(existingBank));
        doNothing().when(bankRepository).delete(existingBank);
        
    }
}
