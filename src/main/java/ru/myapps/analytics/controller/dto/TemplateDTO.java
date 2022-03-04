package ru.myapps.analytics.controller.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TemplateDTO {
	private String templateId;
	private String template;
	private List<String> recipients;
	private List<Map.Entry<String, String>> variableTypes;
}
