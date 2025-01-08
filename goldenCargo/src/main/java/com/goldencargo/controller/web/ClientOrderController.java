package com.goldencargo.controller.web;

import com.goldencargo.model.data.PaymentStatus;
import com.goldencargo.model.data.Status;
import com.goldencargo.model.entities.*;
import com.goldencargo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/client-orders")
public class ClientOrderController {

    private static final String ALIAS = "co";
    private final ClientOrderService clientOrderService;
    private final ClientService clientService;
    private final GenericService genericService;
    private final GoodsService goodsService;
    private final TransportService transportService;
    private final TransportOrderService transportOrderService;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final LocationService locationService;

    public ClientOrderController(ClientOrderService clientOrderService,
                                 ClientService clientService,
                                 GenericService genericService,
                                 GoodsService goodsService,
                                 TransportService transportService,
                                 TransportOrderService transportOrderService,
                                 DriverService driverService,
                                 VehicleService vehicleService, LocationService locationService) {
        this.clientOrderService = clientOrderService;
        this.clientService = clientService;
        this.genericService = genericService;
        this.goodsService = goodsService;
        this.transportService = transportService;
        this.transportOrderService = transportOrderService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.locationService = locationService;
    }

    @GetMapping
    public String getAllClientOrders(
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "comparisonType", required = false, defaultValue = "like") String comparisonType,
            @RequestParam(value = "sortBy", required = false, defaultValue = "orderDate") String sortBy,
            @RequestParam(value = "sortLogic", required = false, defaultValue = "asc") String sortLogic,
            Model model) {

        List<ClientOrder> clientOrders = genericService.getFilteredAndSortedEntities(
                ClientOrder.class,
                ALIAS,
                filterType,
                filterValue,
                comparisonType,
                sortBy,
                sortLogic
        );
        model.addAttribute("clientOrders", clientOrders);
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortLogic", sortLogic);
        model.addAttribute("clientOrder", new ClientOrder());
        return "client-orders/main";
    }

    @PostMapping("/create")
    public String createClientOrder(@ModelAttribute ClientOrder clientOrder) {
        clientOrderService.createClientOrder(clientOrder);
        return "redirect:/client-orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<ClientOrder> clientOrder = clientOrderService.getClientOrderById(id);
        if (clientOrder.isPresent()) {
            model.addAttribute("clientOrder", clientOrder.get());
            model.addAttribute("clients", clientService.getAllClients());
            model.addAttribute("statuses", Status.values());
            model.addAttribute("paymentStatuses", PaymentStatus.values());
            return "client-orders/edit :: editClientOrderModal";
        }
        return "redirect:/client-orders";
    }

    @PostMapping("/update/{id}")
    public String updateClientOrder(@PathVariable Long id, @ModelAttribute ClientOrder clientOrderDetails) {
        clientOrderService.updateClientOrder(id, clientOrderDetails);
        return "redirect:/client-orders";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<ClientOrder> clientOrder = clientOrderService.getClientOrderByIdWithDetails(id);
        if (clientOrder.isPresent()) {
            model.addAttribute("clientOrder", clientOrder.get());
            return "client-orders/details :: detailsClientOrderModal";
        }
        return "redirect:/client-orders";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientOrder(@PathVariable Long id) {
        boolean isDeleted = clientOrderService.deleteClientOrder(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/new-order")
    public String showNewOrderForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("goods", new Goods());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("availableGoods", goodsService.getAllGoods());

        return "client-orders/new-order";
    }

    @GetMapping("/new-transport-order")
    public String showNewTransportOrder(Model model) {
        model.addAttribute("transport", new Transport());
        model.addAttribute("transportOrder", new TransportOrder());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("clientOrders", clientOrderService.getClientOrdersWithGoods());
        model.addAttribute("availableTransports", transportService.getAllTransports());
        model.addAttribute("transportOrders", transportOrderService.getAllOrders());
        model.addAttribute("statuses", Status.values());

        return "client-orders/new-transport-order";
    }

    @PostMapping("/create-client")
    public String createClient(@ModelAttribute Client client) {
        clientService.createClient(client);
        return "redirect:/client-orders/new-order";
    }

    @PostMapping("/create-goods")
    public String createGoods(@ModelAttribute Goods goods) {
        goodsService.createGoods(goods);
        return "redirect:/client-orders/new-order";
    }

    @PostMapping("/new-transport-order")
    public String createTransportOrder(@ModelAttribute TransportOrder transportOrder) {
        transportOrderService.createOrder(transportOrder);
        clientOrderService.updateClientOrderStatus(transportOrder.getClientOrder().getClientOrderId(), Status.COMPLETED);
        return "redirect:/client-orders/new-transport-order";
    }

    @PostMapping("/save")
    public String saveClientOrder(
            @RequestParam Long clientId,
            @RequestParam(required = false) List<Long> goodsIds) {

        Client client = clientService.getClientById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found or deleted"));

        Set<Goods> goods = new HashSet<>(goodsService.getGoodsByIds(goodsIds));

        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(client);
        clientOrder.setOrderDate(new Date());
        clientOrder.setInvoiceType(client.getInvoiceType());
        clientOrder.setTotalAmount(goodsService.calculateTotalAmount(goods));
        clientOrder.setGoods(goods);

        clientOrderService.createClientOrder(clientOrder);
        return "redirect:/client-orders/new-transport-order";
    }

}
