package learn.data;

import learn.data.mappers.TripTypeMapper;
import learn.models.TripType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripTypeJdbcClientRepository implements TripTypeRepository {
    private final String SELECT = """
            select trip_type_id, trip_type_name, trip_type_description
            from trip_types
            """;
    private final JdbcClient jdbcClient;

    public TripTypeJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public TripType findById(int id) {
        final String sql = SELECT + " where trip_type_id = ?;";
        return jdbcClient.sql(sql)
                .param(id)
                .query(new TripTypeMapper())
                .optional().orElse(null);
    }

    @Override
    public List<TripType> findAllByUserId(int userId) {
        final String sql = """
                select distinct
                    tt.trip_type_id,
                    tt.trip_type_name,
                    tt.trip_type_description
                from trip_types tt
                join templates t on tt.trip_type_id = t.template_trip_type_id
                where t.template_user_id = ?;
                """;
        return jdbcClient.sql(sql)
                .param(userId)
                .query(new TripTypeMapper())
                .list();
    }
}
