package vttp.persistence.repository;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import vttp.persistence.model.PHObj;

@Repository
public class MONGODB_CUD_Repository {
    @Autowired
    private MongoTemplate mongoTemp;

    // CREATE

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

    // UPDATE

    public void updatePHObj(String key1, String value1) {
        Criteria criteria = Criteria.where(key1).is(value1);
        Query query = Query.query(criteria);
        Update update = new Update()
                .set("key2", "value2")
                .inc("key3", 1)
                .unset("value4");

        UpdateResult updateResult = mongoTemp.updateMulti(query, update, Document.class, "collectionname");

        System.out.printf("%s documents updated", updateResult.getModifiedCount());
    }

    public void updatePushPopPHObj(String key1, String value1) {
        Criteria criteria = Criteria.where(key1).is(value1);
        Query query = Query.query(criteria);
        Update update = new Update()
                .push("key2").each("null")
                .pop("key3", Update.Position.FIRST);
        UpdateResult updateResult = mongoTemp.updateMulti(query, update, Document.class, "collectionname");
        // mongoTemp.upsert(query, update, Document.class, "collectionname");
        System.out.printf("%s documents updated", updateResult.getModifiedCount());
    }

    // DELETE

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
