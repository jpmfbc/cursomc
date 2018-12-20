package com.joaopaulo.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.joaopaulo.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
