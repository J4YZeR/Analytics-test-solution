package ru.myapps.analytics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.myapps.analytics.controller.dto.TemplateDTO;
import ru.myapps.analytics.service.AddTemplateService;
import ru.myapps.analytics.service.ScheduleNotificationSenderService;
import ru.myapps.analytics.service.SendNotificationsService;
import ru.myapps.analytics.domain.Message;
import ru.myapps.analytics.domain.TemplateRequest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainControllerTest {
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AddTemplateService addTemplateService;

	@MockBean
	private SendNotificationsService sendNotificationsService;

	@MockBean
	private ScheduleNotificationSenderService scheduleNotificationSenderService;

	@BeforeAll
	private void initFields() {
		objectMapper = new ObjectMapper();
	}

	@SneakyThrows
	@Test
	void addNewTemplate() {
		TemplateDTO templateDTO = new TemplateDTO();
		String templateDTOString;
		templateDTO.setTemplateId("TemplateID");
		templateDTO.setTemplate("template");
		templateDTOString = objectMapper.writeValueAsString(templateDTO);
		when(addTemplateService.addTemplate(templateDTO)).thenReturn(templateDTO);
		this.mockMvc.perform(
				post("/add").contentType(MediaType.APPLICATION_JSON)
						.content(templateDTOString)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(templateDTOString));
	}

	@SneakyThrows
	@Test
	void sendMessage() {
		long time = 1000L;
		TemplateRequest templateRequest = new TemplateRequest();
		Message message = new Message();
		String templateRequestString;
		String messageString;
		message.setMessage("message");
		templateRequest.setTemplateId("id");
		templateRequestString = objectMapper.writeValueAsString(templateRequest);
		messageString = objectMapper.writeValueAsString(message);

		when(sendNotificationsService.sendNotifications(templateRequest)).thenReturn(message);
		this.mockMvc.perform(
				post("/send").contentType(MediaType.APPLICATION_JSON)
						.content(templateRequestString)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(messageString));

		when(scheduleNotificationSenderService.scheduleNotificationSender(templateRequest, time)).thenReturn(message);
		this.mockMvc.perform(
				post("/send").contentType(MediaType.APPLICATION_JSON)
						.content(templateRequestString)
						.accept(MediaType.APPLICATION_JSON).param("timeInMilliseconds", String.valueOf(time)))
				.andExpect(status().isOk()).andExpect(content().json(messageString));
	}
}