package jwtprovider.demo.service;


import jwtprovider.demo.domain.Account;
import jwtprovider.demo.domain.AccountDto;
import jwtprovider.demo.error.UnauthorizedException;
import jwtprovider.demo.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public Boolean IsExistAccountFromKakao(String kakaoId){
        Long userId = Long.parseLong(kakaoId);
        Optional<Account> isexist = accountRepository.findAccountByKakaoId(userId);
        if(isexist.isPresent())
            return true;
        else
            return false;
    }

    @Transactional
    public Account createAccount(AccountDto accountDto){
        Account account = Account.builder().kakaoId(accountDto.getKakaoId())
                .email(accountDto.getEmail())
                .nickName(accountDto.getNickName())
                .gender(accountDto.getGender())
                .roles(accountDto.getRoles()).build();
        System.out.println(account.getKakaoId());
        return accountRepository.save(account);
    }

    public Account getAccount(String kakaoId){
        Long userId = Long.parseLong(kakaoId);
        Account account = accountRepository.findAccountByKakaoId(userId).orElseThrow(() -> new UnauthorizedException());
        return account;
    }



}
