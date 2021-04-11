package ru.myapps.analytics.service;

import ru.myapps.analytics.vo.Message;
import ru.myapps.analytics.vo.TemplateRequest;

public interface ScheduleNotificationSenderService {
	Message scheduleNotificationSender(TemplateRequest templateRequest, long timeInSeconds);
}
