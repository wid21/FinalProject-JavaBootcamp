package com.example.finalproject.Service;

import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService  implements UserDetailsService {


    private final MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = myUserRepository.findMyUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Wrong username or password");
        }

        return user;
    }
}

