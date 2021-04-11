package ru.myapps.analytics.service.impl;

import org.springframework.stereotype.Service;
import ru.myapps.analytics.entity.Template;
import ru.myapps.analytics.dto.TemplateDTO;
import ru.myapps.analytics.repository.TemplateRepository;
import ru.myapps.analytics.service.AddTemplateService;
import ru.myapps.analytics.mapper.DTOTemplateMapper;

@Service
public class AddTemplateServiceImpl implements AddTemplateService {
	private final DTOTemplateMapper dtoTemplateMapper;
	private final TemplateRepository templateRepository;

	public AddTemplateServiceImpl(DTOTemplateMapper dtoTemplateMapper, TemplateRepository templateRepository) {
		this.dtoTemplateMapper = dtoTemplateMapper;
		this.templateRepository = templateRepository;
	}

	@Override
	public TemplateDTO addTemplate(TemplateDTO template) {
		return dtoTemplateMapper.toDto(templateRepository.save(dtoTemplateMapper.toEntity(template)));
	}
}
