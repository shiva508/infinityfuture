package com.pool.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import com.pool.domain.User;

@Component
public class InfinityfutureUtil {

	String genearteUserName(User user) {
		StringBuffer ifId = new StringBuffer();
		ifId.append(user.getFirstName().charAt(0)).append(user.getLastName().charAt(0))
				.append(RandomStringUtils.random(6));

		return ifId.toString();
	}
}
