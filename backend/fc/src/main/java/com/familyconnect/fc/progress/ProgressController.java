package com.familyconnect.fc.progress;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("/create")
    public ResponseEntity<?> createProgress(@RequestBody ProgressCreateDTO progress) {
        ResponseEntity<?> createdProgress = progressService.createProgress(progress);
        return ResponseEntity.status(createdProgress.getStatusCode()).body(createdProgress.getBody());
    }

    @DeleteMapping("/delete/{progressId}")
    public ResponseEntity<?> deleteProgress(@PathVariable Integer progressId) {
        ResponseEntity<?> deletedProgress = progressService.deleteProgress(progressId);
        return ResponseEntity.status(deletedProgress.getStatusCode()).body(deletedProgress.getBody());

    }

    @GetMapping("/getFamilyAll/{userName}")
    public ResponseEntity<?> getAllProgress(@PathVariable String userName) {
        List<Progress> allProgress = progressService.getAllProgress(userName);
        if (allProgress == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or no progress found.");
        }
        return ResponseEntity.ok(allProgress);
    }

    @GetMapping("/getByUserId/{userName}")
    public ResponseEntity<?> getProgressByUserName(@PathVariable String userName) {
        List<Progress> userProgress = progressService.getProgressByUserName(userName);
        return ResponseEntity.ok(userProgress);
    }

    @PutMapping("/update/{progressId}")
    public ResponseEntity<?> updateProgress(@PathVariable Integer progressId, @RequestBody ProgressCreateDTO progressDetails) {
        ResponseEntity<?> updatedProgress = progressService.updateProgress(progressId, progressDetails);
        return ResponseEntity.status(updatedProgress.getStatusCode()).body(updatedProgress.getBody());
    }

    // complate progress
    @PostMapping("/completeProgress/{userName}/{progressId}")
    public ResponseEntity<?> completeProgress(@PathVariable String userName, @PathVariable Integer progressId) {
        ResponseEntity<?> completedProgress = progressService.completeProgress(userName, progressId);
        return ResponseEntity.status(completedProgress.getStatusCode()).body(completedProgress.getBody());
    }

    // cancel progress
    @PostMapping("/cancelProgress/{userName}/{progressId}")
    public ResponseEntity<?> cancelProgress(@PathVariable String userName, @PathVariable Integer progressId) {
        ResponseEntity<?> canceledProgress = progressService.cancelProgress(userName, progressId);
        return ResponseEntity.status(canceledProgress.getStatusCode()).body(canceledProgress.getBody());
    }
}
