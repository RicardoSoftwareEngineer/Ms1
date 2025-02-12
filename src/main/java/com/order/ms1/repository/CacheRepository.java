package com.order.ms1.repository;


import com.order.ms1.entity.CacheEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongoCacheRepository")
public interface CacheRepository extends MongoRepository<CacheEntity, String> {
    CacheEntity findByDocument(String document);
}
