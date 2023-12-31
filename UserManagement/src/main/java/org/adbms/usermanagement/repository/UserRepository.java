package org.adbms.usermanagement.repository;

import org.adbms.usermanagement.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(String email);

    Boolean deleteUserByEmail(String email);
}
