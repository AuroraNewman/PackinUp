package learn.data.mappers;

import learn.models.Item;
import learn.models.Template;
import learn.models.TemplateItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TemplateItemMapper implements RowMapper<TemplateItem> {
    TemplateMapper templateMapper = new TemplateMapper();
    ItemMapper itemMapper = new ItemMapper();

    @Override
    public TemplateItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        Template template = templateMapper.mapRow(rs, rowNum);
        Item item = itemMapper.mapRow(rs, rowNum);

        TemplateItem templateItem = new TemplateItem();
        templateItem.setTemplateItemId(rs.getInt("template_item_id"));
        templateItem.setQuantity(rs.getInt("template_item_quantity"));
        templateItem.setChecked(rs.getBoolean("template_item_is_checked"));
        templateItem.setTemplate(template);
        templateItem.setItem(item);

        return templateItem;
    }
}
