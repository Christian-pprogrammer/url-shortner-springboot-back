package com.irembo.test.security;

import com.irembo.test.exceptions.UnAuthorizedException;
import com.irembo.test.persistence.IUserRepository;
import com.irembo.test.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final IUserRepository iUserRepository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUsername(username).orElseThrow(()-> new UnAuthorizedException("Invalid credentials"));
        return  user ;

    }


}
