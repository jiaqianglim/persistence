package vttp.persistence.repository;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import vttp.persistence.model.PHObj;

@Repository
public class MONGODB_CUD_Repository {
    @Autowired
    private MongoTemplate mongoTemp;

    public Document insertPHObj(PHObj phObj) {
        Document docToInsert = phObj.toBsonDoc();
        Document newDoc = mongoTemp.insert(docToInsert, "collectionname");
        return newDoc;
    }

    public ObjectId insertPHObjReturnObjectId(PHObj phObj) {
        Document docToInsert = phObj.toBsonDoc();
        Document newDoc = mongoTemp.insert(docToInsert, "collectionname");
        ObjectId id = newDoc.getObjectId("_id");
        return id;
    }

    public List<Document> insertListOfPHObj(List<PHObj> listObj) {
        List<Document> docsToInsert = listObj.stream().map(s -> s.toBsonDoc()).toList();
        List<Document> newDocs = mongoTemp.insert(docsToInsert, "collectionname").stream().toList();
        return newDocs;
    }

    public void delete(Criteria criteria) {
        Query query = Query.query(criteria);
        DeleteResult result = mongoTemp.remove(query, "collectionname");
        System.out.println("Deleted " + result.getDeletedCount() + " documents");
    }

    public Document deleteAndReturnDoc(Criteria criteria) {
        Query query = Query.query(criteria);
        return mongoTemp.findAndRemove(query, Document.class, "collectionname");
    }

}
