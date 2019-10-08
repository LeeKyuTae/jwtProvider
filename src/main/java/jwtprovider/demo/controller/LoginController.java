package jwtprovider.demo.controller;


import com.fasterxml.jackson.databind.JsonNode;
import jwtprovider.demo.domain.Account;
import jwtprovider.demo.domain.AccountDto;
import jwtprovider.demo.domain.AccountResource;
import jwtprovider.demo.domain.KakoAccessToken;
import jwtprovider.demo.service.AccountService;
import jwtprovider.demo.service.JwtService;
import jwtprovider.demo.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
//@RequestMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class LoginController {

  //  @Value("${app.jwtHeader}")
    private static final String  HEADER_AUTH = "Authorization" ;

    @Autowired
    JwtService jwtService;

    @Autowired
    AccountService accountService;

    @Autowired
    KakaoService kakaoService;

    @CrossOrigin("*")
    @PostMapping("/test/kakao")
    public ResponseEntity testKakao(@RequestBody KakoAccessToken kakaoAccess_token, HttpServletResponse response ){
        JsonNode userInfo = kakaoService.getUserInfo(kakaoAccess_token.getAccess_token());
        String kakaoId = userInfo.path("id").asText();
        System.out.println("kakaoId: " + kakaoId);
        return ResponseEntity.ok(kakaoAccess_token);
    }


    @CrossOrigin("*")
    @PostMapping("/oauth/login/kakao")
    public ResponseEntity kakaoLogin(@RequestBody KakoAccessToken kakaoAccess_token /*, HttpServletResponse response*/ ) {
       // String access_token = kakaoService.getAccessToken(code);
        JsonNode userInfo = kakaoService.getUserInfo(kakaoAccess_token.getAccess_token());
        String kakaoId = userInfo.path("id").asText();
        System.out.println("kakaoId: " + kakaoId);
        //String email = userInfo.get("email").toString();
        // 존재하지 않는 아이디일 경우 DB 저장
        if (accountService.IsExistAccountFromKakao(kakaoId) == false) {
            // 데이터 최대한 넣은 후 create
            JsonNode properties = userInfo.path("properties");
            JsonNode kakao_account = userInfo.path("kakao_account");

            String nickName = properties.path("nickname").asText();
            String email = kakao_account.path("email").asText();
            String gender = kakao_account.path("gender").asText();

            System.out.println("nickName: " + nickName);
            System.out.println("email: " + email);
            System.out.println("gender: " + gender);

            AccountDto accountDto = AccountDto.builder().email(email)
                    .gender(gender).kakaoId(Long.parseLong(kakaoId)).nickName(nickName).build();

            System.out.println("SUCCESS MAKE ACCOUNTDTO");
            accountService.createAccount(accountDto);
            System.out.println("SUCCESS CREATE ACCOUNT");
        }
        //JWT Toeken 발급
        String jwtToken = jwtService.generateToken(kakaoId);
        System.out.println("JWT: " + jwtToken);
     //   response.addHeader(HEADER_AUTH, jwtToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HEADER_AUTH, jwtToken);

       // kakaoAccess_token.setJwt(jwtToken);
        Account account = accountService.getAccount(kakaoId);
        AccountResource accountResource = new AccountResource(account);
        return ResponseEntity.ok().headers(responseHeaders).body(accountResource);
    }




    /*
    @GetMapping("/oauth/login/kakao")
    public ResponseEntity kakaoLogin(@RequestParam("code") String code, HttpServletResponse response ) {
        String access_token = kakaoService.getAccessToken(code);
        JsonNode userInfo = kakaoService.getUserInfo(access_token);

        String kakaoId = userInfo.path("id").asText();
        System.out.println("kakaoId: " + kakaoId);
        //String email = userInfo.get("email").toString();
        // 존재하지 않는 아이디일 경우 DB 저장
        if (accountService.IsExistAccountFromKakao(kakaoId) == false) {
            // 데이터 최대한 넣은 후 create
            JsonNode properties = userInfo.path("properties");
            JsonNode kakao_account = userInfo.path("kakao_account");

            String nickName = properties.path("nickname").asText();
            String email = kakao_account.path("email").asText();
            String gender = kakao_account.path("gender").asText();

            System.out.println("nickName: " + nickName);
            System.out.println("email: " + email);
            System.out.println("gender: " + gender);

            AccountDto accountDto = AccountDto.builder().email(email)
                    .gender(gender).kakaoId(Long.parseLong(kakaoId)).nickName(nickName).build();

            System.out.println("SUCCESS MAKE ACCOUNTDTO");
            accountService.createAccount(accountDto);
            System.out.println("SUCCESS CREATE ACCOUNT");
        }
        //JWT Toeken 발급
        String jwtToken = jwtService.generateToken(kakaoId);
        System.out.println("JWT: " + jwtToken);
        response.setHeader(HEADER_AUTH, jwtToken);
        Account account = accountService.getAccount(kakaoId);
        AccountResource accountResource = new AccountResource(account);
        return ResponseEntity.ok(accountResource);
    }
     */


}
