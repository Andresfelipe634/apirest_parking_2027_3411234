package com.sena.apiparking.model;



import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehiculos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class Vehiculo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_vehiculo;
	@Column(unique= true,nullable = false)
	private String placa;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoVehiculo tipo;
	@Lob 
	private String descripcion;
	@OneToMany(mappedBy = "vehiculo")
	private List<RegistroParqueo> registros; 
}
