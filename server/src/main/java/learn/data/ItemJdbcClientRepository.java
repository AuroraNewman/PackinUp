package learn.data;

import learn.data.mappers.ItemMapper;
import learn.models.Item;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ItemJdbcClientRepository implements ItemRepository{
    private final String SELECT= """
            select 
                i.item_id,
                i.item_name,
                i.item_user_id,
                c.category_id,
                c.category_name,
                c.category_color,
                u.user_id,
                u.username,
                u.email,
                u.password
            from items i
            join categories c on i.item_category_id = c.category_id
            join users u on i.item_user_id = u.user_id
            """;
    private JdbcClient jdbcClient;

    public ItemJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Item findById(int id) {
        final String sql = SELECT + " where item_id = ?;";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new ItemMapper())
                .optional().orElse(null);
    }

    @Override
    public boolean create(Item item) {
        final String sql = """
                insert into items (item_name, item_user_id, item_category_id)
                values (:item_name, :item_user_id, :item_category_id);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcClient.sql(sql)
                .param("item_name", item.getItemName())
                .param("item_user_id", item.getUser().getUserId())
                .param("item_category_id", item.getCategory().getCategoryId())
                .update(keyHolder, "item_id");
        item.setItemId(keyHolder.getKey().intValue());

        return rowsAffected > 0;
    }

    @Override
    public Item findByName(String name) {
        final String sql = SELECT + " where item_name = ?;";
        return jdbcClient.sql(sql)
                .param(name)
                .query(new ItemMapper())
                .optional().orElse(null);

    }
}
