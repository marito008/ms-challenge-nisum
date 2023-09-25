package com.challenge.nisum.service;

import com.challenge.nisum.controller.request.UsuarioRequest;
import com.challenge.nisum.controller.response.UsuarioResponse;
import com.challenge.nisum.exceptions.NisumException;
import com.challenge.nisum.models.entities.Usuario;
import com.challenge.nisum.repository.PasswordRepository;
import com.challenge.nisum.repository.UsuarioRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UsuarioServiceImplTest.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordRepository passwordRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testRegistrarUsuario() throws NisumException {
        // Arrange
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setEmail("test@example.com");
        usuarioRequest.setPassword("MiClave#2023");
        usuarioRequest.setPhones(new ArrayList<>());

        Optional<Usuario> usuarioExistente = Optional.empty();

        when(usuarioRepository.findByEmail(usuarioRequest.getEmail())).thenReturn(usuarioExistente);

        // Act
        UsuarioResponse usuarioResponse = usuarioService.registrarUsuario(usuarioRequest);
        when(usuarioService.registrarUsuario(usuarioRequest)).thenReturn(usuarioResponse);
        // Assert
        assertNotNull(usuarioResponse);
        assertTrue(usuarioResponse.isActive());
        assertNotNull(usuarioResponse.getToken());
        assertNotNull(usuarioResponse.getCreated());

    }

    @Test
    public void testRegistrarUsuario_EmailInvalido() throws NisumException {
        // Arrange
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setEmail("invalidemail");
        usuarioRequest.setPassword("ValidPassword");
       // usuarioRequest.setPhones(/* Lista de teléfonos */);

        when(usuarioRepository.findByEmail(usuarioRequest.getEmail())).thenReturn(Optional.empty());

        // No hay Assert necesario porque esperamos una excepción
         NisumException exception = assertThrows(NisumException.class, () ->  usuarioService.registrarUsuario(usuarioRequest));

         assertNotNull(exception);
         assertEquals(HttpStatus.CONFLICT, exception.getHttpCode());
    }

    @Test
    public void testRegistrarUsuario_PasswordInvalido() throws NisumException {
        // Arrange
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setEmail("aaaaaaa@dominio.cl)");
        usuarioRequest.setPassword("ValidPassword");
        // usuarioRequest.setPhones(/* Lista de teléfonos */);

        when(usuarioRepository.findByEmail(usuarioRequest.getEmail())).thenReturn(Optional.of(new Usuario()));

        // No hay Assert necesario porque esperamos una excepción
        NisumException exception = assertThrows(NisumException.class, () ->  usuarioService.registrarUsuario(usuarioRequest));

        assertNotNull(exception);
        assertEquals(HttpStatus.CONFLICT, exception.getHttpCode());
    }
    @Test
    public void testRegistrarUsuario_existente() throws NisumException {
        // Arrange
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setEmail("aaaaaaa@dominio.cl)");
        usuarioRequest.setPassword("12334");
        // usuarioRequest.setPhones(/* Lista de teléfonos */);
        Optional<Usuario> usuarioExistente = Optional.of(new Usuario());
        usuarioExistente.get().setEmail("aaaaaaa@dominio.cl");
        when(usuarioRepository.findByEmail(usuarioRequest.getEmail())).thenReturn(usuarioExistente);

        // No hay Assert necesario porque esperamos una excepción
        NisumException exception = assertThrows(NisumException.class, () ->  usuarioService.registrarUsuario(usuarioRequest));

        assertNotNull(exception);
        assertEquals(HttpStatus.CONFLICT, exception.getHttpCode());
    }

    
}