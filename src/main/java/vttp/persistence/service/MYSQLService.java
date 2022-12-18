package vttp.persistence.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.persistence.model.PHObj;
import vttp.persistence.repository.MYSQLRepository;

@Service
public class MYSQLService {
    @Autowired
    private MYSQLRepository mysqlRepo;

    public List<PHObj> getAllphObj(int limit, int offset) {
        List<PHObj> results = mysqlRepo.getPHObjs(limit, offset);
        return results;
    }

    public Optional<PHObj> getphObjById(final String id) {
        PHObj results = mysqlRepo.getPHObjById(id);
        if (results != null)
            return Optional.of(results);
        return Optional.empty();
    }
}
