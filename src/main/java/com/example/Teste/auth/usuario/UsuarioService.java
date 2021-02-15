package com.example.Teste.auth.usuario;

import com.example.Teste.framework.CrudService;

public interface UsuarioService extends CrudService<Usuario, Long>{
    Usuario loadUserByUsername(String username);
}
