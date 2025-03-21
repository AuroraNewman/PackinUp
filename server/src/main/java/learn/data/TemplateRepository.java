package learn.data;

import learn.models.Template;

import java.util.List;

public interface TemplateRepository {
    Template findByName(String templateName);
    List<Template> findAllListsByUserId(int userId);
    List<Template> findAllTemplatesByUserId(int userId);
    Template create(Template template);
}
