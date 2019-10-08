package jwtprovider.demo.repository;

import jwtprovider.demo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long > {

    Optional<Account> findAccountByKakaoId(Long kakaoId);
}
