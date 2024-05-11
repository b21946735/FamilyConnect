package com.familyconnect.fc.family;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.familyconnect.fc.user.ApplicationUser;
import com.familyconnect.fc.user.UserRepository;
import com.familyconnect.fc.utils.Enums.TaskStatus;
import com.familyconnect.fc.utils.Enums.UserRole;
import com.familyconnect.fc.spin.Reward;
import com.familyconnect.fc.spin.RewardRepository;
import com.familyconnect.fc.spin.SpinRepository;
import com.familyconnect.fc.task.Task;
import com.familyconnect.fc.task.TaskRepository;


@Service
@Transactional
public class FamilyService {

    @Autowired
    private  FamilyRepository familyRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private SpinRepository spinRepository;

    

    public ResponseEntity<?> Createfamily(FamilyRequestDTO family) {
        Family myFamily = new Family(family.getFamilyName(), family.getFamilyCreatorUserName());
        
        String creatorName = family.getFamilyCreatorUserName();
        
        System.out.println("creator name: " + creatorName );
        if(userRepository.findByUsername(creatorName).isPresent()){
            ApplicationUser user = userRepository.findByUsername(creatorName).get();
            if(user.getFamilyId() != null && user.getFamilyId() != -1){
                System.out.println("User already belongs to a family");

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User already belongs to a family");
            }

            if(user.isChild()){
                System.out.println("User is a child and cannot create a family");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is a child and cannot create a family");
            }
            

            familyRepository.save(myFamily);
            
            Integer familyId = myFamily.getId();
            user.setFamilyId(familyId);
            userRepository.save(user);
            System.out.println("Family id: " + familyId);
            System.out.println("Family created successfully for user: " + creatorName);
            return ResponseEntity.ok(myFamily);
        }
        System.out.println("User not found while creating family");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while creating family");
  
    }

    public ResponseEntity<?> GetFamily(String userName) {
        // check if user exists
        if(!userRepository.findByUsername(userName).isPresent()){
            System.out.println("User not found while getting family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while getting family");
        }

        Integer userFamilyId = userRepository.findByUsername(userName).get().getFamilyId();
        if(userFamilyId == null || userFamilyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Family family = familyRepository.findById(userFamilyId).get();
        System.out.println("Family found: " + family.getFamilyName());
        // update task status
        updateTaskStatus(family);
        return ResponseEntity.ok(family);
    }

    public ResponseEntity<?> addFamilyMembers(int familyId, List<String> userNames) {
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }

        Family family = familyOpt.get();
        List<ApplicationUser> usersToAdd = new ArrayList<>();
        List<String> familyMembers = family.getFamilyMembers();

        for (String userName : userNames) {
            Optional<ApplicationUser> userOpt = userRepository.findByUsername(userName);
            if (!userOpt.isPresent() || (userOpt.get().getFamilyId() != null && userOpt.get().getFamilyId() != -1)) {
                System.out.println("User not found or already belongs to a family: " + userName);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found or already belongs to a family: " );
            }
            ApplicationUser user = userOpt.get();
            user.setFamilyId(familyId);
            usersToAdd.add(user);
            familyMembers.add(userName);

        }
        family.setFamilyMembers(familyMembers);
        
        familyRepository.save(family);
        userRepository.saveAll(usersToAdd);
        return ResponseEntity.ok(family);
    }

    public ResponseEntity<?> updateFamilyName(String  username, String familyName) {
        Optional<ApplicationUser> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            System.out.println("User not found while updating family name");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while updating family name");
        }
        ApplicationUser user = userOpt.get();
        if(!user.isParent()){
            System.out.println("User is not a parent and cannot update family name");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not a parent and cannot update family name");
        }

        Integer familyId = user.getFamilyId();
        if (familyId == null || familyId == -1) {
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        
        Family family = familyOpt.get();
        family.setFamilyName(familyName);
        familyRepository.save(family);
        System.out.println("Family name updated successfully");
        return ResponseEntity.ok(family);
    }

    public ResponseEntity<?> getFamilyMembersInformation(String userName) {
        if(!userRepository.findByUsername(userName).isPresent()){
            System.out.println("User not found while getting family members information");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while getting family members information");
        }
        Integer familyId = userRepository.findByUsername(userName).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();
        List<String> familyMembers = family.getFamilyMembers();
        List<FamilyMemberInfoDTO> familyMemberInfos = new ArrayList<>();
        for (String memberName : familyMembers) {
            Optional<ApplicationUser> userOpt = userRepository.findByUsername(memberName);
            if (!userOpt.isPresent()) {
                System.out.println("User not found while getting family members information");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while getting family members information");
            }
            ApplicationUser user = userOpt.get();
            String role = user.isParent() ? UserRole.PARENT.toString() : UserRole.CHILD.toString();
            FamilyMemberInfoDTO familyMemberInfo = new FamilyMemberInfoDTO(user.getUsername(), user.getName(), user.getProfilePictureId(),role);
            familyMemberInfos.add(familyMemberInfo);
        }

        updateTaskStatus(family);
        return ResponseEntity.ok(familyMemberInfos);
    }


    // update task status check if due date is passed
    public void updateTaskStatus(Family family){
        List<Task> tasks = family.getTasks();
        for(Task task : tasks){
            if(task.getTaskStatus() == TaskStatus.IN_PROGRESS && task.getTaskDueDate().withHour(23).withMinute(59).withSecond(59).isBefore(OffsetDateTime.now())){
                task.setTaskStatus(TaskStatus.FAILED);
                taskRepository.save(task);
            }
        }
    }

