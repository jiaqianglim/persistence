package vttp.persistence.repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.persistence.model.MYSQLObject;

@Repository
public class MYSQLRepository {
    @Autowired
    private JdbcTemplate jdbcTemp;

    public List<MYSQLObject> getMYSQLObjects(final int limit, final int offset) {
        final List<MYSQLObject> result = new LinkedList<>();
        final SqlRowSet rs = jdbcTemp.queryForRowSet("select * from databasename limit ? offset ?", limit, offset);

        while (rs.next()) {
            MYSQLObject mysqlObject = MYSQLObject.fromSqlRS(rs);
            result.add(mysqlObject);
        }
        return (Collections.unmodifiableList(result));
    }

    public MYSQLObject getMYSQLObjectById(final String id) {
        final SqlRowSet rs = jdbcTemp.queryForRowSet("select * from databasename where id = ?", id);
        while (rs.next()) {
            MYSQLObject mysqlObject = MYSQLObject.fromSqlRS(rs);
            return mysqlObject;
        }
        return null;
    }

    public boolean add(final MYSQLObject obj) {
        int added = jdbcTemp.update("insert into databasename(id,name) values(?,?)", obj.getId(), obj.getName());
        return added > 0;
    }

    public int[] addList(final List<MYSQLObject> lsObj) {
        List<Object[]> params = lsObj.stream().map(obj -> new Object[] { obj.getId(), obj.getName() })
                .collect(Collectors.toList());
        int[] added = jdbcTemp.batchUpdate("insert into databasename(id,name_ values(?,?)", params);
        return added;
    }

    public boolean update(final MYSQLObject obj) {
        int updated = jdbcTemp.update("update databasename set name = '?' where id = ?", obj.getName(), obj.getId());
        return updated > 0;
    }

    public boolean delete(final MYSQLObject obj) {
        int deleted = jdbcTemp.update("delete from databasename where ? = ?", "COLUMN_NAME", obj.getId());
        return deleted > 0;
    }
}
