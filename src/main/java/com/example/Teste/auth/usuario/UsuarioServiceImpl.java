package com.example.Teste.auth.usuario;

import com.example.Teste.framework.CrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends CrudServiceImpl<Usuario, Long>
implements UsuarioService, UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public JpaRepository<Usuario, Long> getRepository() {
        return usuarioRepository;
    }

    @Override
    public Usuario loadUserByUsername(String username) {
        Usuario user = usuarioRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        return user;
    }
}
