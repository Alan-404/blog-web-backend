package com.blogalanai01.server.services.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.blogalanai01.server.dtos.user.RegisterDTO;
import com.blogalanai01.server.models.Account;
import com.blogalanai01.server.repositories.AccountRepository;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.modelmapper.ModelMapper;


@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.modelMapper = new ModelMapper();
    }


    @Override
    public Account addAccount(RegisterDTO account){
        Account newAccount = this.modelMapper.map(account, Account.class);
        String hashedPassword = this.passwordEncoder.encode(newAccount.getPassword());
        newAccount.setPassword(hashedPassword);
        this.accountRepository.save(newAccount);
        return newAccount;
    }


    @Override
    public List<Account> getAllAccounts(){
        List<Account> lstAccounts = new ArrayList<Account>();
        lstAccounts = this.accountRepository.findAll();

        return lstAccounts;
    }

    @Override
    public Account getAccountByUserId(String userId){
        return this.accountRepository.getAccountByUserId(userId);
        
    }

    @Override
    public Account login(String userId, String password){

        Account account = getAccountByUserId(userId);

        if (userId == null){
            return null;
        }
        if (this.passwordEncoder.matches(password, account.getPassword()) == false){
            return null;
        }
        return account;
    }

    @Override
    public Account getAccountById(String id){
        return this.accountRepository.getAccountById(id);
    }


    @Override
    public boolean changePassword(Account account, String oldPassword, String newPassword){
        if (this.passwordEncoder.matches(oldPassword, account.getPassword()) == false){
            return false;
        }
        String hashPassword = this.passwordEncoder.encode(newPassword);
        account.setPassword(hashPassword);
        this.accountRepository.save(account);

        return true;
    }

}
