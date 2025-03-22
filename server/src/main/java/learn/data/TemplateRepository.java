package learn.data;

import learn.models.Template;

import java.util.List;

public interface TemplateRepository {
    Template findByName(String templateName);
    Template findById(int templateId);
    List<Template> findByUserId(int userId);
    Template create(Template template);
}
