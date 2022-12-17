package vttp.persistence.model;

import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class MYSQLObject {

    private String id;
    private String name;

    public static MYSQLObject fromSqlRS(SqlRowSet rs) {
        final MYSQLObject mysqlObject = new MYSQLObject();
        mysqlObject.setId(rs.getString("id"));
        mysqlObject.setName(rs.getString("name"));
        return mysqlObject;
    }

    public JsonObject toJson() {
        JsonObject MYSQLObjectJson = Json.createObjectBuilder().add("id", id).add("name", name).build();
        return MYSQLObjectJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
