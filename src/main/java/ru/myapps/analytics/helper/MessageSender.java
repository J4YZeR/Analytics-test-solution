package ru.myapps.analytics.helper;

import ru.myapps.analytics.vo.Message;

import java.util.List;

public interface MessageSender {
	Message sendMessage(List<String> recepients, Message messageToSend);
}
