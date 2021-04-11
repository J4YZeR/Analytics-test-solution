package ru.myapps.analytics.entity;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Template {
	@LazyCollection(LazyCollectionOption.FALSE)
	@ElementCollection
	List<String> recipients;
	@Id
	private String templateId;
	private String template;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	private List<VariableType> variableTypes;
}
