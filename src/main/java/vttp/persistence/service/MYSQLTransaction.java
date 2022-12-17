package vttp.persistence.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.persistence.repository.MYSQLTransactionRepository;

@Service
public class MYSQLTransaction {

    @Autowired
    MYSQLTransactionRepository transRepo;

    @Transactional
    public void transfer(String fromAcct, String toAcct, double amount) throws IllegalAccessException {
        final Optional<Double> optFrom = transRepo.getBalance(fromAcct);
        final Optional<Double> optTo = transRepo.getBalance(toAcct);

        if (optFrom.isEmpty() || optTo.isEmpty() || (optFrom.get() < amount))
            throw new IllegalArgumentException("Incorrect parameters");

        if (!transRepo.withdraw(fromAcct, amount) || !transRepo.deposit(toAcct, amount))
            throw new IllegalAccessException("Cannot perform transfer");
    }

}
