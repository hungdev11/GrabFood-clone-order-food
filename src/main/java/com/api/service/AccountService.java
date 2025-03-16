package com.api.service;

import com.api.model.Account;

public interface AccountService {
    long addNewAccount(String username, String password);
    void checkAccount(String username, String password);
    public Account getAccountById(long id);
}
