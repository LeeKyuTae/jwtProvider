package jwtprovider.demo.controller;


import jwtprovider.demo.domain.Account;
import jwtprovider.demo.domain.AccountResource;
import jwtprovider.demo.service.AccountService;
import jwtprovider.demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    JwtService jwtService;

    @CrossOrigin("*")
    @GetMapping("/get/account")
    public ResponseEntity getAccount(){
        Long memberId = jwtService.getAccountId();
        Account account = accountService.getAccount(String.valueOf(memberId));
        AccountResource accountResource = new AccountResource(account);
        return ResponseEntity.ok(accountResource);
    }


    @CrossOrigin("*")
    @GetMapping("/hello")
    public String Hello(){
       return "Hello";
    }

    @CrossOrigin("*")
    @PostMapping("/why")
    public String Why(@RequestBody String value, HttpServletResponse response){
        return value;
    }


}
