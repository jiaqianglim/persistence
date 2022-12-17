package vttp.persistence.restcontroller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.persistence.model.MYSQLObject;
import vttp.persistence.service.MYSQLService;

@RestController
@RequestMapping(path = "/mysql", produces = MediaType.APPLICATION_JSON_VALUE)
public class MYSQLController {
    @Autowired
    private MYSQLService mysqlSvc;

    @GetMapping
    public ResponseEntity<String> getAllSQLObjects(
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "offset") int offset) {
        List<MYSQLObject> results = mysqlSvc.getAllSQLObject(limit, offset);
        List<JsonObject> jsonResults = results.stream().map(s -> s.toJson()).toList();
        return ResponseEntity.ok(jsonResults.toString());
    }

    @GetMapping("{id}")
    public ResponseEntity<String> getSQLModelById(@PathVariable String id) {
        Optional<MYSQLObject> opt = mysqlSvc.getSQLObjectById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(404)
                    .body(Json.createObjectBuilder().add("message", "Cannot find " + id).build().toString());
        return ResponseEntity.ok(opt.get().toJson().toString());
    }
}
