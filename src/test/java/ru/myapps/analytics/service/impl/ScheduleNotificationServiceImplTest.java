package ru.myapps.analytics.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.TaskScheduler;
import ru.myapps.analytics.service.ScheduleNotificationSenderService;
import ru.myapps.analytics.service.SendNotificationsService;
import ru.myapps.analytics.domain.Message;
import ru.myapps.analytics.domain.TemplateRequest;

import java.time.Duration;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduleNotificationServiceImplTest {
	@Autowired
	ScheduleNotificationSenderService scheduleNotificationService;

	@MockBean
	SendNotificationsService sendNotificationsService;
	@MockBean
	TaskScheduler taskScheduler;

	@Test
	void scheduleNotificationSender() {
		long time = 1000L;
		TemplateRequest templateRequest = new TemplateRequest();
		Message message = new Message();
		templateRequest.setTemplateId("id");

		message.setMessage("message");
		when(taskScheduler.scheduleAtFixedRate(any(Runnable.class), any(Instant.class), any(Duration.class))).thenReturn(null);
		when(sendNotificationsService.sendNotifications(templateRequest)).thenReturn(message);
		Assertions.assertEquals(message, scheduleNotificationService.scheduleNotificationSender(templateRequest, time));
	}
}