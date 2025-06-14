package com.codewithfavour.data.repository;

import com.codewithfavour.data.model.BorrowingRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BorrowingRecordRepository extends MongoRepository<BorrowingRecord, String> {
}
