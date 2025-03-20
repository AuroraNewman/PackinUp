package learn.data;

import learn.models.Template;

public interface TemplateRepository {
    Template findByName(String templateName);
    Template create(Template template);
}
