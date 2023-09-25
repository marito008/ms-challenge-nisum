package com.challenge.nisum.controller.models.dto;

import java.util.List;

import com.challenge.nisum.controller.request.UsuarioRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefonoDTO {
	private String number;
    private String citycode;
	private String countrycode;

}
