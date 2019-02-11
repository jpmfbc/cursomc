package com.joaopaulo.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.joaopaulo.cursomc.domain.Categoria;
import com.joaopaulo.cursomc.dto.CategoriaDTO;
import com.joaopaulo.cursomc.repositories.CategoriaRepository;
import com.joaopaulo.cursomc.services.exception.DataIntegrityException;
import com.joaopaulo.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	
	public List<Categoria> findAll() {
		return repo.findAll();		
	}
	
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Categoria insert(Categoria c) {
		c.setId(null);
		return  repo.save(c);
		
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj,obj);
		return repo.save(newObj);
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw  new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos.");
		}	
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage,String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}

	private void updateData(Categoria newObj, Categoria obj ) {
		newObj.setNome(obj.getNome());
	}

	
}
