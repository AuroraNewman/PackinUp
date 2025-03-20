package learn.data;

import learn.models.Template;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateJdbcRepository implements TemplateRepository{
    private final String SELECT = """
            select
                t.template_id,
                t.template_name,
                t.template_description,
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

    public TemplateJdbcRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Template findById(int templateId) {
        return null;
    }

    @Override
    public Template create(Template template) {
        int rowsAffected = 0;
        final String sql = """
                insert into templates(template_name, template_description, template_trip_type_id, template_user_id)
                values(:template_name, :template_description, :template_trip_type_id, :template_user_id);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        rowsAffected = jdbcClient.sql(sql)
                .param("template_name", template.getTemplateName())
                .param("template_description", template.getTemplateDescription())
                .param("template_user_id", template.getTemplateUser().getUserId())
                .param("template_trip_type_id", template.getTemplateTripType().getTripTypeId())
                .update(keyHolder, "template_id");
        if (rowsAffected <= 0) {
            return null;
        }
        template.setTemplateId(keyHolder.getKey().intValue());
        return null;
    }
}
