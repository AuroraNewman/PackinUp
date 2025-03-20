package learn.data;

import learn.models.Template;

public interface TemplateRepository {
    Template findById(int templateId);
    Template create(Template template);
}
