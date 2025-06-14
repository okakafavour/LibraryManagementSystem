package com.codewithfavour.service;

import com.codewithfavour.data.model.BorrowingRecord;
import com.codewithfavour.data.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Override
    public List<BorrowingRecord> viewAllBorrowingHistory() {
        return borrowingRecordRepository.findAll();
    }
}
