package com.example.pr3_maven.Repositories;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
public interface UniversalRepository {
    Optional<?> findEntityById(String modelName, Long id);
    void saveEntity(String modelName, Object entity);
    <T> void updateEntity(T entity, Map<String, String> fieldValues);
    List<?> searchEntities(String modelName, String fieldName, String fieldValue);
    void deleteEntityById(String modelName, Long id);
}
