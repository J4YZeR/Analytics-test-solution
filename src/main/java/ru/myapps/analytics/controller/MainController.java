package ru.myapps.analytics.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.myapps.analytics.dto.TemplateDTO;
import ru.myapps.analytics.vo.Message;
import ru.myapps.analytics.vo.TemplateRequest;
import ru.myapps.analytics.service.AddTemplateService;
import ru.myapps.analytics.service.ScheduleNotificationSenderService;
import ru.myapps.analytics.service.SendNotificationsService;

@RestController
public class MainController {
	private final AddTemplateService addTemplateService;
	private final SendNotificationsService sendNotificationsService;
	private final ScheduleNotificationSenderService scheduleNotificationSenderService;

	public MainController(AddTemplateService addTemplateService,
						  SendNotificationsService sendNotificationsService,
						  ScheduleNotificationSenderService scheduleNotificationSenderService) {
		this.addTemplateService = addTemplateService;
		this.sendNotificationsService = sendNotificationsService;
		this.scheduleNotificationSenderService = scheduleNotificationSenderService;
	}

	@PostMapping("/add")
	ResponseEntity<TemplateDTO> addNewTemplate(@RequestBody TemplateDTO templateDTO) {
		TemplateDTO template = addTemplateService.addTemplate(templateDTO);
		return ResponseEntity.ok(template);
	}
	@PostMapping("/send")
	ResponseEntity<Message> sendMessage(@RequestBody TemplateRequest templateRequest,
									   @RequestParam(required = false) Long timeInMilliseconds) {

		Message message;
		if (timeInMilliseconds == null) {
			message = sendNotificationsService.sendNotifications(templateRequest);
		} else {
			message = scheduleNotificationSenderService.scheduleNotificationSender(templateRequest, timeInMilliseconds);
		}
		return ResponseEntity.ok(message);
	}
}
