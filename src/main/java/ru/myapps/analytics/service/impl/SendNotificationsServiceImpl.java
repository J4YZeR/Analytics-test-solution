package ru.myapps.analytics.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myapps.analytics.domain.Template;
import ru.myapps.analytics.domain.VariableType;
import ru.myapps.analytics.helper.exception.CustomExceptionType;
import ru.myapps.analytics.helper.exception.ExceptionFactory;
import ru.myapps.analytics.helper.MessageSender;
import ru.myapps.analytics.repository.TemplateRepository;
import ru.myapps.analytics.service.SendNotificationsService;
import ru.myapps.analytics.domain.Message;
import ru.myapps.analytics.domain.TemplateRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SendNotificationsServiceImpl implements SendNotificationsService {
	private final TemplateRepository templateRepository;
	private final MessageSender messageSender;

	public SendNotificationsServiceImpl(TemplateRepository templateRepository,
										@Qualifier("messageSenderRestTemplate") MessageSender messageSender) {
		this.templateRepository = templateRepository;
		this.messageSender = messageSender;
	}

	@Override
	public Message sendNotifications(TemplateRequest templateRequest) {
		Optional<Template> templateOptional = templateRepository.findById(templateRequest.getTemplateId());
		AtomicReference<Message> messageToSend = new AtomicReference<>();
		templateOptional.ifPresentOrElse(template -> {
			messageToSend.set(composeMessage(templateRequest.getVariables(), template.getVariableTypes(),
					template.getTemplate()));
			messageSender.sendMessage(template.getRecipients(), messageToSend.get());

		}, () -> {
			throw ExceptionFactory.create(CustomExceptionType.ID_NOT_FOUND, templateRequest.getTemplateId());
		});
		return messageToSend.get();
	}

	private Message composeMessage(List<Map.Entry<String, String>> variables, List<VariableType> variableTypes, String template) {
		Message message = new Message();
		var messageToSend = template;
		if (!Objects.isNull(variables)) {
			for (Map.Entry<String, String> variable : variables) {
				String varType = findVariableTypeName(variable.getKey(), variableTypes);
				if (!Objects.isNull(varType)) {
					if (!isVariableMatchesType(variable.getValue(), varType)) {
						throw ExceptionFactory.create(CustomExceptionType.VARIABLE_NOT_MATCHES_TYPE, variable.getKey(), varType);
					}
				}
				messageToSend = messageToSend.replace("$" + variable.getKey() + "$", variable.getValue()); // TODO ???????? " " + varType
			}
		}
		message.setMessage(messageToSend);
		return message;
	}

	private boolean isVariableMatchesType(String variable, String varType) {
		switch (varType) {
			case "string":
				return !StringUtils.isNumeric(variable);
			case "number":
				return StringUtils.isNumeric(variable);
			default:
				return false;
		}
	}

	/**
	 * ???????????????????? ?????? ???????? ?????????????????? ????????????????????.
	 *
	 * @param variableName  ?????????????????? ????????????????????.
	 * @param variableTypes ???????????? ?????????? ?????? ????????????????????.
	 * @return ?????? ???????? ???????????????????? ?????? null, ???????? ???????????????????? ?????? ?? ????????????.
	 */
	private String findVariableTypeName(String variableName, List<VariableType> variableTypes) {
		for (VariableType variableType : variableTypes) {
			if (variableType.getVariableName().equals(variableName)) {
				return variableType.getVariableType();
			}
		}
		return null;
	}
}
