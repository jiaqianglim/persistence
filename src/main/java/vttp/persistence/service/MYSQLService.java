package vttp.persistence.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.persistence.model.MYSQLObject;
import vttp.persistence.repository.MYSQLRepository;

@Service
public class MYSQLService {
    @Autowired
    private MYSQLRepository mysqlRepo;

    public List<MYSQLObject> getAllSQLObject(int limit, int offset) {
        List<MYSQLObject> results = mysqlRepo.getMYSQLObjects(limit, offset);
        return results;
    }

    public Optional<MYSQLObject> getSQLObjectById(final String id) {
        MYSQLObject results = mysqlRepo.getMYSQLObjectById(id);
        if (results != null)
            return Optional.of(results);
        return Optional.empty();
    }
}
