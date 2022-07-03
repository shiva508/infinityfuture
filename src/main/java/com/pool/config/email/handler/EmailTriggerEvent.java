package com.pool.config.email.handler;

import com.pool.model.email.EmailModel;

public class EmailTriggerEvent {
	private EmailModel emailModel;

	public EmailTriggerEvent(EmailModel emailModel) {
		super();
		this.emailModel = emailModel;
	}

	public EmailModel getEmailModel() {
		return emailModel;
	}

}
