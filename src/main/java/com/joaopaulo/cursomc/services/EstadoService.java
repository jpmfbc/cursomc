package com.joaopaulo.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaopaulo.cursomc.domain.Estado;
import com.joaopaulo.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findAllByOrderByNome(){
		return estadoRepository.findAllByOrderByNome();
	}
}
