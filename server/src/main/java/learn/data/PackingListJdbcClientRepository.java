package learn.data;

import learn.data.mappers.PackingListMapper;
import learn.models.PackingList;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PackingListJdbcClientRepository implements PackingListRepository {
    private final String SELECT = """
            select
                t.template_id,
                t.template_name,
                t.template_reusable,
                t.template_description,
                t.template_reusable ,
                t.template_user_id,
                tt.trip_type_id,
                tt.trip_type_name,
                tt.trip_type_description,
                u.user_id,
                u.username,
                u.email,
                u.`password`
            from templates t
            inner join trip_types tt on t.template_trip_type_id = tt.trip_type_id
            inner join users u on t.template_user_id = u.user_id
            """;


    private JdbcClient jdbcClient;

    public PackingListJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public PackingList findByName(String templateName) {
        final String sql = SELECT + " where t.template_name = ?;";
        return jdbcClient.sql(sql)
                .param(templateName)
                .query(new PackingListMapper())
                .optional().orElse(null);
    }

    @Override
    public List<PackingList> findAllListsByUserId(int userId) {
            final String sql = SELECT + " where t.template_user_id = ?;";
            return jdbcClient.sql(sql)
                    .param(userId)
                    .query(new PackingListMapper())
                    .list();
        }

    @Override
    public List<PackingList> findAllTemplatesByUserId(int userId) {
        final String sql = SELECT + " where t.template_user_id = ? and t.template_reusable = true;";
        return jdbcClient.sql(sql)
                .param(userId)
                .query(new PackingListMapper())
                .list();
    }

    @Override
    public PackingList create(PackingList packingList) throws DuplicateKeyException{
        int rowsAffected = 0;
        final String sql = """
                insert into templates(template_name, template_description, template_reusable, template_trip_type_id, template_user_id)
                values(:template_name, :template_description, :template_reusable, :template_trip_type_id, :template_user_id);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
            rowsAffected = jdbcClient.sql(sql)
                    .param("template_name", packingList.getTemplateName())
                    .param("template_description", packingList.getTemplateDescription())
                    .param("template_reusable", packingList.isReusable())
                    .param("template_user_id", packingList.getTemplateUser().getUserId())
                    .param("template_trip_type_id", packingList.getTemplateTripType().getTripTypeId())
                    .update(keyHolder, "template_id");

        if (rowsAffected <= 0) {
            return null;
        }
        packingList.setTemplateId(keyHolder.getKey().intValue());
        return packingList;
    }
}
