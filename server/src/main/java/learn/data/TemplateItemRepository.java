package learn.data;

import learn.models.TemplateItem;

import java.util.List;

public interface TemplateItemRepository {
    boolean create(TemplateItem item);
    List<TemplateItem> findAllByTemplateId(int templateId);
}
