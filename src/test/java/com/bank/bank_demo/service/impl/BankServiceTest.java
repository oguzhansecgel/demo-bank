package com.bank.bank_demo.service.impl;

import com.bank.bank_demo.dto.request.bank.CreateBankRequest;
import com.bank.bank_demo.dto.request.bank.UpdateBankRequest;
import com.bank.bank_demo.dto.response.bank.CreateBankResponse;
import com.bank.bank_demo.dto.response.bank.GetAllBankResponse;
import com.bank.bank_demo.dto.response.bank.GetByIdBankResponse;
import com.bank.bank_demo.dto.response.bank.UpdateBankResponse;
import com.bank.bank_demo.entity.Bank;
import com.bank.bank_demo.mapper.BankMapper;
import com.bank.bank_demo.repository.BankRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private Long id;
    @BeforeEach
    void setUp()
    {
        ReflectionTestUtils.setField(bankService, "log", LoggerFactory.getLogger(BankServiceImpl.class));
        id =1L;

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
    public void testUpdateBank_bankNotFound()
    {


        when(bankRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bankService.updateBank(updateBankRequest, id));
        verify(bankRepository,times(1)).findById(id);
    }
    @Test
    public void testDeleteBank_success() {
        existingBank = new Bank();
        existingBank.setId(id);
        existingBank.setName("Garanti");
        existingBank.setCode("GB-021");

        when(bankRepository.findById(id)).thenReturn(Optional.of(existingBank));

        doNothing().when(bankRepository).delete(existingBank);

        bankService.deleteBank(id);

        verify(bankRepository).findById(id);
        verify(bankRepository).delete(existingBank);
    }
    @Test
    public void testDeleteBank_bankNotFound()
    {
        when(bankRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            bankService.deleteBank(id);
        });
        verify(bankRepository, never()).delete(any(Bank.class));

    }
    @Test
    public void testFindAllBank_success()
    {
        List<Bank> banks = new ArrayList<>();
        Bank bank1 = new Bank();
        bank1.setId(1L);
        bank1.setName("Garanti");
        bank1.setCode("GB-021");

        Bank bank2 = new Bank();
        bank2.setId(2L);
        bank2.setName("Yapı Kredi");
        bank2.setCode("GB-022");

        banks.add(bank1);
        banks.add(bank2);

        List<GetAllBankResponse> expectedResponse = new ArrayList<>();
        GetAllBankResponse response1 = new GetAllBankResponse(1L, "Garanti", "GB-021");
        GetAllBankResponse response2 = new GetAllBankResponse(2L, "Yapı Kredi", "GB-022");
        expectedResponse.add(response1);
        expectedResponse.add(response2);

        // Mocking
        when(bankRepository.findAll()).thenReturn(banks);
        when(bankMapper.getAllBanksToList(banks)).thenReturn(expectedResponse);

        List<GetAllBankResponse> result = bankService.getAllBanks();

        // Assert işlemleri
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Garanti", result.get(0).getName());
        assertEquals("Yapı Kredi", result.get(1).getName());

        // Verify işlemleri
        verify(bankRepository).findAll();
        verify(bankMapper).getAllBanksToList(banks);

    }
    @Test
    public void testFindById_success()
    {
        bank.setId(1L);
        GetByIdBankResponse expectedResponse = new GetByIdBankResponse(bank.getId(), bank.getName(), bank.getCode());

        when(bankRepository.findById(id)).thenReturn(Optional.of(bank));
        when(bankMapper.getByIdBank(bank)).thenReturn(expectedResponse);

        GetByIdBankResponse response = bankService.getByIdBank(id);

        assertEquals(bank.getId(),response.getId());
        verify(bankRepository).findById(id);
    }
    @Test
    public void testFindById_bankNotFound()
    {

        when(bankRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            bankService.getByIdBank(id);
        });

        verify(bankRepository).findById(id);
    }

}
