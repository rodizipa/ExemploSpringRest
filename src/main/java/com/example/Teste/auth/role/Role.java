package com.example.Teste.auth.role;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private EnumRole nome;

    public Role(EnumRole nome){
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return this.nome.name();
    }
}
