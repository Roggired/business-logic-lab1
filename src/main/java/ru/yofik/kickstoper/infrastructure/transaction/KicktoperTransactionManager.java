package ru.yofik.kickstoper.infrastructure.transaction;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.jta.JtaTransactionManager;
import ru.yofik.kickstoper.api.exceptions.InternalServerException;

import javax.transaction.*;

@Component
@Log4j2
public class KicktoperTransactionManager {
    @Autowired
    private JtaTransactionManager jtaTransactionManager;


    public void begin() {
        try {
            jtaTransactionManager.getTransactionManager().begin();
        } catch (NullPointerException | NotSupportedException | SystemException e) {
            log.fatal(() -> "Unexpected error while beginning transactions", e);
            throw new InternalServerException();
        }
    }

    public void commit() {
        try {
            jtaTransactionManager.getTransactionManager().commit();
        } catch (NullPointerException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException e) {
            log.fatal(() -> "Unexpected error while committing transactions", e);

            try {
                jtaTransactionManager.getTransactionManager().rollback();
            } catch (SystemException ex) {
                log.fatal(() -> "Can't rollback after failed commit", ex);
                throw new InternalServerException();
            }

            throw new InternalServerException();
        }
    }

    public void rollback() {
        try {
            jtaTransactionManager.getTransactionManager().rollback();
        } catch (NullPointerException | SystemException e) {
            log.fatal(() -> "Unexpected error while rolling back", e);
            throw new InternalServerException();
        }
    }
}
