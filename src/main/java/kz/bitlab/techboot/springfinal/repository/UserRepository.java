package kz.bitlab.techboot.springfinal.repository;

import jakarta.transaction.Transactional;
import kz.bitlab.techboot.springfinal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByFullName(String fullName);
}
