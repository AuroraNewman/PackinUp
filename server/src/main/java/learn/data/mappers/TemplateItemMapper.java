package learn.data.mappers;

import learn.models.TemplateItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TemplateItemMapper implements RowMapper<TemplateItem> {

    @Override
    public TemplateItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        TemplateItem templateItem = new TemplateItem();
        templateItem.setTemplateItemId(rs.getInt("template_item_id"));
        templateItem.setQuantity(rs.getInt("template_item_quantity"));
        templateItem.setChecked(rs.getBoolean("template_item_is_checked"));
        templateItem.setTemplateId(rs.getInt("template_item_template_id"));
        templateItem.setItemId(rs.getInt("template_item_item_id"));
        
        return templateItem;
    }
}
