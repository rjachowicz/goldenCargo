package com.goldencargo.service;

import com.goldencargo.model.entities.Transport;
import com.goldencargo.repository.TransportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransportService {

    private final TransportRepository transportRepository;

    public TransportService(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    public List<Transport> getAllTransports() {
        return transportRepository.findByIsDeletedFalse();
    }

    public Optional<Transport> getTransportById(Long id) {
        return transportRepository.findById(id);
    }

    public Transport createTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    public Optional<Transport> updateTransport(Long id, Transport transportDetails) {
        return transportRepository.findById(id).map(transport -> {
            transport.setTransportOrder(transportDetails.getTransportOrder());
            transport.setActualDeparture(transportDetails.getActualDeparture());
            transport.setActualArrival(transportDetails.getActualArrival());
            transport.setStatus(transportDetails.getStatus());
            transport.setNotes(transportDetails.getNotes());
            transport.setUpdatedAt(new java.util.Date());
            return transportRepository.save(transport);
        });
    }

    public boolean deleteTransport(Long id) {
        if (transportRepository.existsById(id)) {
            transportRepository.softDelete(id);
            return true;
        }
        return false;
    }
}
