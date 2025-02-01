package com.goldencargo.service;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.dto.api.TransportOrderCreateRequest;
import com.goldencargo.model.entities.Transport;
import com.goldencargo.model.entities.TransportOrder;
import com.goldencargo.repository.TransportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportService {

    private final TransportRepository transportRepository;
    private final TransportOrderService transportOrderService;

    public TransportService(TransportRepository transportRepository, TransportOrderService transportOrderService) {
        this.transportRepository = transportRepository;
        this.transportOrderService = transportOrderService;
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

    public Transport save(Transport transport) {
        return transportRepository.save(transport);
    }

    public Optional<Transport> findByTransportOrderId(Long transportOrderId) {
        return transportRepository.findByTransportOrder(transportOrderId);
    }

    public void startTransport(TransportOrderCreateRequest request, Optional<TransportOrder> transportOrderOpt) {
        TransportOrder transportOrder = transportOrderOpt.get();
        Transport transport = new Transport();
        transport.setTransportOrder(transportOrder);
        transport.setActualDeparture(request.getDate());
        transport.setStatus(Status.PENDING);

        transportOrderService.modifyDataStatuses(transportOrder);
        createTransport(transport);
        transportOrderService.updateOrder(request.getTransportOrderId(), transportOrder);
    }

    public void endTransport(TransportOrderCreateRequest request, Optional<Transport> transportOpt) {
        Transport transport = transportOpt.get();
        transport.setStatus(Status.NEW);
        transport.setActualArrival(request.getDate());
        updateTransport(transport.getTransportId(), transport);
    }
}
