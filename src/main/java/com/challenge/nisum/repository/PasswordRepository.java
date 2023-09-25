package com.challenge.nisum.repository;

import com.challenge.nisum.models.entities.Password;
import com.challenge.nisum.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordRepository  extends JpaRepository<Password, UUID> {
    Optional<Password> findByUsuario(Usuario usuario);
}

