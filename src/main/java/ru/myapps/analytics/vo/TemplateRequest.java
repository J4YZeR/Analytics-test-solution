package ru.myapps.analytics.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TemplateRequest {
	String templateId;
	List<Map.Entry<String, String>> variables;
}
