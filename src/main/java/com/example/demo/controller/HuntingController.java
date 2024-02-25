package com.example.demo.controller;

import com.example.demo.model.entities.dto.HuntingDto;
import com.example.demo.service.HuntingService;
import com.example.demo.service.serviceImp.HuntingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/huntings")
@CrossOrigin(origins = "http://localhost:4200")
public class HuntingController {
    private final HuntingServiceImpl huntingService;

    @Autowired
    public HuntingController(HuntingServiceImpl huntingService) {
        this.huntingService = huntingService;
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<HuntingDto> createHunting(@RequestBody HuntingDto huntingDto) {

        HuntingDto createdHunting = huntingService.addHuntingAndCalculateScore(huntingDto);
        return new ResponseEntity<>(createdHunting, HttpStatus.CREATED);
    }
    @GetMapping
    @PreAuthorize("hasAnyRole('ADHERENT', 'JURY', 'MANAGER')")
    public ResponseEntity<List<HuntingDto>> getAllHuntings() {
        List<HuntingDto> huntings = huntingService.getAllHuntings();
        return ResponseEntity.ok(huntings);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADHERENT', 'JURY', 'MANAGER')")
    public ResponseEntity<HuntingDto> getHuntingById(@PathVariable Long id) {
        HuntingDto hunting = huntingService.getHuntingById(id);
        return ResponseEntity.ok(hunting);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<HuntingDto> updateHunting(@PathVariable Long id, @RequestBody HuntingDto huntingDto) {
        HuntingDto updatedHunting = huntingService.updateHunting(id, huntingDto);
        return ResponseEntity.ok(updatedHunting);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteHunting(@PathVariable Long id) {
        huntingService.deleteHunting(id);
        return ResponseEntity.noContent().build();
    }
}
