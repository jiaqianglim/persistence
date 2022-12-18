package vttp.persistence.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MONGODBRepository {

    @Autowired
    private MongoTemplate mongoTemp;

    public List<Document> getAll(){
        return null;
    }
    
}
