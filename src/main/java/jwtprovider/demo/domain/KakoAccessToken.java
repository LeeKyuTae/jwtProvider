package jwtprovider.demo.domain;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakoAccessToken {
    String access_token;
    String token_type ;
    String refresh_token;
    String expires_in;
    String scope;
}
