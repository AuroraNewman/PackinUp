package learn.data.mappers;

import learn.models.TripType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripTypeMapper implements RowMapper {
    @Override
    public TripType mapRow(ResultSet rs, int rowNum) throws SQLException {
        TripType tripType = new TripType();

        tripType.setTripTypeId(rs.getInt("trip_type_id"));
        tripType.setTripTypeName(rs.getString("trip_type_name"));
        tripType.setTripTypeDescription(rs.getString("trip_type_description"));

        return tripType;
    }
}
