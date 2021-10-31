package com.pool.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.pool.constant.InfinityFutureConstant;
import com.pool.domain.User;

@Service
public class InfinityFutureEmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendRegistrationConfirmation(User user) {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(msg, true);
			helper.setTo(user.getEmail());
			helper.setSubject(InfinityFutureConstant.WELCOME_MESSAGE + user.getFirstName());
			helper.setText("<p style=\"color:blue;font-size:46px;\">\n"
					+ "      We are glad to see you here, <strong>"+user.getFirstName()+"</strong>\n" + "    </p>",true);
			 javaMailSender.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
