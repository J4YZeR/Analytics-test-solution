package ru.myapps.analytics.helper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.myapps.analytics.helper.exception.CustomExceptionType;
import ru.myapps.analytics.helper.exception.ExceptionFactory;
import ru.myapps.analytics.domain.Message;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageSenderRestTemplate implements MessageSender {
	private final Logger LOGGER = LoggerFactory.getLogger(MessageSenderRestTemplate.class);
	private final RestTemplate restTemplate;

	public MessageSenderRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Message sendMessage(List<String> recipients, Message messageToSend) {
		List<String> notSentMessageRecipientsList = new ArrayList<>();
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Message> request =
					new HttpEntity<>(messageToSend, headers);
		for (String recipient : recipients) {
			LOGGER.info("Sending message " + messageToSend.toString() + " to " + recipient + " via RestTemplate");
			try {
				restTemplate.postForEntity(recipient, request, Message.class);
			} catch (Exception e) {
				notSentMessageRecipientsList.add(recipient);
			}

		}
		if (notSentMessageRecipientsList.size() != 0) {
			if (notSentMessageRecipientsList.size() == 1) {
				throw ExceptionFactory.create(CustomExceptionType.CANNOT_SEND_MESSAGE_TO_RECIPIENT,
						notSentMessageRecipientsList.get(0));
			} else {
				throw ExceptionFactory.create(CustomExceptionType.CANNOT_SEND_MESSAGE_TO_RECIPIENTS,
						StringUtils.join(notSentMessageRecipientsList, ", "));
			}
		}
		return messageToSend;
	}
}
