package vttp.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MONGODBRepository {

    @Autowired
    private MongoTemplate mongoTemp;

    private Criteria getKeyExists = Criteria.where("key1").exists(true);
    private Criteria getKeyDoesNotExist = Criteria.where("key1").exists(false);

    private Criteria getByCriteria = Criteria.where("key").is("value");
    private Criteria getByMultipleCriteria = Criteria.where("key1").is("value1").and("key2").is("value2");
    private Criteria getByCriteriaAndRegex = new Criteria().andOperator(Criteria.where("key1").is("value1"),
            Criteria.where("key2").regex("expression"));
    // MATCH 1 OR MORE IN LIST
    private Criteria getByCriteriaInList = Criteria.where("key1").in(List.of("value1", "value2"));
    // Match ALL IN LIST
    private Criteria getByCriteriaAllList = Criteria.where("key1").all(List.of("value1", "value2"));

    public List<Document> getAllWithoutCriteria() {
        Criteria criteria = new Criteria();
        Query query = Query.query(criteria);
        List<Document> results = mongoTemp.find(query, Document.class, "collectionname");
        return results;
    }

    public List<Document> getByCriteria(Criteria criteria) {
        Query query = Query.query(criteria);
        List<Document> results = mongoTemp.find(query, Document.class, "collectionname");
        return results;
    }

    public Optional<Document> getPHObjById(String id) {
        ObjectId oId = new ObjectId(id);
        return Optional.ofNullable(mongoTemp.findById(oId, Document.class, "collectionname"));
    }

    public List<String> getDistinctValues(String key1) {
        Query query = new Query();
        List<String> results = mongoTemp.findDistinct(query, key1, "collectionname", String.class);
        return results;
    }

    public long getCount(Criteria criteria) {
        Query query = Query.query(criteria);
        long count = mongoTemp.count(query, "collectionname");
        return count;

    }

    public List<Document> getWithPagination(Criteria criteria, int limit, int offset) {
        Query query = Query.query(criteria).skip(offset).limit(limit);
        return mongoTemp.find(query, Document.class, "collectionname");
    }

    public List<Document> getWithPaginationAndSort(Criteria criteria, int limit, int offset, String sortCriteria) {
        Query query = Query.query(criteria).with(Sort.by(Sort.Direction.ASC, sortCriteria)).skip(offset).limit(limit);
        return mongoTemp.find(query, Document.class, "collectionname");
    }

}
