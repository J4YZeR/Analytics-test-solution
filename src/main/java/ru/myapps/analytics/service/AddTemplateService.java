package ru.myapps.analytics.service;

import ru.myapps.analytics.entity.Template;
import ru.myapps.analytics.dto.TemplateDTO;

public interface AddTemplateService {
	TemplateDTO addTemplate(TemplateDTO template);
}
