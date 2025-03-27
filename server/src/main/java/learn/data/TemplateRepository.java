package learn.data;

import learn.models.Template;
import learn.models.TemplateItem;

import java.util.List;

public interface TemplateRepository {
    Template findByName(String templateName);
    Template findById(int templateId);
    List<Template> findByUserId(int userId);
    Template create(Template template);
    boolean update(Template template);
    boolean deleteById(int templateId);
}
