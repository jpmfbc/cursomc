package com.joaopaulo.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaopaulo.cursomc.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Integer> {

}
