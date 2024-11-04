package com.goldencargo.service;

import com.goldencargo.model.entities.Incident;
import com.goldencargo.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
    }

    public Incident createIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    public Optional<Incident> updateIncident(Long id, Incident incidentDetails) {
        return incidentRepository.findById(id).map(incident -> {
            incident.setIncidentType(incidentDetails.getIncidentType());
            incident.setDate(incidentDetails.getDate());
            incident.setDescription(incidentDetails.getDescription());
            incident.setVehicle(incidentDetails.getVehicle());
            incident.setDriver(incidentDetails.getDriver());
            incident.setReportedBy(incidentDetails.getReportedBy());
            incident.setStatus(incidentDetails.getStatus());
            incident.setUpdatedAt(new java.util.Date());
            return incidentRepository.save(incident);
        });
    }

    public boolean deleteIncident(Long id) {
        if (incidentRepository.existsById(id)) {
            incidentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
