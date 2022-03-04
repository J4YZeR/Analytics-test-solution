package ru.myapps.analytics.service.impl;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import ru.myapps.analytics.service.ScheduleNotificationSenderService;
import ru.myapps.analytics.service.SendNotificationsService;
import ru.myapps.analytics.domain.Message;
import ru.myapps.analytics.domain.TemplateRequest;

import java.time.Duration;
import java.time.Instant;

@Service
public class ScheduleNotificationServiceImpl implements ScheduleNotificationSenderService {
	private final TaskScheduler taskScheduler;
	private final SendNotificationsService sendNotificationsService;

	public ScheduleNotificationServiceImpl(TaskScheduler taskScheduler, SendNotificationsService sendNotificationsService) {
		this.taskScheduler = taskScheduler;
		this.sendNotificationsService = sendNotificationsService;
	}

	@Override
	public Message scheduleNotificationSender(TemplateRequest templateRequest, long timeInMilliseconds) {
		Message message = sendNotificationsService.sendNotifications(templateRequest);
		taskScheduler.scheduleAtFixedRate(() -> {
			sendNotificationsService.sendNotifications(templateRequest);
		}, Instant.now().plusMillis(timeInMilliseconds), Duration.ofMillis(timeInMilliseconds));
		return message;
	}
}
