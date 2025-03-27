package learn.data;

import learn.data.mappers.TemplateMapper;
import learn.models.Template;
import learn.models.TemplateItem;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TemplateJdbcClientRepository implements TemplateRepository{
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

    public TemplateJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Template findByName(String templateName) {
        final String sql = SELECT + " where t.template_name = ?;";
        return jdbcClient.sql(sql)
                .param(templateName)
                .query(new TemplateMapper())
                .optional().orElse(null);
    }

    @Override
    public Template findById(int templateId) {
        final String sql = SELECT + " where t.template_id = ?;";
        return jdbcClient.sql(sql)
                .param(templateId)
                .query(new TemplateMapper())
                .optional().orElse(null);
    }

    @Override
    public List<Template> findByUserId(int userId) {
        List<Template> templates = new ArrayList<>();
        final String sql = """
                select t.template_id
                from templates t
                where t.template_user_id = ?;
                """;
        List<Integer> templateIndices = jdbcClient.sql(sql)
                .param(userId)
                .query(Integer.class)
                .list();
        for (Integer i : templateIndices){
            templates.add(findById(i));
        }
        return templates;
    }

    @Override
    public Template create(Template template) throws DuplicateKeyException{
        final String sql = """
                insert into templates(template_name,
                 template_description, template_trip_type_id, template_user_id)
                values(:template_name, :template_description, :template_trip_type_id, :template_user_id);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcClient.sql(sql)
                    .param("template_name", template.getTemplateName())
                    .param("template_description", template.getTemplateDescription())
                    .param("template_user_id", template.getTemplateUser().getUserId())
                    .param("template_trip_type_id", template.getTemplateTripType().getTripTypeId())
                    .update(keyHolder, "template_id");

        if (rowsAffected <= 0) {
            return null;
        }
        template.setTemplateId(keyHolder.getKey().intValue());
        return template;
    }

    @Override
    public boolean update(Template template) {
        final String sql = """
                update templates
                set template_name = :template_name,
                    template_description = :template_description,
                    template_trip_type_id = :template_trip_type_id,
                    template_user_id = :template_user_id
                where template_id = :template_id;
                """;
        return jdbcClient.sql(sql)
                .param("template_name", template.getTemplateName())
                .param("template_description", template.getTemplateDescription())
                .param("template_trip_type_id", template.getTemplateTripType().getTripTypeId())
                .param("template_user_id", template.getTemplateUser().getUserId())
                .param("template_id", template.getTemplateId())
                .update() > 0;
    }


    @Override
    public boolean deleteById(int templateId) {
        final String sql = """
                delete from templates
                where template_id = ?;
                """;
        int rowsAffected = jdbcClient.sql(sql)
                .param(templateId)
                .update();
        return rowsAffected > 0;
    }

}
