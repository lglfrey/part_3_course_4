package com.example.pr3_maven.Repositories;

import com.example.pr3_maven.Models.Book;
import com.example.pr3_maven.Models.Car;
import com.example.pr3_maven.Models.Game;
import com.example.pr3_maven.Models.People;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class UniversalRepositoryImpl implements UniversalRepository{
    private final BookRepository bookRepository;
    private final CarRepository carRepository;
    private final GameRepository gameRepository;
    private final PeopleRepository peopleRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UniversalRepositoryImpl(BookRepository bookRepository, CarRepository carRepository, GameRepository gameRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.carRepository = carRepository;
        this.gameRepository = gameRepository;
        this.peopleRepository = peopleRepository;
    }
    @Override
    public Optional<?> findEntityById(String modelName, Long id) {
        return switch (modelName) {
            case "Book" -> bookRepository.findById(id);
            case "Car" -> carRepository.findById(id);
            case "Game" -> gameRepository.findById(id);
            case "People" -> peopleRepository.findById(id);
            default -> Optional.empty();
        };
    }

    @Override
    public void saveEntity(String modelName, Object entity) {
        switch (modelName) {
            case "Book":
                bookRepository.save((Book) entity);
                break;
            case "Car":
                carRepository.save((Car) entity);
                break;
            case "Game":
                gameRepository.save((Game) entity);
                break;
            case "People":
                peopleRepository.save((People) entity);
                break;
            default:
                throw new IllegalArgumentException("Invalid model name");
        }
    }

    @Override
    public <T> void updateEntity(T entity, Map<String, String> fieldValues) {
        Class<?> entityClass = entity.getClass();

        for (Field field : entityClass.getDeclaredFields()) {
            String fieldName = field.getName();

            if (fieldValues.containsKey(fieldName)) {
                String fieldValue = fieldValues.get(fieldName);

                try {
                    String setterMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class<?> fieldClass = field.getType();
                    Class<?>[] paramTypes = new Class<?>[] { fieldClass };
                    java.lang.reflect.Method setterMethod = entityClass.getMethod(setterMethodName, paramTypes);

                    Object convertedValue = convertStringToType(fieldClass, fieldValue);

                    setterMethod.invoke(entity, convertedValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<?> searchEntities(String modelName, String fieldName, String fieldValue) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<?> query = criteriaBuilder.createQuery();

        Root<?> root = query.from(getEntityClassByName(modelName));

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(fieldValue)) {
            Path<String> fieldPath = root.get(fieldName);
            predicates.add(criteriaBuilder.equal(fieldPath, fieldValue));
        }

        query.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
    private Class<?> getEntityClassByName(String modelName) {
        try {
            return Class.forName("com.example.pr3_maven.Models." + modelName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Invalid model name: " + modelName);
        }
    }
    @Override
    public void deleteEntityById(String modelName, Long id) {
        switch (modelName) {
            case "Book" -> bookRepository.deleteById(id);
            case "Car" -> carRepository.deleteById(id);
            case "Game" -> gameRepository.deleteById(id);
            case "People" -> peopleRepository.deleteById(id);
            default -> throw new IllegalArgumentException("Invalid model name");
        }
    }

    private Object convertStringToType(Class<?> targetType, String value) {
        if (targetType == String.class) {
            return value;
        } else if (targetType == Integer.class || targetType == int.class) {
            return Integer.parseInt(value);
        } else if (targetType == Long.class || targetType == long.class) {
            return Long.parseLong(value);
        }
        return null;
    }
}
