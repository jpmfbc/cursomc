package com.joaopaulo.cursomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.joaopaulo.cursomc.domain.Cidade;
import com.joaopaulo.cursomc.domain.Cliente;
import com.joaopaulo.cursomc.domain.Endereco;
import com.joaopaulo.cursomc.domain.enums.Perfil;
import com.joaopaulo.cursomc.domain.enums.TipoCliente;
import com.joaopaulo.cursomc.dto.ClienteDTO;
import com.joaopaulo.cursomc.dto.ClienteNewDto;
import com.joaopaulo.cursomc.repositories.ClienteRepository;
import com.joaopaulo.cursomc.repositories.EnderecoRepository;
import com.joaopaulo.cursomc.security.UserSS;
import com.joaopaulo.cursomc.services.exception.AuthorizationException;
import com.joaopaulo.cursomc.services.exception.DataIntegrityException;
import com.joaopaulo.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private S3Service s3Service;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if(user == null || user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new  AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente c) {
		c.setId(null);
		c = repo.save(c);
		enderecoRepository.saveAll(c.getEnderecos());
		return c;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir porque há pedidos relacionados");
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null,null);
	}

	public Cliente fromDTO(ClienteNewDto objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()),pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		return s3Service.upaloadFile(multipartFile);
	}

}
