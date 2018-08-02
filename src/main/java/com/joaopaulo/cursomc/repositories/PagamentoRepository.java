package com.joaopaulo.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaopaulo.cursomc.domain.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento,Integer> {

}
