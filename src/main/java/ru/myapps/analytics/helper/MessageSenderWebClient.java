package ru.myapps.analytics.helper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.myapps.analytics.exception.CustomExceptionType;
import ru.myapps.analytics.exception.ExceptionFactory;
import ru.myapps.analytics.vo.Message;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageSenderWebClient implements MessageSender {
	private Logger LOGGER = LoggerFactory.getLogger(MessageSenderWebClient.class);

	@Override
	public Message sendMessage(List<String> recipients, Message messageToSend) {
		List<String> notSentMessageRecipientsList = new ArrayList<>();
		for (String recipient : recipients) {
			LOGGER.info("Sending message " + messageToSend.toString() + " to " + recipient + " via RestTemplate");
			WebClient webClient = WebClient.builder()
					.baseUrl(recipient)
					.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.build();
			try {
				webClient.post()
						.body(Mono.just(messageToSend), Message.class)
						.retrieve().bodyToMono(Message.class).block();
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
		recipients.forEach(recipient -> {
			LOGGER.info("Sending message " + messageToSend.toString() + " to " + recipient + " via WebClient");
		});
		return messageToSend;
	}
}
