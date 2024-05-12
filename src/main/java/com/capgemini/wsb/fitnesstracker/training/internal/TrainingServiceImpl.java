package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingTO;
import com.capgemini.wsb.fitnesstracker.user.api.User;
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

}
