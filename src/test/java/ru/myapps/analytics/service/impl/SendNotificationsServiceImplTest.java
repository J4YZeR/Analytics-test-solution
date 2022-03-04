package ru.myapps.analytics.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.myapps.analytics.domain.Template;
import ru.myapps.analytics.helper.MessageSender;
import ru.myapps.analytics.repository.TemplateRepository;
import ru.myapps.analytics.service.SendNotificationsService;
import ru.myapps.analytics.domain.Message;
import ru.myapps.analytics.domain.TemplateRequest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class SendNotificationsServiceImplTest {
	@Autowired
	SendNotificationsService sendNotificationsService;

	@MockBean
	@Qualifier("messageSenderRestTemplate")
	MessageSender sender;
	@MockBean
	TemplateRepository templateRepository;

	@Test
	void sendNotifications() {
		TemplateRequest templateRequest = new TemplateRequest();
		Template template = new Template();
		template.setTemplateId("id");
		template.setTemplate("message");
		Message message = new Message();
		templateRequest.setTemplateId("id");
		message.setMessage("message");
		when(templateRepository.findById(templateRequest.getTemplateId())).thenReturn(Optional.of(template));
		Assertions.assertEquals(message, sendNotificationsService.sendNotifications(templateRequest));

	}
}