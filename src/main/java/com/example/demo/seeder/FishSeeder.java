package com.example.demo.seeder;

import com.example.demo.model.entities.Fish;
import com.example.demo.model.entities.Level;
import com.example.demo.repository.IfishRepo;
import com.example.demo.repository.IlevelRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FishSeeder {

    private final IfishRepo fishRepository;
    private final IlevelRepo levelRepository;

    @Autowired
    public FishSeeder(IfishRepo fishRepository, IlevelRepo levelRepository) {
        this.fishRepository = fishRepository;
        this.levelRepository = levelRepository;
    }

    @PostConstruct
    public void seed() {
        if (fishRepository.count() == 0) {
            Level commonLevel = new Level("common", 25);
            Level beginnerLevel = new Level("Beginner", 50);
            Level intermediateLevel = new Level("Intermediate", 100);
            Level advancedLevel = new Level("Advanced", 150);
            levelRepository.saveAll(List.of(commonLevel, beginnerLevel, intermediateLevel, advancedLevel));

            List<Fish> fishList = List.of(
                    new Fish(null, "Alewife", 0.5f, commonLevel),
                    new Fish(null, "Amberjack", 15.0f, intermediateLevel),
                    new Fish(null, "Amberjack (yellowtail)", 18.0f, advancedLevel),
                    new Fish(null, "European flounder", 2.0f, intermediateLevel),
                    new Fish(null, "European seabass", 3.5f, advancedLevel),
                    new Fish(null, "European smelt", 0.8f, commonLevel),
                    new Fish(null, "European eel", 5.0f, intermediateLevel),
                    new Fish(null, "Fathead minnow", 0.3f, commonLevel),
                    new Fish(null, "Formosa snakehead", 4.0f, advancedLevel),
                    new Fish(null, "Gourami", 1.2f, intermediateLevel),
                    new Fish(null, "Gilthead seabream", 2.5f, intermediateLevel),
                    new Fish(null, "Gizzard shad", 1.0f, beginnerLevel),
                    new Fish(null, "Glass knifefish", 0.6f, commonLevel),
                    new Fish(null, "Gold sing wrasse", 1.8f, advancedLevel),
                    new Fish(null, "Golden shiner", 0.5f, commonLevel),
                    new Fish(null, "Goldfish (Crucian carp)", 0.3f, commonLevel),
                    new Fish(null, "Grass carp", 8.0f, advancedLevel),
                    new Fish(null, "Grayling", 1.2f, intermediateLevel),
                    new Fish(null, "Greenback flounder", 2.2f, intermediateLevel),
                    new Fish(null, "Gudgeon (topmouth)", 0.4f, commonLevel),
                    new Fish(null, "Gulf killifish", 0.6f, commonLevel),
                    new Fish(null, "Gulf menhaden", 1.0f, beginnerLevel),
                    new Fish(null, "Guppy", 0.2f, commonLevel),
                    new Fish(null, "Hardhead catfish", 2.0f, intermediateLevel),
                    new Fish(null, "Ide (Orfe)", 1.5f, intermediateLevel),
                    new Fish(null, "Indian glassfish", 0.4f, commonLevel),
                    new Fish(null, "Itipa mojarras", 0.7f, commonLevel)
            );

            fishRepository.saveAll(fishList);
        }
    }
}