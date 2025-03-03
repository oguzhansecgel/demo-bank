package com.bank.bank_demo.service.impl;

import com.bank.bank_demo.dto.request.accountHolder.CreateAccountHolderRequest;
import com.bank.bank_demo.dto.request.accountHolder.UpdateAccountHolderRequest;
import com.bank.bank_demo.dto.response.accountHolder.CreateAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.GetAllAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.GetByIdAccountHolderResponse;
import com.bank.bank_demo.dto.response.accountHolder.UpdateAccountHolderResponse;
import com.bank.bank_demo.entity.Account;
import com.bank.bank_demo.entity.AccountHolder;
import com.bank.bank_demo.entity.Bank;
import com.bank.bank_demo.mapper.AccountHolderMapper;
import com.bank.bank_demo.repository.AccountHolderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountHolderServiceImplTest {

    @InjectMocks
    private AccountHolderServiceImpl accountHolderService;

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private AccountHolderMapper accountHolderMapper;


    @BeforeEach
    void setUp()
    {
        ReflectionTestUtils.setField(accountHolderService, "log", LoggerFactory.getLogger(AccountHolderServiceImpl.class));
    }

    @Test
    public void testCreateAccountHolder_success()
    {
        // arrange veri hazırlığı
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setHolderName("Oğuzhan");
        accountHolder.setHolderSurname("Seçgel");
        accountHolder.setIdentityNumber("12345678909");

        AccountHolder savedAccountHolder = new AccountHolder();
        savedAccountHolder.setId(1L);
        savedAccountHolder.setHolderName("Oğuzhan");
        savedAccountHolder.setHolderSurname("Oğuzhan");
        savedAccountHolder.setIdentityNumber("12345678909");

        CreateAccountHolderRequest request = new CreateAccountHolderRequest();
        request.setHolderName("Oğuzhan");
        request.setHolderSurname("Seçgel");
        request.setIdentityNumber("12345678909");


        when(accountHolderMapper.createAccountHolder(any(CreateAccountHolderRequest.class))).thenReturn(accountHolder);
        when(accountHolderRepository.save(any(AccountHolder.class))).thenReturn(savedAccountHolder);

        CreateAccountHolderResponse response = accountHolderService.createAccountHolder(request);
        assertNotNull(response);
        assertEquals(savedAccountHolder.getId(),response.getId());
        assertEquals(savedAccountHolder.getHolderSurname(), response.getHolderName());
        assertEquals(savedAccountHolder.getHolderSurname(), response.getHolderSurname());
        assertEquals(savedAccountHolder.getIdentityNumber(), response.getIdentityNumber());


        verify(accountHolderMapper).createAccountHolder(any(CreateAccountHolderRequest.class));
        verify(accountHolderRepository).save(any(AccountHolder.class));

    }
    @Test
    @Transactional
    public void testCreateAccountHolder_notUniqueIdentityNumber()
    {
        CreateAccountHolderRequest request1 = new CreateAccountHolderRequest();
        request1.setHolderName("Oğuzhan");
        request1.setHolderSurname("Seçgel");
        request1.setIdentityNumber("12345678909");

        CreateAccountHolderRequest request2 = new CreateAccountHolderRequest();
        request2.setHolderName("Oğuzhan");
        request2.setHolderSurname("Seçgel");
        request2.setIdentityNumber("12345678909");

        AccountHolder accountHolder1 = accountHolderMapper.createAccountHolder(request1);
        AccountHolder accountHolder2 = accountHolderMapper.createAccountHolder(request2);

        when(accountHolderRepository.save(accountHolder1)).thenReturn(accountHolder1);
        when(accountHolderRepository.save(accountHolder2))
                .thenThrow(new DataIntegrityViolationException("Unique constraint violation"));

        accountHolderService.createAccountHolder(request1);

        assertEquals(request1.getHolderName(), accountHolder1.getHolderName());
        assertEquals(request1.getHolderSurname(), accountHolder1.getHolderSurname());
        assertThrows(DataIntegrityViolationException.class, () -> {
            accountHolderService.createAccountHolder(request2);
        });

        verify(accountHolderRepository).save(accountHolder1);
        verify(accountHolderRepository).save(accountHolder2);

    }
    @Test
    public void testUpdateAccountHolder_success()
    {
        long id = 1L;
        UpdateAccountHolderRequest request1 = new UpdateAccountHolderRequest();
        request1.setHolderName("B");
        request1.setHolderSurname("B");
        request1.setIdentityNumber("12345678909");

        AccountHolder existingAccountHolder = new AccountHolder();
        existingAccountHolder.setId(1L);
        existingAccountHolder.setHolderName("A");
        existingAccountHolder.setHolderSurname("A");
        existingAccountHolder.setIdentityNumber("12345678909");

        AccountHolder savedAccountHolder = new AccountHolder();
        savedAccountHolder.setId(1L);
        savedAccountHolder.setHolderName("B");
        savedAccountHolder.setHolderSurname("B");
        existingAccountHolder.setIdentityNumber("12345678909");

        when(accountHolderRepository.findById(id)).thenReturn(Optional.of(existingAccountHolder));
        doReturn(savedAccountHolder).when(accountHolderRepository).save(any(AccountHolder.class));
        UpdateAccountHolderResponse response = accountHolderService.updateAccountHolder(request1,1L);
        response.setHolderName("B");
        response.setHolderSurname("B");
        response.setIdentityNumber("12345678909");



    }
    @Test
    public void testUpdateAccountHolder_AccountHolderNotFound()
    {
        long id = 1L;
        UpdateAccountHolderRequest request1 = new UpdateAccountHolderRequest();
        request1.setHolderName("B");
        request1.setHolderSurname("B");
        request1.setIdentityNumber("12345678909");
        when(accountHolderRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->accountHolderService.updateAccountHolder(request1,id));
        verify(accountHolderRepository,times(1)).findById(id);
    }
    @Test
    public void testDeleteAccountHolder_success() {
        long id = 1L;
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(id);
        accountHolder.setHolderName("A");
        accountHolder.setHolderSurname("A");
        accountHolder.setIdentityNumber("12345678909");

        when(accountHolderRepository.findById(id)).thenReturn(Optional.of(accountHolder));
        accountHolderService.deleteAccountHolder(id);

        verify(accountHolderRepository).deleteById(id);
    }
    @Test
    public void testDeleteAccountHolder_accessHolderNotFound() {

        long id = 1L;
        when(accountHolderRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> {
            accountHolderService.deleteAccountHolder(id);
        });

        verify(accountHolderRepository,never()).deleteById(id);
    }
    @Test
    public void testGetByIdAccountHolder_success()
    {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(1L);
        accountHolder.setHolderName("A");
        accountHolder.setHolderSurname("A");
        accountHolder.setIdentityNumber("12345678909");
        GetByIdAccountHolderResponse response = new GetByIdAccountHolderResponse(accountHolder.getId(),accountHolder.getHolderName(),accountHolder.getHolderSurname(), accountHolder.getIdentityNumber());
        when(accountHolderRepository.findById(1L)).thenReturn(Optional.of(accountHolder));
        when(accountHolderMapper.getByIdAccountHolder(accountHolder)).thenReturn(response);

        GetByIdAccountHolderResponse response1 = accountHolderService.getByIdAccountHolder(1L);
        assertEquals(accountHolder.getId(),response1.getId());
        verify(accountHolderRepository,times(1)).findById(accountHolder.getId());
    }
    @Test
    public void testGetByIdAccountHolder_accountHolderNotFound()
    {
        long id = 1L;
        when(accountHolderRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->accountHolderService.getByIdAccountHolder(id));
        verify(accountHolderRepository,times(1)).findById(id);


    }
    @Test
    public void testGetAllAccountHolders_success() {
        List<AccountHolder> accountHolders = Arrays.asList(
                new AccountHolder(1L, "Oğuzhan", "Seçgel", "12345678909"),
                new AccountHolder(2L ,"John", "Doe", "98765432100")
        );

        when(accountHolderRepository.findAll()).thenReturn(accountHolders);
        when(accountHolderMapper.getAllAccountHoldersToList(accountHolders))
                .thenReturn(Arrays.asList(
                        new GetAllAccountHolderResponse(1L, "Oğuzhan", "Seçgel", "12345678909"),
                        new GetAllAccountHolderResponse(2L, "John", "Doe", "98765432100")
                ));

        List<GetAllAccountHolderResponse> result = accountHolderService.getAllAccountHolders();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Oğuzhan", result.get(0).getHolderName());
        assertEquals("Doe", result.get(1).getHolderSurname());

        verify(accountHolderRepository).findAll();
        verify(accountHolderMapper).getAllAccountHoldersToList(accountHolders);
    }
}