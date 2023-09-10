package com.example.pr3_maven.Controllers;
import com.example.pr3_maven.ModelInfoDTO;
import com.example.pr3_maven.Other;
import com.example.pr3_maven.Repositories.BookRepository;
import com.example.pr3_maven.Repositories.CarRepository;
import com.example.pr3_maven.Repositories.GameRepository;
import com.example.pr3_maven.Repositories.PeopleRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private final BookRepository bookRepository;
    private final CarRepository carRepository;
    private final GameRepository gameRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public MainController(BookRepository bookRepository, CarRepository carRepository, GameRepository gameRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.carRepository = carRepository;
        this.gameRepository = gameRepository;
        this.peopleRepository = peopleRepository;
    }

    @GetMapping("/")
    public String MenuShow(Model model) {
        List<List<String>> modelInfoList = Other.ReadModelsWithFields();
        List<ModelInfoDTO> modelInfoDTOList = new ArrayList<>();
        for (List<String> innerList : modelInfoList) {
            if (!innerList.isEmpty()) {
                String modelName = innerList.get(0);
                List<String> fieldNames = new ArrayList<>(innerList.subList(1, innerList.size()));
                ModelInfoDTO modelInfoDTO = new ModelInfoDTO(modelName, fieldNames);
                modelInfoDTOList.add(modelInfoDTO);
            }
        }
        List<List<?>> allModelDataList = new ArrayList<>();
        CrudRepository<?, Long>[] repositories = new CrudRepository[]{bookRepository, carRepository, gameRepository, peopleRepository};
        for (CrudRepository<?, Long> repository : repositories) {
            List<?> modelData = findAllFromRepository(repository);
            allModelDataList.add(modelData);
        }
        model.addAttribute("AllModelDataList", allModelDataList);
        model.addAttribute("ModelInfoList", modelInfoDTOList);
        return "Menu";
    }
    private List<?> findAllFromRepository(CrudRepository<?, Long> repository) {
        return (List<?>) repository.findAll();
    }

}
