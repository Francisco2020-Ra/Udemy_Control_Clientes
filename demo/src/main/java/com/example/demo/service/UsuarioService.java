package com.example.demo.service;

import com.example.demo.dao.UsuarioDAO;
import com.example.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;

@Service("userDetailsService")
public class UsuarioService  implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByUsername(username);

        if(usuario == null){
            throw new UsernameNotFoundException(username);
        }

        var roles = new ArrayList<GrantedAuthority>();

        for(Rol rol: usuario.getRoles()){
                roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }


        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
