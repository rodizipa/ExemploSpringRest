package com.example.Teste.auth;

import com.example.Teste.auth.token.JwtTokenUtil;
import com.example.Teste.auth.usuario.Usuario;
import com.example.Teste.auth.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/registrar")
    public void registrar(@RequestBody Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuarioService.save(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) throws Exception {

        Authentication auth = authenticate(usuario.getUsername(), usuario.getPassword());
        final String token = jwtTokenUtil.generateJwtToken(auth);
        return ResponseEntity.ok(token);
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
           return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (DisabledException e) {
            throw new Exception("Usuario Desativado.", e);
        } catch (BadCredentialsException e){
            throw new Exception("Usuário Inválido.", e);
        }
    }
}
