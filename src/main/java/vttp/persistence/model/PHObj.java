package vttp.persistence.model;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class PHObj {

    private String id;
    private String name;

    public static PHObj fromSqlRS(SqlRowSet rs) {
        final PHObj phObj = new PHObj();
        phObj.setId(rs.getString("id"));
        phObj.setName(rs.getString("name"));
        return phObj;
    }

    public JsonObject toJson() {
        JsonObject MYSQLObjectJson = Json.createObjectBuilder().add("id", id).add("name", name).build();
        return MYSQLObjectJson;
    }

    public Document toBsonDoc() {
        Document doc = new Document("_id", new ObjectId());
        doc.append("id", id);
        doc.append("name", name);
        return doc;
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
