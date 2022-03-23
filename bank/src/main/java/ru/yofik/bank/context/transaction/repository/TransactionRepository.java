package ru.yofik.bank.context.transaction.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.bank.context.transaction.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {
}
