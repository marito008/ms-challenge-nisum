package com.challenge.nisum.models.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends Auditable {

	@Id
	@GeneratedValue
	private UUID id;
	@Column(name = "nombre", length = 150)
	private String nombre;
	
	@Column(name = "email")
	private String email;

	// Listado de teléfonos
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Telefono> telefonos;

	@OneToOne(mappedBy = "usuario")
	private Password password;




}
