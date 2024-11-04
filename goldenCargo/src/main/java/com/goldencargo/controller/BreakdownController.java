package com.goldencargo.controller;

import com.goldencargo.model.entities.Breakdown;
import com.goldencargo.service.BreakdownService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/breakdowns")
public class BreakdownController {

    private final BreakdownService breakdownService;

    public BreakdownController(BreakdownService breakdownService) {
        this.breakdownService = breakdownService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Breakdown>> getAllBreakdowns() {
        List<Breakdown> breakdowns = breakdownService.getAllBreakdowns();
        return new ResponseEntity<>(breakdowns, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Breakdown> getBreakdownById(@PathVariable Long id) {
        Optional<Breakdown> breakdown = breakdownService.getBreakdownById(id);
        return breakdown.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Breakdown> createBreakdown(@RequestBody Breakdown breakdown) {
        Breakdown createdBreakdown = breakdownService.createBreakdown(breakdown);
        return new ResponseEntity<>(createdBreakdown, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Breakdown> updateBreakdown(@PathVariable Long id, @RequestBody Breakdown breakdownDetails) {
        Optional<Breakdown> updatedBreakdown = breakdownService.updateBreakdown(id, breakdownDetails);
        return updatedBreakdown.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBreakdown(@PathVariable Long id) {
        boolean isDeleted = breakdownService.deleteBreakdown(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}