package com.goldencargo.controller;

import com.goldencargo.model.entities.Damage;
import com.goldencargo.service.DamageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/damages")
public class DamageController {

    private final DamageService damageService;

    public DamageController(DamageService damageService) {
        this.damageService = damageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Damage>> getAllDamages() {
        List<Damage> damages = damageService.getAllDamages();
        return new ResponseEntity<>(damages, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Damage> getDamageById(@PathVariable Long id) {
        Optional<Damage> damage = damageService.getDamageById(id);
        return damage.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Damage> createDamage(@RequestBody Damage damage) {
        Damage createdDamage = damageService.createDamage(damage);
        return new ResponseEntity<>(createdDamage, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Damage> updateDamage(@PathVariable Long id, @RequestBody Damage damageDetails) {
        Optional<Damage> updatedDamage = damageService.updateDamage(id, damageDetails);
        return updatedDamage.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDamage(@PathVariable Long id) {
        boolean isDeleted = damageService.deleteDamage(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}