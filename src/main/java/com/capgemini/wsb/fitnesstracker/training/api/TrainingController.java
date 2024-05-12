package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/trainings/userid/{userId}")
    public ResponseEntity<List<Training>> getTrainingsForUser(@PathVariable Long userId) {
        List<Training> trainings = trainingService.getTrainingsForUser(userId);
        return ResponseEntity.ok(trainings);
    }
    @GetMapping("/trainings/completed-after")
    public ResponseEntity<List<Training>> getTrainingsCompletedAfter(
            @RequestParam("date") String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        List<Training> trainings = trainingService.getTrainingsCompletedAfter(timestamp);
        return ResponseEntity.ok(trainings);
    }
}