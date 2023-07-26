package kz.bitlab.techboot.springfinal.repository;


import jakarta.transaction.Transactional;
import kz.bitlab.techboot.springfinal.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BlogRepository extends JpaRepository<Blog, Long> {
}
