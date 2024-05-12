package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingTO;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: Provide Impl
@Service
public class TrainingServiceImpl implements TrainingProvider {
    private TrainingRepository trainingRepository;
    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<TrainingTO> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(TrainingTO::new)
                .collect(Collectors.toList());
    }

    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }
    @Autowired
    public void TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public List<Training> getTrainingsForUser(Long userId) {
        return trainingRepository.findByUserId(userId);
    }
    public List<Training> getTrainingsCompletedAfter(Timestamp date) {
        return trainingRepository.findTrainingsCompletedAfter(date);
    }

    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    @Transactional
    public TrainingTO updateTraining(Long trainingId, TrainingTO trainingTO) {
        Training existingTraining = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException(String.valueOf(trainingId)));

        if (trainingTO.getActivityType() != null) {
            existingTraining.setActivityType(trainingTO.getActivityType());
        }
        if (trainingTO.getDistance() != null) {
            existingTraining.setDistance(trainingTO.getDistance());
        }
        if (trainingTO.getStartTime() != null) {
            existingTraining.setStartTime(trainingTO.getStartTime());
        }
        if (trainingTO.getEndTime() != null) {
            existingTraining.setEndTime(trainingTO.getEndTime());
        }
        if (trainingTO.getAverageSpeed() != null) {
            existingTraining.setAverageSpeed(trainingTO.getAverageSpeed());
        }

        trainingRepository.save(existingTraining);
        return trainingTO; // Might need to map the updated entity back to a DTO
    }

}
