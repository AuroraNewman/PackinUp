package learn.data.mappers;

import learn.models.Template;
import learn.models.TripType;
import learn.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TemplateMapper implements RowMapper<Template> {
    private UserMapper userMapper = new UserMapper();
    private TripTypeMapper tripTypeMapper = new TripTypeMapper();
    @Override
    public Template mapRow(ResultSet rs, int rowNum) throws SQLException {
        Template template = new Template();
        User user = userMapper.mapRow(rs, rowNum);
        TripType tripType = tripTypeMapper.mapRow(rs, rowNum);

        template.setTemplateId(rs.getInt("template_id"));
        template.setTemplateName(rs.getString("template_name"));
        template.setTemplateDescription(rs.getString("template_description"));
        template.setTemplateTripType(tripType);
        template.setTemplateUser(user);

        return template;
    }
}
