package com.challenge.nisum.controller;

import com.challenge.nisum.controller.response.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.challenge.nisum.controller.request.UsuarioRequest;
import com.challenge.nisum.exceptions.NisumException;
import com.challenge.nisum.models.entities.Usuario;
import com.challenge.nisum.service.UsuarioService;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) throws NisumException {
		UsuarioResponse nuevoUsuario = usuarioService.registrarUsuario(usuarioRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
	}


	@GetMapping("/usuarios")
	public ResponseEntity<?> registrarUsuario() throws NisumException {
		List<Usuario> nuevoUsuario = usuarioService.obtenerUsuarios();
		return ResponseEntity.status(HttpStatus.OK).body(nuevoUsuario);
	}
}
