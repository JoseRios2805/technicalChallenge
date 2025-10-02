package com.challenge.technicalChallenge.service;

import com.challenge.technicalChallenge.model.CallHistory;
import com.challenge.technicalChallenge.repository.CallHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final CallHistoryRepository callHistoryRepository;

    @Async
    public void saveAsync(CallHistory callHistory) {
        callHistoryRepository.save(callHistory);
    }
}
