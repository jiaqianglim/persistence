package vttp.persistence.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

    private final String primaryKey;
    private final String tableName;

    public RecordNotFoundException(String table, String primaryKey) {
        super(String.format("Cannot find record in %s table with primary key %s", table, primaryKey));
        this.primaryKey = primaryKey;
        this.tableName = table;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getTableName() {
        return tableName;
    }

}
