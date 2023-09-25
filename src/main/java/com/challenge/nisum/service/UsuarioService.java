package com.challenge.nisum.service;

import com.challenge.nisum.controller.request.UsuarioRequest;
import com.challenge.nisum.controller.response.UsuarioResponse;
import com.challenge.nisum.exceptions.NisumException;
import com.challenge.nisum.models.entities.Usuario;

import java.util.List;

public interface UsuarioService {

	UsuarioResponse registrarUsuario(UsuarioRequest usuarioRequest) throws NisumException;

	List<Usuario> obtenerUsuarios( ) throws NisumException;

}
