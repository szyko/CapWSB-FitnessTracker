package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Component
@AllArgsConstructor
@Slf4j
public class ReportGenerator {
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private UserRepository userRepository;
    @Scheduled(cron = "0 0 0 * * MON") // Co tydzień w poniedziałek o północy
    public void sendWeeklyReports() {
        final Logger log = LoggerFactory.getLogger(ReportGenerator.class);

        List<User> users = userRepository.findAll();
        log.info("SENDING EMAILS");
        for (User user : users) {
            List<Training> trainings = trainingRepository.findAllById(Collections.singleton(user.getId()));

            Date lastWeekStartDate = getLastWeekStartDate();
            Date lastWeekEndDate = getLastWeekEndDate();

            // Filtrowanie treningów według daty
            List<Training> filteredTrainings = trainings.stream()
                    .filter(training -> training.getDate() != null &&!training.getDate().before(lastWeekStartDate) && !training.getDate().after(lastWeekEndDate))
                    .toList();

            int trainingCount = filteredTrainings.size();
            EmailDto emailDto = new EmailDto(user.getEmail(), "Tygodniowy raport treningów", "Drogi " + user.getFirstName() + ",\n\nW minionym tygodniu zarejestrowałeś " + trainingCount + " treningów.\n\nPozdrawiamy,\nZespół Fitness");

            emailSender.sendEmail(emailDto);
        }
        log.info("EMAILS SEND");
    }
    private Date getLastWeekStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DATE, -7);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getLastWeekEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }}
