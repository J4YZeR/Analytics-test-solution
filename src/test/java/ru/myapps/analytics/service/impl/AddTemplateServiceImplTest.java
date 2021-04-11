package ru.myapps.analytics.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.myapps.analytics.dto.TemplateDTO;
import ru.myapps.analytics.entity.Template;
import ru.myapps.analytics.mapper.DTOTemplateMapper;
import ru.myapps.analytics.repository.TemplateRepository;
import ru.myapps.analytics.service.AddTemplateService;

import static org.mockito.Mockito.when;

@SpringBootTest
class AddTemplateServiceImplTest {

	@Autowired
	AddTemplateService addTemplateService;
	@MockBean
	TemplateRepository templateRepository;
	@MockBean
	DTOTemplateMapper dtoTemplateMapper;

	@Test
	void addTemplate() {
		TemplateDTO templateDTO = new TemplateDTO();
		Template template = new Template();
		templateDTO.setTemplateId("id");
		template.setTemplateId("id");
		when(dtoTemplateMapper.toDto(template)).thenReturn(templateDTO);
		when(dtoTemplateMapper.toEntity(templateDTO)).thenReturn(template);
		when(templateRepository.save(template)).thenReturn(template);
		Assertions.assertEquals(template.getTemplateId(), addTemplateService.addTemplate(templateDTO).getTemplateId());
	}
}