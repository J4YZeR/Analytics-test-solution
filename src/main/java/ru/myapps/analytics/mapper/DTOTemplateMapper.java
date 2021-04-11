package ru.myapps.analytics.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.myapps.analytics.entity.Template;
import ru.myapps.analytics.dto.TemplateDTO;
import ru.myapps.analytics.entity.VariableType;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class DTOTemplateMapper {
	private final ModelMapper mapper;

	public DTOTemplateMapper(ModelMapper mapper) {
		this.mapper = mapper;
	}

	public Template toEntity(TemplateDTO dto) {
		if (Objects.isNull(dto)) {
			return null;
		}

		List<VariableType> variableTypeList = new ArrayList<>();
		if (!Objects.isNull(dto.getVariableTypes())) {
			dto.getVariableTypes().forEach(entry -> {
				VariableType variableType = new VariableType();
				variableType.setVariableName(entry.getKey());
				variableType.setVariableType(entry.getValue());
				variableTypeList.add(variableType);
			});
		}
		Template template = mapper.map(dto, Template.class);
		template.setVariableTypes(variableTypeList);
		return template;
	}

	public TemplateDTO toDto(Template entity) {
		if (Objects.isNull(entity)) {
			return null;
		}

		List<Map.Entry<String, String>> variableTypeList = new ArrayList<>();
		if (!Objects.isNull(entity.getVariableTypes())) {
			entity.getVariableTypes().forEach(variableType -> {
				AbstractMap.SimpleEntry<String, String> simpleEntry = new AbstractMap.SimpleEntry<>(
						variableType.getVariableName(), variableType.getVariableType());
				variableTypeList.add(simpleEntry);
			});
		}
		TemplateDTO templateDTO = mapper.map(entity, TemplateDTO.class);
		templateDTO.setVariableTypes(variableTypeList);
		return templateDTO;
	}
}
