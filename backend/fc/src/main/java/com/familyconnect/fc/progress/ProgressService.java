package com.familyconnect.fc.progress;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.familyconnect.fc.family.Family;
import com.familyconnect.fc.family.FamilyRepository;
import com.familyconnect.fc.spin.Spin;
import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;
import com.familyconnect.fc.utils.Enums;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FamilyRepository familyRepository;

    public ResponseEntity<?> createProgress(ProgressCreateDTO progress) {
        if (!userRepository.findByUsername(progress.getCreatedBy()).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        ApplicationUser user = userRepository.findByUsername(progress.getCreatedBy()).get();

        if (!familyRepository.findById(user.getFamilyId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found.");
        }

        Family family = familyRepository.findById(user.getFamilyId()).get();

        if (!family.getFamilyMembers().contains(progress.getAssignedTo())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found in family.");
        }

        OffsetDateTime now = OffsetDateTime.now();

        if (progress.getDueDate().withHour(23).withMinute(59).withSecond(59).isBefore(now)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Due date cannot be in the past.");
        }

        Progress createdProgress = new Progress(
            progress.getProgressName(),
            progress.getQuota(),
            progress.getCurrentStatus(),
            progress.getDueDate(),
            progress.getCreatedBy(),
            progress.getAssignedTo(),
            progress.getRewards()
            );

        return ResponseEntity.ok(progressRepository.save(createdProgress));
        
    }

    public boolean existsById(Integer progressId) {
        return progressRepository.existsById(progressId);
    }

    public ResponseEntity<?> deleteProgress(Integer progressId) {

        // check if progress exists
        if (!progressRepository.existsById(progressId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress not found.");
        }

        progressRepository.deleteById(progressId);

        return ResponseEntity.ok("Progress deleted successfully.");
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
            if (family.getFamilyMembers().contains(progresses.get(i).getAssignedTo())) {
                familyProgresses.add(progresses.get(i));
            }
        }

        return familyProgresses;
    }

    public List<Progress> getProgressByUserName(String userName) {
        return progressRepository.findByAssignedTo(userName);
    }

    public Progress updateProgress(Integer progressId, Progress progressDetails) {
        Optional<Progress> optionalProgress = progressRepository.findById(progressId);
        if (!optionalProgress.isPresent()) {
            System.out.println("Progress not found for this id :: " + progressId);
            return null;
        }
        Progress existingProgress = optionalProgress.get();


        // check if progress is inprogress
        if (existingProgress.getProgressStatus() != Enums.ProgressStatus.IN_PROGRESS) {
            System.out.println("Progress is not in progress.");
            return null;
        }
        

        
        existingProgress.setQuota(progressDetails.getQuota());

        return progressRepository.save(existingProgress);
    }

    public ResponseEntity<?> updateProgress(Integer progressId, ProgressCreateDTO progressDetails) {
        Optional<Progress> optionalProgress = progressRepository.findById(progressId);
        if (!optionalProgress.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress not found for this id :: " + progressId);
        }

        // check if due date is not in the past
        OffsetDateTime now = OffsetDateTime.now();

        if (progressDetails.getDueDate().withHour(23).withMinute(59).withSecond(59).isBefore(now)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Due date cannot be in the past.");
        }


        Progress existingProgress = optionalProgress.get();
        existingProgress.setProgressName(progressDetails.getProgressName());
        existingProgress.setQuota(progressDetails.getQuota());
        existingProgress.setDueDate(progressDetails.getDueDate());
        existingProgress.setAssignedTo(progressDetails.getAssignedTo());

        existingProgress.setRewards(progressDetails.getRewards());

        return ResponseEntity.ok(progressRepository.save(existingProgress));
    }

    public ResponseEntity<?> completeProgress(String userName, Integer progressId) {
        // check if progress exists
        if (!progressRepository.existsById(progressId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress not found.");
        }

        Progress progress = progressRepository.findById(progressId).get();

        // check if user exists

        if (!userRepository.findByUsername(userName).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        ApplicationUser user = userRepository.findByUsername(userName).get();

        // check if user is parent
        if(!user.isParent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not parent");
        }

        // check if progress is in progress
        if (progress.getProgressStatus() != Enums.ProgressStatus.IN_PROGRESS) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Progress is not in progress.");
        }

        // set progress status to completed
        progress.setProgressStatus(Enums.ProgressStatus.COMPLETED);

        
        // get family and check if user in family
        if (!familyRepository.findById(user.getFamilyId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family not found.");
        }

        Family family = familyRepository.findById(user.getFamilyId()).get();
        
        // add rewards to user
        Spin spin = new Spin(progress.getAssignedTo(), progress.getRewards(), family);
    
        family.addSpin(spin);

        familyRepository.save(family);
        
        
        Family changedFamily = familyRepository.findById(user.getFamilyId()).get();

        changedFamily.PrintSpins();
        
        return ResponseEntity.ok(progressRepository.save(progress));
    }

    public ResponseEntity<?> cancelProgress(String userName, Integer progressId) {
        // check if progress exists
        if (!progressRepository.existsById(progressId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress not found.");
        }

        Progress progress = progressRepository.findById(progressId).get();

        // check if user exists

        if (!userRepository.findByUsername(userName).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        ApplicationUser user = userRepository.findByUsername(userName).get();

        // check if user is parent
        if(!user.isParent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not parent");
        }

        // set progress status to cancelled
        progress.setProgressStatus(Enums.ProgressStatus.CANCELLED);
        
        return ResponseEntity.ok(progressRepository.save(progress));
    }
}
