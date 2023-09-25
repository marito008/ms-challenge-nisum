package com.challenge.nisum.controller.request;

import java.util.List;

import com.challenge.nisum.controller.models.dto.TelefonoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
	private String name;
    private String email;
    private String password;
    private List<TelefonoDTO> phones; 

}
