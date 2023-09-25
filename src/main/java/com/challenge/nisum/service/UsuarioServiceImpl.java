package com.challenge.nisum.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.challenge.nisum.controller.response.UsuarioResponse;
import com.challenge.nisum.models.entities.Password;
import com.challenge.nisum.models.entities.Telefono;
import com.challenge.nisum.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.challenge.nisum.controller.models.dto.TelefonoDTO;
import com.challenge.nisum.controller.request.UsuarioRequest;
import com.challenge.nisum.exceptions.NisumException;
import com.challenge.nisum.models.Mensaje;
import com.challenge.nisum.models.entities.Usuario;
import com.challenge.nisum.repository.UsuarioRepository;
import com.challenge.nisum.utils.AESUtil;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordRepository passwordRepository;

	@Override
	public UsuarioResponse registrarUsuario(UsuarioRequest usuarioRequest) throws NisumException {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuarioRequest.getEmail());

		if (usuarioExistente.isPresent()) {
            throw new NisumException(HttpStatus.CONFLICT, new Mensaje("El correo ya registrado"));
        }


        if (!validarEmail(usuarioRequest.getEmail())) {
        	throw new NisumException(HttpStatus.CONFLICT, new Mensaje("El formato de el correo  no es válido"));
        }


        if (!validarContrasena(usuarioRequest.getPassword())) {
            throw new NisumException(HttpStatus.CONFLICT, new Mensaje("El formato de la contraseña no es válido"));
        }
        


        Usuario nuevoUsuario = new Usuario();
        // Asociar teléfonos
        nuevoUsuario.setTelefonos(getPhonesList(usuarioRequest.getPhones(), nuevoUsuario));
        nuevoUsuario.setEmail(usuarioRequest.getEmail());
        nuevoUsuario.setNombre(usuarioRequest.getName());
        nuevoUsuario.setCreatedDate(new Date());

        usuarioRepository.save(nuevoUsuario);
        
        // Generar token (puedes usar UUID.randomUUID() u otro mecanismo)
        String token = UUID.randomUUID().toString();
        
        // Crear contraseña
        Password password = new Password();
        //encriptar la password
        password.setPassword(AESUtil.encrypt(usuarioRequest.getPassword()));
        password.setUsuario(nuevoUsuario);
        password.setIsActive(true);
        passwordRepository.save(password);
        
        
        UsuarioResponse usuarioResponse = new UsuarioResponse();
        usuarioResponse.setId(nuevoUsuario.getId());
        usuarioResponse.setActive(true);
        usuarioResponse.setToken(token);
        usuarioResponse.setCreated(nuevoUsuario.getCreatedDate());

		return usuarioResponse;
	}

    @Override
    public List<Usuario> obtenerUsuarios() throws NisumException {
	    List<Usuario> usuarioList = usuarioRepository.findAll();


        return usuarioList;
    }

    private List<Telefono> getPhonesList(List<TelefonoDTO> phones, Usuario usuario) {
		List<Telefono> list = phones.stream().map(
				p -> new Telefono(null, p.getNumber(), p.getCitycode(), p.getCountrycode(), usuario)).collect(Collectors.toList());
		return list;
	}

    public static boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }

    private boolean validarContrasena(String contrasena) {
        String regex ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,}$";

        return Pattern.matches(regex, contrasena);
    }


}
