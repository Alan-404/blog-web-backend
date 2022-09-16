package com.blogalanai01.server.services.account;

import java.util.List;

import com.blogalanai01.server.dtos.user.RegisterDTO;
import com.blogalanai01.server.models.Account;

public interface AccountService {
    public Account addAccount(RegisterDTO account);
    public List<Account> getAllAccounts();
    public Account login(String userId, String password);
    public Account getAccountByUserId(String userId);
    public Account getAccountById(String id);
    public boolean changePassword(Account account, String oldPassword, String newPassword);
    public boolean getRoleAccount(String accountId);
}
