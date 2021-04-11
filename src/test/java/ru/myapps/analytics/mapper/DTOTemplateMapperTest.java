package ru.myapps.analytics.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.myapps.analytics.dto.TemplateDTO;
import ru.myapps.analytics.entity.Template;
import ru.myapps.analytics.entity.VariableType;

import java.util.AbstractMap;
import java.util.ArrayList;

@SpringBootTest
class DTOTemplateMapperTest {
	@Autowired
	private DTOTemplateMapper dtoTemplateMapper;

	@Test
	void toEntity() {
		Template template = new Template();
		VariableType variableType = new VariableType();
		variableType.setVariableType("1");
		variableType.setVariableName("2");
		template.setTemplateId("TemplateID");
		template.setTemplate("template");
		template.setRecipients(new ArrayList<>() {{
			add("1");
		}});
		template.setVariableTypes(new ArrayList<>() {{
			add(variableType);
		}});
		TemplateDTO templateDTO = dtoTemplateMapper.toDto(template);

		Assertions.assertEquals(template.getTemplateId(), templateDTO.getTemplateId());
		Assertions.assertEquals(template.getTemplate(), templateDTO.getTemplate());
		Assertions.assertEquals(template.getVariableTypes().get(0).getVariableName(), templateDTO.getVariableTypes().get(0).getKey());
		Assertions.assertEquals(template.getVariableTypes().get(0).getVariableType(), templateDTO.getVariableTypes().get(0).getValue());
		Assertions.assertEquals(template.getRecipients(), templateDTO.getRecipients());
	}

	@Test
	void toDto() {
		TemplateDTO templateDTO = new TemplateDTO();
		VariableType variableType = new VariableType();
		variableType.setVariableType("1");
		variableType.setVariableName("2");
		templateDTO.setTemplateId("TemplateID");
		templateDTO.setTemplate("template");
		templateDTO.setRecipients(new ArrayList<>() {{
			add("1");
		}});
		templateDTO.setVariableTypes(new ArrayList<>() {{
			add(new AbstractMap.SimpleEntry<>("1", "2"));
		}});
		Template template = dtoTemplateMapper.toEntity(templateDTO);

		Assertions.assertEquals(templateDTO.getTemplateId(), template.getTemplateId());
		Assertions.assertEquals(templateDTO.getTemplate(), template.getTemplate());
		Assertions.assertEquals(templateDTO.getVariableTypes().get(0).getKey(), template.getVariableTypes().get(0).getVariableName());
		Assertions.assertEquals(templateDTO.getVariableTypes().get(0).getValue(), template.getVariableTypes().get(0).getVariableType());
		Assertions.assertEquals(templateDTO.getRecipients(), template.getRecipients());
	}
}