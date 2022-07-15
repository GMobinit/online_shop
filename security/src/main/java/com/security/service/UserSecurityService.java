package com.security.service;

import com.onlineshop.app.clients.security.CreateUserSecurityRequest;
import com.security.model.UserSecurity;
import com.security.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class UserSecurityService implements UserDetailsService {

    private final PasswordService passwordService;
    private final UserSecurityRepository userSecurityRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserSecurity> user = userSecurityRepository.findUserSecurityByUserName(userName);
        if (user.isEmpty()){
            log.info("USer not found in the data base");
            throw new UsernameNotFoundException("user name not found in the data base");
        }
        log.info("User found in the database {}",userName);
        UserSecurity userSecurity = user.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER_ROLE"));
        return new User(userName,userSecurity.getPassword(),authorities);
    }

    public void createCloneUser(CreateUserSecurityRequest userSecurityRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = passwordService.generateSalt();
        assert salt != null;
//        String encryptedPassword = passwordService.generateEncryptedPassword(userSecurityRequest.password(),salt);
        String encryptedPassword = passwordEncoder.encode(userSecurityRequest.password());

        assert encryptedPassword != null;
        UserSecurity user = UserSecurity.builder().systemUserId(userSecurityRequest.systemUserId()).userName(userSecurityRequest.userName())
                        .encryptedPassword(encryptedPassword).salt(salt).password(userSecurityRequest.password()).build();

        userSecurityRepository.save(user);
    }
}
