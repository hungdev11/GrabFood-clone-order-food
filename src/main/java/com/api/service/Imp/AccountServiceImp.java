package com.api.service.Imp;

import com.api.exception.AppException;
import com.api.exception.ErrorCode;
import com.api.entity.Account;
import com.api.repository.AccountRepository;
import com.api.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImp implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public long addNewAccount(String username, String password) {
        if (this.IsUsernameExisted(username)) {
            log.error("Account already exists with username: " + username);
            throw new AppException(ErrorCode.ACCOUNT_USERNAME_DUPLICATED);
        }
        Account createdAccount = accountRepository.save(Account.builder()
                        .username(username)
                        .password(passwordEncoder.encode(password))
                .build());
        return createdAccount.getId();
    }

    @Override
    public void checkAccount(String username, String password) {
        if (!this.IsUsernameExisted(username)) {
            log.error("Username doesn't existed: " + username);
            throw new AppException(ErrorCode.ACCOUNT_USERNAME_NOT_EXISTED);
        }
        boolean isPasswordValid = passwordEncoder.matches(password, passwordEncoder.encode(password));
        if (!isPasswordValid) {
            log.error("Password is incorrect");
            throw new AppException(ErrorCode.ACCOUNT_PASSWORD_NOT_MATCH);
        }
    }

    @Override
    public Account getAccountById(long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Account doesn't existed: " + id);
                    return new AppException(ErrorCode.RESOURCE_NOT_FOUND);
                });
    }


    private boolean IsUsernameExisted(String username) {
        return accountRepository.existsByUsername(username);
    }
}
