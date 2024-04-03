package com.familyconnect.fc.services;

import com.familyconnect.fc.models.Progress;
import com.familyconnect.fc.models.ProgressCreateDTO;
import com.familyconnect.fc.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public Progress createProgress(ProgressCreateDTO progress) {
        Progress createdProgress = new Progress(
            progress.getProgressName(),
            progress.getQuota(),
            progress.getCurrentStatus(),
            progress.getDueDate(),
            progress.getCreatedBy(),
            progress.getAssignedTo()
            );

        return progressRepository.save(createdProgress);
    }

    public boolean existsById(Integer progressId) {
        return progressRepository.existsById(progressId);
    }

    public void deleteProgress(Integer progressId) {
        progressRepository.deleteById(progressId);
    }
}
