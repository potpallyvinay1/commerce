package com.commerce.fashion.Repository;

import com.commerce.fashion.entity.SignInCreds;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignInRepository extends MongoRepository<SignInCreds, String> {
    SignInCreds findByMailId(String mailId);

}
