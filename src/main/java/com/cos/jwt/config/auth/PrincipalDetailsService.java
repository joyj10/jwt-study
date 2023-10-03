package com.cos.jwt.config.auth;

import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * PrincipalDetailsService
 * <pre>
 * 원래 /login 요청이 오는 경우 동작 해야하지만 formLogin disable 처리하여 동작하지 않음
 * 직접 필터 추가 필요
 * </pre>
 *
 * @version 1.0,
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("### username = {} ", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow();
        return new PrincipalDetails(user);
    }
}
