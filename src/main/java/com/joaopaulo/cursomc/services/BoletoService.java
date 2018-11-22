package com.joaopaulo.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.joaopaulo.cursomc.domain.PagamentoComBoleto;

/*Simulando um boleto service, no caso real será necessario consumir um webservice de boleto*/
@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date dataPedido) {
		Calendar c = Calendar.getInstance();
		c.setTime(dataPedido);
		c.add(Calendar.DAY_OF_MONTH,7);
		pagto.setDataVencimento(c.getTime());
	}
}
