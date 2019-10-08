package jwtprovider.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String email;
    private String nickName;
    private String gender;

    private Long kakaoId;

    @ElementCollection(fetch = FetchType.EAGER) // 유저는 Admin과 일반 유저를 동시에 가질 수 있으므로 @ElementCollection을 사용함. 단 fetch에 해당하는 부분은 모르니 추가로 공부할 것.
    @Enumerated(value = EnumType.STRING)
    private List<AccountRole> roles;
}
