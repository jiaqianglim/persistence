package vttp.persistence.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

@Repository
public class MONGODB_TEXT_Repository {
    @Autowired
    private MongoTemplate mongoTemp;

    /*
    matchingAny(String... words) - match any of the given words, bag of words
    matchingPhrase(String phrase) - match a phrase
    notMatchingAny(String... words) - not matching any of the given words
    notMatchingPhrase(String phrase) - do not match the phrase
     */

    public List<Document> getPHObjBySearchTerm(String searchTerm){
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm);
        // TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm).caseSensitive(true);
        TextQuery query = TextQuery.queryText(textCriteria);
        List<Document> results = mongoTemp.find(query, Document.class, "collectionname");
        return results;
    }

    public List<Document> getPHObjBySearchString(String searchString){
        return null;
    }
}
