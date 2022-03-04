package ru.myapps.analytics.helper;

import ru.myapps.analytics.domain.Message;

import java.util.List;

public interface MessageSender {
	Message sendMessage(List<String> recepients, Message messageToSend);
}
