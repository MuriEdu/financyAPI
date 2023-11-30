package com.muri.financyAPI.controllers;

import com.muri.financyAPI.models.MovementModel;
import com.muri.financyAPI.repositories.IMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movements")
public class MovementController {
    @Autowired
    private IMovementRepository movementRepository;
/*
    @PostMapping("/new")
    public ResponseEntity create(@RequestBody MovementModel newMovement){

    }
*/
}
