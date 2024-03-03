package com.irembo.test.scheduledTasks;

import com.irembo.test.persistence.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExpiredUrlCleanupTask {

    @Autowired
    private UrlRepository urlRepository;

    // Run daily at 00:00 AM
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredUrls() {
        LocalDateTime currentDate = LocalDateTime.now();
        urlRepository.deleteAllByExpiryDateBefore(currentDate);

    }
}
