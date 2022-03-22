package ru.yofik.kickstoper.context.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yofik.kickstoper.context.application.entity.FinanceData;

@Repository
public interface FinanceDataRepository extends JpaRepository<FinanceData, Integer> {
}
