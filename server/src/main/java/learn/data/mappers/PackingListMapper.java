package learn.data.mappers;

import learn.models.PackingList;
import learn.models.TripType;
import learn.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PackingListMapper implements RowMapper<PackingList> {
    private UserMapper userMapper = new UserMapper();
    private TripTypeMapper tripTypeMapper = new TripTypeMapper();
    @Override
    public PackingList mapRow(ResultSet rs, int rowNum) throws SQLException {
        PackingList packingList = new PackingList();
        User user = userMapper.mapRow(rs, rowNum);
        TripType tripType = tripTypeMapper.mapRow(rs, rowNum);

        packingList.setTemplateId(rs.getInt("template_id"));
        packingList.setTemplateName(rs.getString("template_name"));
        packingList.setTemplateDescription(rs.getString("template_description"));
        packingList.setReusable(rs.getBoolean("template_reusable"));
        packingList.setTemplateTripType(tripType);
        packingList.setTemplateUser(user);

        return packingList;
    }
}
