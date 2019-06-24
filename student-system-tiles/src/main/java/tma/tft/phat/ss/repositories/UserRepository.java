package tma.tft.phat.ss.repositories;

import org.springframework.data.repository.CrudRepository;

import tma.tft.phat.ss.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
}
