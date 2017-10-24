package com.kramar.data.service;

import com.kramar.data.dbo.AdvertDbo;
import com.kramar.data.profiling.Profiling;
import com.kramar.data.repository.AdvertRepository;
import com.kramar.data.type.AdvertStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertScheduler {

    @Autowired
    private AdvertRepository advertRepository;

        @Scheduled(cron = "0 0 12 * * ?")
//    @Scheduled(cron = "1 * * * * *")
    public void setOldAdvertsInActive() {
        final List<AdvertDbo> advertDbos = advertRepository.findByAdvertStatus(AdvertStatus.ACTIVE);
        advertDbos.forEach(a -> {
            if (a.getCreatedTime().isBefore(LocalDateTime.now().minusMonths(1L))) {
                a.setAdvertStatus(AdvertStatus.INACTIVE);
            }
        });
    }

        @Scheduled(cron = "0 0 12 * * ?")
//    @Scheduled(cron = "1 * * * * *")
    public void deleteInActiveAdverts() {
        final List<AdvertDbo> advertDbos = advertRepository.findByAdvertStatus(AdvertStatus.INACTIVE);
        final List<AdvertDbo> collect =
                advertDbos
                        .stream()
                        .filter(a -> a.getCreatedTime().isBefore(LocalDateTime.now().minusMonths(2L)))
                        .collect(Collectors.toList());
        advertRepository.delete(collect);
    }
}
