package com.challenge.technicalChallenge.repository;

import com.challenge.technicalChallenge.model.CallHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallHistoryRepository extends JpaRepository<CallHistory, Long> {
}
