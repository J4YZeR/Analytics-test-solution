package ru.myapps.analytics.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.myapps.analytics.domain.Template;

@Repository
public interface TemplateRepository extends CrudRepository<Template, String> {
}
