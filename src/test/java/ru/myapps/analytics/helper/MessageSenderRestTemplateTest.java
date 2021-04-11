package ru.myapps.analytics.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import ru.myapps.analytics.vo.Message;

import java.util.ArrayList;

@SpringBootTest
class MessageSenderRestTemplateTest {

	@Qualifier("messageSenderRestTemplate")
	@Autowired
	private MessageSender messageSender;
	@Autowired
	private RestTemplate restTemplate;

	@SneakyThrows
	@Test
	void sendMessage() {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<String> recipients = new ArrayList<>() {{
			add("https://httpbin.org/post");
		}};
		Message message = new Message();
		message.setMessage("message");
		Assertions.assertEquals(message,
				messageSender.sendMessage(recipients, message));
	}
}