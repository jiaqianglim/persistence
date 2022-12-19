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
     * matchingAny(String... words) - match any of the given words, bag of words
     * matchingPhrase(String phrase) - match a phrase
     * notMatchingAny(String... words) - not matching any of the given words
     * notMatchingPhrase(String phrase) - do not match the phrase
     */

    public List<Document> getPHObjBySearchTerm(TextCriteria textCriteria) {
        // TextCriteria textCriteria =
        // TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm).caseSensitive(true);
        TextQuery query = TextQuery.queryText(textCriteria);
        List<Document> results = mongoTemp.find(query, Document.class, "collectionname");
        return results;
    }

    public List<Document> getPHObjBySearchTermSortByScore(TextCriteria textCriteria) {
        // TextCriteria textCriteria =
        // TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm).caseSensitive(true);
        TextQuery query = TextQuery.queryText(textCriteria).sortByScore();
        query.setScoreFieldName("score");
        List<Document> results = mongoTemp.find(query, Document.class, "collectionname");
        return results;
    }

    public TextCriteria createCriteriaBySearchTerm(String searchTerm) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingPhrase(searchTerm);
        return textCriteria;
    }

    public TextCriteria createCriteriaBySearchString(String searchString) {
        StringBuilder matchesSB = new StringBuilder();
        StringBuilder notMatchSB = new StringBuilder();
        for (String word : searchString.split(",")) {
            String w = word.trim();
            if (w.startsWith("-")) {
                notMatchSB.append(" ");
                notMatchSB.append(w.substring(-1));
            } else {
                matchesSB.append(" ");
                matchesSB.append(w);
            }
        }
        String matches = matchesSB.toString();
        String notMatch = notMatchSB.toString();

        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(matches).notMatchingAny(notMatch);

        return textCriteria;
    }
}