    public ResponseEntity<?> removeFamilyMember(Integer familyId, String userName) {
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();
        List<String> familyMembers = family.getFamilyMembers();
        if (!familyMembers.contains(userName)) {
            System.out.println("User does not belong to the family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to the family");
        }
        familyMembers.remove(userName);
        family.setFamilyMembers(familyMembers);
        familyRepository.save(family);
        Optional<ApplicationUser> userOpt = userRepository.findByUsername(userName);
        if (!userOpt.isPresent()) {
            System.out.println("User not found while removing family member");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while removing family member");
        }
        ApplicationUser user = userOpt.get();
        user.setFamilyId(-1);
        userRepository.save(user);
        return ResponseEntity.ok(family);
    }

    public ResponseEntity<?> getSpins(String username) {
        if(!userRepository.findByUsername(username).isPresent()){
            System.out.println("User not found while getting spins");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while getting spins");
        }
        Integer familyId = userRepository.findByUsername(username).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();
        family.PrintSpins();
        return ResponseEntity.ok(family.getSpins());
    }

    public ResponseEntity<?> setReward(FamilySpinDataDTO spinData) {
        if(!userRepository.findByUsername(spinData.getUsername()).isPresent()){
            System.out.println("User not found while rolling spin");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while rolling spin");
        }
        Integer familyId = userRepository.findByUsername(spinData.getUsername()).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();


        // remove spin from user

        boolean isRemoved =family.removeSpin(spinData.getId());
        spinRepository.deleteById(spinData.getId());
        if(!isRemoved){
            System.out.println("Spin not found for user: " + spinData.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Spin not found for user: " + spinData.getUsername());
        }

        // add prize to user
        Reward reward = new Reward(spinData.getUsername(), spinData.getPrize(), family);
        family.addEarnedReward(reward);
        familyRepository.save(family);
        

        return ResponseEntity.ok(reward);
    }


    public ResponseEntity<?> getFamilyRewards(String username) {
        if(!userRepository.findByUsername(username).isPresent()){
            System.out.println("User not found while getting family rewards");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while getting family rewards");
        }
        Integer familyId = userRepository.findByUsername(username).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();
        return ResponseEntity.ok(family.getEarnedRewards());
    }

    public ResponseEntity<?> getUserSpins(String username) {
        if(!userRepository.findByUsername(username).isPresent()){
            System.out.println("User not found while getting spins");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while getting spins");
        }
        Integer familyId = userRepository.findByUsername(username).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();
        return ResponseEntity.ok(family.getUserSpins(username));
    }

    public ResponseEntity<?> getUserRewards(String username) {
        if(!userRepository.findByUsername(username).isPresent()){
            System.out.println("User not found while getting rewards");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while getting rewards");
        }
        Integer familyId = userRepository.findByUsername(username).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();
        return ResponseEntity.ok(family.getUserRewards(username));
    }


    public ResponseEntity<?> getFamilyPhotos(String username) {
        if(!userRepository.findByUsername(username).isPresent()){
            System.out.println("User not found while getting family photos");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while getting family photos");
        }
        Integer familyId = userRepository.findByUsername(username).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();
        return ResponseEntity.ok(family.getFamilyPhotos());
    }

    public ResponseEntity<?> addFamilyPhotos(FamilyPhotosDTO photoIds) {
        if(photoIds.getFamilyPhotos().size() == 0){
            System.out.println("No photos to add");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No photos to add");

        }

        if(!userRepository.findByUsername(photoIds.getUsername()).isPresent()){
            System.out.println("User not found while adding family photos");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while adding family photos");
        }
        Integer familyId = userRepository.findByUsername(photoIds.getUsername()).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();
        List<String> familyPhotos = family.getFamilyPhotos();
        familyPhotos.addAll(photoIds.getFamilyPhotos());
        family.setFamilyPhotos(familyPhotos);
        familyRepository.save(family);
        return ResponseEntity.ok(family);
    }

    public ResponseEntity<?> setFamilyPhotos(FamilyPhotosDTO photos) {
        if(!userRepository.findByUsername(photos.getUsername()).isPresent()){
            System.out.println("User not found while setting family photos");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found while setting family photos");
        }
        Integer familyId = userRepository.findByUsername(photos.getUsername()).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not belong to any family");
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Family not found with ID: " );
        }
        Family family = familyOpt.get();
        family.setFamilyPhotos(photos.getFamilyPhotos());
        familyRepository.save(family);
        return ResponseEntity.ok(family);
    }

    //get family photos list
    public List<String> getFamilyPhotosList(String username) {
        if(!userRepository.findByUsername(username).isPresent()){
            System.out.println("User not found while getting family photos");
            return null;
        }
        Integer familyId = userRepository.findByUsername(username).get().getFamilyId();
        if(familyId == null || familyId == -1){
            System.out.println("User does not belong to any family");
            return null;
        }
        Optional<Family> familyOpt = familyRepository.findById(familyId);
        if (!familyOpt.isPresent()) {
            System.out.println("Family not found with ID: " + familyId);
            return null;
        }
        Family family = familyOpt.get();
        return family.getFamilyPhotos();
    }
} 