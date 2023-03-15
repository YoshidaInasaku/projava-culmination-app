package com.yoshidainasaku.output.projavaculminationapp.entity;

import com.yoshidainasaku.output.projavaculminationapp.entity.repository.LoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserDetailsService implements UserDetailsService {
    private final LoginUserRepository loginUserRepository;

    @Autowired
    public LoginUserDetailsService(LoginUserRepository loginUserRepository) {
        this.loginUserRepository = loginUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LoginUser> userOptional = loginUserRepository.findByEmail(username);
        return userOptional.map(LoginUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found"));
    }
}
