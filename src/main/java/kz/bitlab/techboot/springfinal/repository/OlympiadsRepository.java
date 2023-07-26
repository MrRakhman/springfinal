package kz.bitlab.techboot.springfinal.repository;


import kz.bitlab.techboot.springfinal.model.Olympiads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OlympiadsRepository extends JpaRepository<Olympiads, Long> {
}
