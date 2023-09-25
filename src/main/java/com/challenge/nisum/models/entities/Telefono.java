package com.challenge.nisum.models.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "telefono")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telefono {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "number")
    private String number;
	@Column(name = "city_code")
    private String cityCode;
	@Column(name = "country_code")
    private String contryCode;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
	
	

}
