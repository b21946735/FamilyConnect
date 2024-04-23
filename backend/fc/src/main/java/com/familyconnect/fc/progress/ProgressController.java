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
        Progress createdProgress = progressService.createProgress(progress);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProgress);
    }

    @DeleteMapping("/delete/{progressId}")
    public ResponseEntity<?> deleteProgress(@PathVariable Integer progressId) {
        if (progressService.existsById(progressId)) { // Progress var mı kontrol et
            progressService.deleteProgress(progressId);
            return ResponseEntity.ok("Progress deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress not found.");
        }
    }

    @GetMapping("/getFamilyAll/{userName}")
    public ResponseEntity<?> getAllProgress(@PathVariable String userName) {
        List<Progress> allProgress = progressService.getAllProgress(userName);
        if (allProgress != null&& allProgress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or no progress found.");
        }
        return ResponseEntity.ok(allProgress);
    }

    @GetMapping("/getByUserId/{userName}")
    public ResponseEntity<?> getProgressByUserName(@PathVariable String userName) {
        List<Progress> userProgress = progressService.getProgressByUserName(userName);
        if (userProgress.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No progress found for user: " + userName);
        }
        return ResponseEntity.ok(userProgress);
    }

    @PutMapping("/update/{progressId}")
    public ResponseEntity<?> updateProgress(@PathVariable Integer progressId, @RequestBody ProgressCreateDTO progressDetails) {
        if (!progressService.existsById(progressId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progress not found for id: " + progressId);
        }
        Progress updatedProgress = progressService.updateProgress(progressId, progressDetails);
        return ResponseEntity.ok(updatedProgress);
    }
}
