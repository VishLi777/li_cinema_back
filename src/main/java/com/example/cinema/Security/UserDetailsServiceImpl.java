package com.example.cinema.Security;

import com.example.cinema.Entity.UserEntity;
import com.example.cinema.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository usersRepository;


    /** Внутрений метод фильтров Security (JWTAuthenticationFilter /attemptAuthentication)
     *  для проверки существования пользователя в БД */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = usersRepository.findByEmail(email);

        // Если пользователя не существует или он не подтвердил свой телефонный номер
        if(user == null){
//        if(user == null || !user.getVerification()){
            throw new UsernameNotFoundException(email);
        }

        //Превращение листа с ролями в Collection <? extends GrantedAuthority>
        //https://www.youtube.com/watch?v=m5FAo5Oa6ag&t=3818s время 30:20
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new User(user.getEmail(), user.getPassword(), authorities);
    }



}
