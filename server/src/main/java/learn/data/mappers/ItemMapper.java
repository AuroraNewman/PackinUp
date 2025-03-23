package learn.data.mappers;

import learn.models.Category;
import learn.models.Item;
import learn.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<Item> {
    private UserMapper userMapper = new UserMapper();
    private CategoryMapper categoryMapper = new CategoryMapper();
    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = categoryMapper.mapRow(rs, rowNum);
        User user = userMapper.mapRow(rs, rowNum);

        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setItemName(rs.getString("item_name"));
        item.setUser(user);
        item.setCategory(category);

        return item;
    }
}
