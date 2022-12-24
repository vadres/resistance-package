package com.ennodo.resistence.infra.dto;

import com.ennodo.resistence.infra.entity.JogadorJpa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogadorDTO {
	private Integer id;
	private String nome;
	private String personagem;
	private String info;
	private List<String> revelados;

	public static JogadorDTO toDTO(JogadorJpa jogadorJpa) {
		JogadorDTO dto = new JogadorDTO();
		dto.setId(jogadorJpa.getId());
		dto.setNome(jogadorJpa.getNome());
		return dto;
	}
}
