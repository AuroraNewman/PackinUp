package learn.data;

import learn.models.TemplateItem;

import java.util.List;

public interface TemplateItemRepository {
    TemplateItem create(TemplateItem item);
    TemplateItem findById(int templateItemId);
    List<TemplateItem> findAllByTemplateId(int templateId);
    List<TemplateItem> findAllByTripTypeId(int tripTypeId);
    boolean deleteById(int templateItemId);
}
