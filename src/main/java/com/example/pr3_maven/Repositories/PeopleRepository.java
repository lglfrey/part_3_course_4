package com.example.pr3_maven.Repositories;
import com.example.pr3_maven.Models.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends CrudRepository<People, Long> {
}

