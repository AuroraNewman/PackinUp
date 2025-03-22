package learn.data;

import learn.data.mappers.TemplateItemMapper;
import learn.models.TemplateItem;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TemplateItemJdbcClientRepository implements TemplateItemRepository{
    private final JdbcClient jdbcClient;

    public TemplateItemJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    final String SELECT = """
            select
                ti.template_item_id,
                ti.template_item_quantity,
                ti.template_item_is_checked,
                ti.template_item_template_id,
                ti.template_item_item_id
            from template_items ti
            """;

    @Override
    public boolean create(TemplateItem item) {
        final String sql = """
                insert into template_items (
                    template_item_quantity,
                    template_item_is_checked,
                    template_item_template_id,
                    template_item_item_id
                ) values (
                    :template_item_quantity,
                    :template_item_is_checked,
                    :template_item_template_id,
                    :template_item_item_id
                )
                """;
        return jdbcClient.sql(sql)
                .param("template_item_quantity", item.getQuantity())
                .param("template_item_is_checked", item.isChecked())
                .param("template_item_template_id", item.getTemplateId())
                .param("template_item_item_id", item.getItemId())
                .update() > 0;
    }

    @Override
    public List<TemplateItem> findAllByTemplateId(int templateId) {
        final String sql = SELECT + " where template_item_template_id = ?;";
        return jdbcClient.sql(sql)
                .param(templateId)
                .query(new TemplateItemMapper())
                .list();
    }
}
