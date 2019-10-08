package jwtprovider.demo.controller;


import jwtprovider.demo.domain.Account;
import jwtprovider.demo.domain.AccountResource;
import jwtprovider.demo.service.AccountService;
import jwtprovider.demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    JwtService jwtService;

    @GetMapping("/get/account")
    public ResponseEntity getAccount(){
        Long memberId = jwtService.getAccountId();
        Account account = accountService.getAccount(String.valueOf(memberId));
        AccountResource accountResource = new AccountResource(account);
        return ResponseEntity.ok(accountResource);
    }
}
