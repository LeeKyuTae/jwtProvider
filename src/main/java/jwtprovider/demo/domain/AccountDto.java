package jwtprovider.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto  {

    private String email;
    private String nickName;
    private String gender;
    private Long kakaoId;

    @JsonIgnore
    private String password;

    @ElementCollection(fetch = FetchType.EAGER) // 유저는 Admin과 일반 유저를 동시에 가질 수 있으므로 @ElementCollection을 사용함. 단 fetch에 해당하는 부분은 모르니 추가로 공부할 것.
    @Enumerated(value = EnumType.STRING)
    private List<AccountRole> roles;


}