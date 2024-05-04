package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrainingController {

    private final TrainingServiceImpl trainingService;

    @Autowired
    public TrainingController(TrainingServiceImpl trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingTO>> getAllTrainings() {
        List<TrainingTO> trainings = trainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @PostMapping("/trainings/add")
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        Training newTraining = trainingService.createTraining(training);
        return new ResponseEntity<>(newTraining, HttpStatus.CREATED);
    }
}