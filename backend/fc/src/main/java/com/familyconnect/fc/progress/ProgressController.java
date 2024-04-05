package com.familyconnect.fc.progress;

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
}
