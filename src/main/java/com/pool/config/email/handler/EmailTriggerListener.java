package com.pool.config.email.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pool.service.email.InfinityFutureEmailService;

@Component
public class EmailTriggerListener {
	
	private final InfinityFutureEmailService infinityFutureEmailService;
	
	@Autowired
	public EmailTriggerListener(InfinityFutureEmailService infinityFutureEmailService) {
		super();
		this.infinityFutureEmailService = infinityFutureEmailService;
	}


	@EventListener
	void handleRegistration(EmailTriggerEvent event) {
		infinityFutureEmailService.sendRegistrationConfirmation(event.getEmailModel());
		System.out.println("Registration event got triggered for customer::  " + event.getEmailModel());
	}
}
