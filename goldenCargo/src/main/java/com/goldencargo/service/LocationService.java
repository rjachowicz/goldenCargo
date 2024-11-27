package com.goldencargo.service;

import com.goldencargo.model.entities.Location;
import com.goldencargo.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findByIsDeletedFalse();
    }

    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    public void createLocation(Location location) {
        locationRepository.save(location);
    }

    public void updateLocation(Long id, Location locationDetails) {
        locationRepository.findById(id).map(location -> {
            location.setName(locationDetails.getName());
            location.setAddress(locationDetails.getAddress());
            location.setCity(locationDetails.getCity());
            location.setState(locationDetails.getState());
            location.setCountry(locationDetails.getCountry());
            location.setPostalCode(locationDetails.getPostalCode());
            location.setLatitude(locationDetails.getLatitude());
            location.setLongitude(locationDetails.getLongitude());
            location.setUpdatedAt(new java.util.Date());
            return locationRepository.save(location);
        });
    }

    public boolean deleteLocation(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.softDelete(id);
            return true;
        }
        return false;
    }
}