package learn.data;

import learn.data.mappers.ItemMapper;
import learn.models.Item;
import org.springframework.jdbc.core.simple.JdbcClient;
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
}
