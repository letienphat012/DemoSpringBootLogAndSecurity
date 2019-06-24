package tma.tft.phat.ss.repositories;

import org.springframework.data.repository.CrudRepository;

import tma.tft.phat.ss.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByName(String name);
}
