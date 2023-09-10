package com.example.pr3_maven.Controllers;

import com.example.pr3_maven.Models.Book;
import com.example.pr3_maven.Models.Car;
import com.example.pr3_maven.Models.Game;
import com.example.pr3_maven.Models.People;
import com.example.pr3_maven.Repositories.*;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CRUDController {
    private final BookRepository bookRepository;
    private final CarRepository carRepository;
    private final GameRepository gameRepository;
    private final PeopleRepository peopleRepository;
    private final UniversalRepository universalRepository;


    public CRUDController(UniversalRepository universalRepository, BookRepository bookRepository, CarRepository carRepository, GameRepository gameRepository, PeopleRepository peopleRepository) {
        this.universalRepository = universalRepository;
        this.bookRepository = bookRepository;
        this.carRepository = carRepository;
        this.gameRepository = gameRepository;
        this.peopleRepository = peopleRepository;
    }


    @GetMapping("/{ModelName}/Edit/{id}")
    public String Redact(
            @PathVariable(name = "ModelName") String modelName,
            @RequestParam(name = "fieldNameList") List<String> fieldNameList,
            @RequestParam Map<String, String> requestParams,
            Model model, @PathVariable String id) {
        Map<String, String> cleanedRequestParams = cleanRequestParamKeys(requestParams);

        Optional<?> optionalEntity = universalRepository.findEntityById(modelName, Long.parseLong(id));
        if (optionalEntity.isPresent()) {
            Object entity = optionalEntity.get();
            updateEntityFields(entity, cleanedRequestParams);

            universalRepository.saveEntity(modelName, entity);
            model.addAttribute("ModelName", modelName);
            model.addAttribute("val", cleanedRequestParams.values());
            model.addAttribute("fieldNameList", fieldNameList.stream().map(this::cleanFieldName).collect(Collectors.toList()));
            return "ModelDetail";
        } else {
            return "redirect:/"; // Entity not found
        }
    }

   @GetMapping("/{ModelName}/Edit/Submit")
   public String RedactSub(
    @PathVariable (name = "ModelName") String modelName,
    @RequestParam (name = "0", defaultValue = "") String val0,
    @RequestParam (name = "1", defaultValue = "") String val1,
    @RequestParam (name = "2", defaultValue = "") String val2,
    @RequestParam (name = "3", defaultValue = "") String val3,
    @RequestParam (name = "4", defaultValue = "") String val4,
    @RequestParam (name = "5", defaultValue = "") String val5,
    @RequestParam (name = "6", defaultValue = "") String val6,
    @RequestParam (name = "7", defaultValue = "") String val7,
    Model model){
        String[] val = new String[8];
        val[0] = val0;
        val[1] = val1;
        val[2] = val2;
        val[3] = val3;
        val[4] = val4;
        val[5] = val5;
        val[6] = val6;
        val[7] = val7;
        model.addAttribute("val",val);
        switch (modelName) {
            case "Book":
                Book entityToUpdate1 = new Book();
                entityToUpdate1.setId(Long.parseLong(val[0]));
                entityToUpdate1.setAuthor(val[1]);
                entityToUpdate1.setTitle(val[2]);
                entityToUpdate1.setYear(val[3]);
                bookRepository.save(entityToUpdate1);
                break;
            case "Car":
                Car entityToUpdate2 = new Car();
                entityToUpdate2.setId(Long.parseLong(val[0]));
                entityToUpdate2.setMake(val[1]);
                entityToUpdate2.setModel(val[2]);
                entityToUpdate2.setYear(val[3]);
                carRepository.save(entityToUpdate2);
                break;
            case "Game":
                Game entityToUpdate3 = new Game();
                entityToUpdate3.setId(Long.parseLong(val[0]));
                entityToUpdate3.setTitle (val[1]);
                entityToUpdate3.setGenre(val[2]);
                entityToUpdate3.setYear(val[3]);
                gameRepository.save(entityToUpdate3);
                break;
            case "People":
                People entityToUpdate4 = new People();
                entityToUpdate4.setId(Long.parseLong(val[0]));
                entityToUpdate4.setFirstName (val[1]);
                entityToUpdate4.setLastName(val[2]);
                entityToUpdate4.setMiddleName(val[3]);
                entityToUpdate4.setAge(Integer.parseInt(val[4]));
                entityToUpdate4.setGender(val[5]);
                entityToUpdate4.setOccupation(val[6]);
                peopleRepository.save(entityToUpdate4);
                break;
            default:
                return null;
        }
        return "redirect:/";
    }
    /*public String RedactSub(
            @PathVariable(name = "ModelName") String modelName,
            @RequestParam Map<String, String> requestParams,
            Model model) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> entityClass = Class.forName("com.example.pr3_maven.Models." + modelName);

        Long entityId = Long.parseLong(requestParams.get("0"));

        Object repository = getRepositoryForModel(modelName);

        if (repository != null) {
            Optional<?> optionalEntity = universalRepository.findEntityById(modelName, entityId);

            if (optionalEntity.isPresent()) {
                Object entityToUpdate = optionalEntity.get();

                universalRepository.updateEntity(entityToUpdate, requestParams);

                universalRepository.saveEntity(modelName, entityToUpdate);

                return "redirect:/";
            } else {
                return "redirect:/";
            }
        } else {
            return "redirect:/";
        }
    } */


    @GetMapping("/{ModelName}/Create")
    public String Create(
            @PathVariable(name = "ModelName") String modelName,
            @RequestParam(name = "fieldNameList") List<String> fieldNameList,
            Model model) {
        model.addAttribute("ModelName",modelName);
        List<String> cleanedFieldNameList = new ArrayList<>();
        int listSize = fieldNameList.size();
        for (int i = 0; i < listSize; i++) {
            String fieldName = fieldNameList.get(i);
            if (i == 0) {
                if (fieldName.length() > 0) {
                    fieldName = fieldName.substring(1);
                }
            } else if (i == listSize - 1) {
                if (fieldName.length() > 0) {
                    fieldName = fieldName.substring(0, fieldName.length() - 1);
                }
            }
            cleanedFieldNameList.add(fieldName);
        }
        model.addAttribute("fieldNameList", cleanedFieldNameList.stream().toList());
        return "ModelDetail";
    }

    @PostMapping("/{ModelName}/Create/Submit")
    public String CreateSub(
            @PathVariable(name = "ModelName") String modelName,
            @RequestParam Map<String, String> requestParams) {
        Map<String, String> cleanedRequestParams = cleanRequestParamKeys(requestParams);
        Object entity = createEntityFromParams(modelName, cleanedRequestParams);
        universalRepository.saveEntity(modelName, entity);
        return "redirect:/";
    }

    @GetMapping("/{ModelName}/Delete/{id}")
    public String Delete(
            @PathVariable(name = "ModelName") String modelName,
            @PathVariable(name = "id") Long id) {
        universalRepository.deleteEntityById(modelName, id);
        return "redirect:/";
    }

    private String cleanFieldName(String fieldName) {
        if (fieldName.length() > 0 && fieldName.charAt(0) == '[') {
            fieldName = fieldName.substring(1);
        }
        if (fieldName.length() > 0 && fieldName.charAt(fieldName.length() - 1) == ']') {
            fieldName = fieldName.substring(0, fieldName.length() - 1);
        }
        return fieldName;
    }

    private Map<String, String> cleanRequestParamKeys(Map<String, String> requestParams) {
        return requestParams.entrySet().stream()
                .filter(entry -> entry.getKey().matches("\\d+"))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
    private Object createEntityFromParams(String modelName, Map<String, String> fieldValues) {
        try {
            Class<?> entityClass = Class.forName("com.example.pr3_maven.Models." + modelName);
            Object entity = entityClass.getDeclaredConstructor().newInstance();

            for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
                String fieldName = "field" + entry.getKey();
                Field field = entityClass.getDeclaredField(fieldName);
                field.setAccessible(true);

                Class<?> fieldType = field.getType();
                if (fieldType == Long.class) {
                    field.set(entity, Long.parseLong(entry.getValue()));
                } else if (fieldType == Integer.class) {
                    field.set(entity, Integer.parseInt(entry.getValue()));
                } else {
                    field.set(entity, entry.getValue());
                }
            }

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void updateEntityFields(Object entity, Map<String, String> fieldValues) {
        Class<?> entityClass = entity.getClass();
        for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
            String fieldName = "field" + entry.getKey();
            try {
                var field = entityClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                if (field.getType() == Long.class) {
                    field.set(entity, Long.parseLong(entry.getValue()));
                } else if (field.getType() == Integer.class) {
                    field.set(entity, Integer.parseInt(entry.getValue()));
                } else {
                    field.set(entity, entry.getValue());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Object getRepositoryForModel(String modelName) {
        switch (modelName) {
            case "Book":
                return bookRepository;
            case "Car":
                return carRepository;
            case "Game":
                return gameRepository;
            case "People":
                return peopleRepository;
            default:
                return null;
        }
    }

    private Object createEntityFromParams(Class<?> entityClass, Map<String, String> params) throws IllegalAccessException, InstantiationException {
        Object entity = entityClass.newInstance();

        for (Field field : entityClass.getDeclaredFields()) {
            String fieldName = field.getName();
            if (params.containsKey(fieldName)) {
                String paramValue = params.get(fieldName);
                setField(entity, field, paramValue);
            }
        }
        return entity;
    }

    private void setField(Object entity, Field field, String value) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.getType() == Long.class) {
            field.set(entity, Long.parseLong(value));
        } else if (field.getType() == String.class) {
            field.set(entity, value);
        }
    }
}
