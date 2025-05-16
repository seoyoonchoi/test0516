package com.example.test_back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false) // DB 컬럼명 설정, updatable = false로 수정 불가 설정
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    // Set 컬렉션 프레임워크: 저장 순서 X, 중복 저장 X

    @Builder
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ------------------------------------------------------------------ //
    // UserDetails 인터페이스 메서드 구현

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return Set.of(new SimpleGrantedAuthority("ROLE_" + this.role));
}


    @Override
    public String getPassword() {
        // 사용자 비밀번호를 반환하는 메서드
        return password;
    }

    @Override
    public String getUsername() {
        // 사용자의 고유 식별자를 반환하는 메서드
        // : 일반적으로 username을 반환 (해당 코드는 email 을 사용)
        // - Spring Security가 로그인 처리 시 이 값을 통해 사용자 조회
        return username;
    }

    // 계정 만료 여부(true: 만료되지 않음)
    @Override public boolean isAccountNonExpired() {
        return true;
    }
    // 계정 잠김 여부(true: 잠기지 않음)
    @Override public boolean isAccountNonLocked() {
        return true;
    }
    // 인증 정보 만료 여부
    @Override public boolean isCredentialsNonExpired() {
        return true;
    }
    // 계정 활성화 여부
    @Override public boolean isEnabled() {
        return true;
    }

}
