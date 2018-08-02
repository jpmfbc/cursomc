package com.joaopaulo.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaopaulo.cursomc.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {

}
