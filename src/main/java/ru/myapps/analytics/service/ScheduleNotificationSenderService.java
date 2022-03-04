package ru.myapps.analytics.service;

import ru.myapps.analytics.domain.Message;
import ru.myapps.analytics.domain.TemplateRequest;

public interface ScheduleNotificationSenderService {
	Message scheduleNotificationSender(TemplateRequest templateRequest, long timeInSeconds);
}
