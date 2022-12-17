package vttp.persistence.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class MYSQLTransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemp;

    public boolean withdraw(String acctId, double amount) {
        final int rowCount = jdbcTemp.update("update account set balance = balance - ? where acct_id = ?", amount,
                acctId);
        return rowCount > 0;
    }

    public boolean deposit(String acctId, double amount) {
        final int rowCount = jdbcTemp.update("update account set balance = balance + ? where acct_id = ?", amount,
                acctId);
        return rowCount > 0;
    }

    public Optional<Double> getBalance(String acctId) {
        final SqlRowSet rs = jdbcTemp.queryForRowSet("select balance from account where acct_id = ?", acctId);
        return Optional.ofNullable(rs.next() ? rs.getDouble("balance") : null);
    }

}
