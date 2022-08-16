package com.backend.Coffee.repository;

import com.backend.Coffee.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    Boolean existsByUserName(String username);
    Boolean existsByEmail(String email);

}
