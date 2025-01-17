package com.cubas.examen01.gestion.service;

import com.cubas.examen01.gestion.entity.Usuario;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsuarioService {
    public List<Usuario> findAll(Pageable page);
    public List<Usuario> findAll();
    public Usuario findByEmail(String email);
    public Usuario findById(int id);
    public Usuario save(Usuario usuario);
    public Usuario deactivate(int id);
    public Usuario activate(int id);
}
