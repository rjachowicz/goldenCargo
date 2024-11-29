package com.goldencargo.service;

import com.goldencargo.model.entities.Route;
import com.goldencargo.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findByIsDeletedFalse();
    }

    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id);
    }

    public void createRoute(Route route) {
        routeRepository.save(route);
    }

    public void updateRoute(Long id, Route routeDetails) {
        routeRepository.findById(id).map(route -> {
            route.setStartLocation(routeDetails.getStartLocation());
            route.setEndLocation(routeDetails.getEndLocation());
            route.setDistance(routeDetails.getDistance());
            route.setEstimatedTime(routeDetails.getEstimatedTime());
            route.setPreferredRoute(routeDetails.getPreferredRoute());
            route.setUpdatedAt(new java.util.Date());
            return routeRepository.save(route);
        });
    }

    public boolean deleteRoute(Long id) {
        if (routeRepository.existsById(id)) {
            routeRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
