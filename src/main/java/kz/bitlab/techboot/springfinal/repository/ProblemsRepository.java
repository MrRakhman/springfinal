package kz.bitlab.techboot.springfinal.repository;


import kz.bitlab.techboot.springfinal.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProblemsRepository extends JpaRepository<Problem, Long> {
}
