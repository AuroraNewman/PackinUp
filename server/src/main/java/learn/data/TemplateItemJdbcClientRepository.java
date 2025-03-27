package learn.data;

import learn.data.mappers.TemplateItemMapper;
import learn.models.TemplateItem;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
                            t.template_id,
                            t.template_name,
                            t.template_description,
                            tt.trip_type_id,
                            tt.trip_type_name,
                            tt.trip_type_description,
                            u.username,
                            u.email,
                            u.password,
                            i.item_id,
                            i.item_name,
                            i.item_user_id as user_id,
                            c.category_id,
                            c.category_name,
                            c.category_color
                        from template_items ti
                        join templates t on ti.template_item_template_id = t.template_id
                        join trip_types tt on t.template_trip_type_id = tt.trip_type_id
                        join users u on t.template_user_id = u.user_id
                        join items i on ti.template_item_item_id = i.item_id
                        join categories c on i.item_category_id = c.category_id
            """;

    @Override
    public List<TemplateItem> findAllByTemplateId(int templateId) {
        final String sql = SELECT + " where ti.template_item_template_id = ?;";
        return jdbcClient.sql(sql)
                .param(templateId)
                .query(new TemplateItemMapper())
                .list();
    }

    @Override
    public List<TemplateItem> findAllByTripTypeId(int tripTypeId) {
        final String sql = SELECT + " where tt.trip_type_id = ?;";
        return jdbcClient.sql(sql)
                .param(tripTypeId)
                .query(new TemplateItemMapper())
                .list();
    }

    @Override
    public boolean deleteById(int templateItemId) {
        final String sql = """
                delete from template_items
                where template_item_id = ?
                """;
        int rowsAffected = jdbcClient.sql(sql)
                .param(templateItemId)
                .update();
        return rowsAffected > 0;
    }

    @Override
    public TemplateItem create(TemplateItem templateItem) {
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
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected= jdbcClient.sql(sql)
                .param("template_item_quantity", templateItem.getQuantity())
                .param("template_item_is_checked", templateItem.isChecked())
                .param("template_item_template_id", templateItem.getTemplate().getTemplateId())
                .param("template_item_item_id", templateItem.getOutgoingItem().getItemId())
                .update(keyHolder, "template_item_id");

        if (rowsAffected <= 0) {
            return null;
        }
        templateItem.setTemplateItemId(keyHolder.getKey().intValue());
        return templateItem;
    }


}
