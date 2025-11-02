package com.my_finance.my_finance.Repository;

import com.my_finance.my_finance.Entity.SyncLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SyncLogRepository extends JpaRepository<SyncLog, Integer> {
    List<SyncLog> findByUserUserId(Integer userId);
}
