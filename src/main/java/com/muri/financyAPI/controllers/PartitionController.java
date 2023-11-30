package com.muri.financyAPI.controllers;

import com.muri.financyAPI.models.PartitionModel;
import com.muri.financyAPI.repositories.IPartitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/partitions")
public class PartitionController {

    @Autowired
    private IPartitionRepository partitionRepository;

    @PostMapping("/new")
    public ResponseEntity create(@RequestBody PartitionModel partitionModel){
        var partition = this.partitionRepository.save(partitionModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(partition);
    }

    @GetMapping("/get")
    public ResponseEntity getAll(){
        List<PartitionModel> res;
        res = partitionRepository.findAll();
        if(res.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No partitions found");

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/get/")
    public ResponseEntity getById (@RequestParam("id") UUID id){
        var partition = this.partitionRepository.findById(id).orElse(null);
        if(partition == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partition not founded");

        return ResponseEntity.status(HttpStatus.OK).body(partition);
    }

    @GetMapping("/get/bydate")
    public ResponseEntity getByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String dateString) {
        LocalDateTime date;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            date = LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Please provide date in ISO format.");
        }

        List<PartitionModel> res = partitionRepository.findByCreatedAtGreaterThanEqual(date);

        if (res.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No partitions found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    
}
