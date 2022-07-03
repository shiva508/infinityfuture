package com.pool.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.pool.domain.User;
import com.pool.model.email.EmailModel;
import com.pool.service.user.UserServiceImpl;
import com.pool.util.InfinityFutureConstant;

@Service
public class InfinityFutureEmailService {

	private Logger log = LoggerFactory.getLogger(InfinityFutureEmailService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendRegistrationConfirmation(EmailModel emailModel) {
		log.info("Sending email to new user:" + emailModel.getEmail());
		MimeMessage msg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(msg, true);
			helper.setTo(emailModel.getEmail());
			helper.setSubject(InfinityFutureConstant.WELCOME_MESSAGE + emailModel.getUserName());
			helper.setText("<P>Welcome </br></p>"
					+ "<p style=\"color:blue;font-size:46px;\">\n" + "      We are glad to see you here, <strong>"
					+ emailModel.getUserName() + "</strong>\n" + "    </p>", true);
			javaMailSender.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
}
