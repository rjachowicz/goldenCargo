package com.goldencargo.service;

import com.goldencargo.model.data.Status;
import com.goldencargo.model.dto.api.TransportOrderDetailsDTO;
import com.goldencargo.model.entities.ClientOrder;
import com.goldencargo.model.entities.Driver;
import com.goldencargo.model.entities.TransportOrder;
import com.goldencargo.model.entities.Vehicle;
import com.goldencargo.repository.TransportOrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TransportOrderService {

    private final TransportOrderRepository transportOrderRepository;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final ClientOrderService clientOrderService;

    public TransportOrderService(TransportOrderRepository transportOrderRepository, DriverService driverService, VehicleService vehicleService,
                                 ClientOrderService clientOrderService) {
        this.transportOrderRepository = transportOrderRepository;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.clientOrderService = clientOrderService;
    }

    public List<TransportOrder> getAllOrders() {
        return transportOrderRepository.findByIsDeletedFalse();
    }

    public List<TransportOrder> findTransportOrdersWithStatusNew() {
        return transportOrderRepository.findTransportOrdersWithStatusNew();
    }

    public Optional<TransportOrder> getOrderById(Long id) {
        return transportOrderRepository.findById(id);
    }

    public void createOrder(TransportOrder order) {
        transportOrderRepository.save(order);
    }

    public Optional<TransportOrder> updateOrder(Long id, TransportOrder orderDetails) {
        return transportOrderRepository.findById(id).map(existingOrder -> {
            copyTransportOrderFields(orderDetails, existingOrder);
            existingOrder.setClientOrders(orderDetails.getClientOrders());
            return transportOrderRepository.save(existingOrder);
        });
    }

    public void createTransportOrdersForClientOrders(TransportOrder transportOrder, List<Long> clientOrderIds) {
        List<ClientOrder> clientOrders = clientOrderService.getClientOrdersByIds(clientOrderIds);
        transportOrder.setClientOrders(new HashSet<>(clientOrders));
        transportOrder.setStatus(Status.NEW);

        Optional<Driver> driver = driverService.getDriverById(transportOrder.getAssignedDriver().getDriverId());
        driver.get().setDriverStatus(Driver.DriverStatus.OCCUPIED);

        Optional<Vehicle> vehicle = vehicleService.getVehicleById(transportOrder.getAssignedVehicle().getVehicleId());
        vehicle.get().setStatus(Vehicle.VehicleStatus.OCCUPIED);

        transportOrderRepository.save(transportOrder);
        for (ClientOrder clientOrder : clientOrders) {
            clientOrder.setStatus(Status.COMPLETED);
            clientOrderService.updateClientOrder(clientOrder.getClientOrderId(), clientOrder);
        }
        driverService.updateDriver(driver.get().getDriverId(), driver.get());
        vehicleService.updateVehicle(vehicle.get().getVehicleId(), vehicle.get());
    }

    public boolean deleteOrder(Long id) {
        if (transportOrderRepository.existsById(id)) {
            transportOrderRepository.softDelete(id);
            return true;
        }
        return false;
    }

    public List<TransportOrder> getTransportOrdersNotAssignedToTransport() {
        return transportOrderRepository.findTransportOrdersNotAssignedToTransport();
    }

    private TransportOrder copyTransportOrderFields(TransportOrder source, TransportOrder target) {
        target.setName(source.getName());
        target.setAssignedDriver(source.getAssignedDriver());
        target.setAssignedVehicle(source.getAssignedVehicle());
        target.setStartLocation(source.getStartLocation());
        target.setEndLocation(source.getEndLocation());
        target.setScheduledDeparture(source.getScheduledDeparture());
        target.setScheduledArrival(source.getScheduledArrival());
        target.setStatus(source.getStatus());
        target.setUpdatedAt(new java.util.Date());
        return target;
    }

    public void modifyDataStatuses(TransportOrder transportOrder) {
        transportOrder.setStatus(Status.COMPLETED);

        Optional<Driver> driver = driverService.getDriverById(transportOrder.getAssignedDriver().getDriverId());
        driver.get().setDriverStatus(Driver.DriverStatus.AVAILABLE);
        driverService.updateDriver(driver.get().getDriverId(), driver.get());

        Optional<Vehicle> vehicle = vehicleService.getVehicleById(transportOrder.getAssignedVehicle().getVehicleId());
        vehicle.get().setStatus(Vehicle.VehicleStatus.AVAILABLE);
        vehicleService.updateVehicle(vehicle.get().getVehicleId(), vehicle.get());
    }

    public List<TransportOrderDetailsDTO> getTransportOrderApi() {
        return transportOrderRepository.findAllTransportOrders();
    }

}
