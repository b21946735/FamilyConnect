package com.familyconnect.fc.progress;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.familyconnect.fc.family.Family;
import com.familyconnect.fc.family.FamilyRepository;
import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyRepository familyRepository;

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

    public List<Progress> getAllProgress(String username) {
        if(!userRepository.findByUsername(username).isPresent()){
            return null;
        }
        ApplicationUser user = userRepository.findByUsername(username).get();
        Optional <Family> familyOptional = familyRepository.findById(user.getFamilyId());
        if(!familyOptional.isPresent()){
            return null;
        }
        Family family = familyOptional.get();

        

        List<Progress> progresses= progressRepository.findAll();
        List<Progress> familyProgresses = new ArrayList<>();

        // loop through all progresses and check if the progress is assigned to a user in the family
        for (int i = 0; i < progresses.size(); i++) {
            if (!family.getFamilyMembers().contains(progresses.get(i).getAssignedTo())) {
                familyProgresses.add(progresses.get(i));
            }
        }

        return familyProgresses;
    }

    public List<Progress> getProgressByUserName(String userName) {
        return progressRepository.findByAssignedTo(userName);
    }

    public Progress updateProgress(Integer progressId, ProgressCreateDTO progressDetails) {
        Optional<Progress> optionalProgress = progressRepository.findById(progressId);
        if (!optionalProgress.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Progress not found for this id :: " + progressId);
        }
        Progress existingProgress = optionalProgress.get();
        existingProgress.setProgressName(progressDetails.getProgressName());
        existingProgress.setQuota(progressDetails.getQuota());
        existingProgress.setDueDate(progressDetails.getDueDate());
        existingProgress.setAssignedTo(progressDetails.getAssignedTo());

        return progressRepository.save(existingProgress);
    }

    public Progress updateProgress(Integer progressId, Progress progressDetails) {
        Optional<Progress> optionalProgress = progressRepository.findById(progressId);
        if (!optionalProgress.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Progress not found for this id :: " + progressId);
        }
        Progress existingProgress = optionalProgress.get();
        existingProgress.setProgressName(progressDetails.getProgressName());
        existingProgress.setQuota(progressDetails.getQuota());
        existingProgress.setDueDate(progressDetails.getDueDate());
        existingProgress.setAssignedTo(progressDetails.getAssignedTo());

        return progressRepository.save(existingProgress);
    }
}
