package com.familyconnect.fc.progress;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<Progress> getAllProgress() {
        return progressRepository.findAll();
    }

    public List<Progress> getProgressByUserName(String userName) {
        return progressRepository.findByAssignedTo(userName);
    }

    public Progress updateProgress(Integer progressId, Progress progressDetails) {
        Optional<Progress> optionalProgress = progressRepository.findById(progressId);
        if (!optionalProgress.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found for this id :: " + progressId);
        }
        Progress existingProgress = optionalProgress.get();
        existingProgress.setProgressName(progressDetails.getProgressName());
        existingProgress.setQuota(progressDetails.getQuota());
        existingProgress.setDueDate(progressDetails.getDueDate());
        existingProgress.setAssignedTo(progressDetails.getAssignedTo());

        return progressRepository.save(existingProgress);
    }
}
