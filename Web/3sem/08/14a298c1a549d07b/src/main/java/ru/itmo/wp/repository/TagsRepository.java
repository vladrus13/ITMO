package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.wp.domain.Tag;

public interface TagsRepository extends JpaRepository<Tag, Long> {
    @Query(value = "SELECT * FROM tag WHERE name=?1", nativeQuery = true)
    Tag findByName(String name);
}
