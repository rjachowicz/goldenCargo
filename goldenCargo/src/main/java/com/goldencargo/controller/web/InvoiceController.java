package com.goldencargo.controller.web;

import com.goldencargo.model.data.InvoiceType;
import com.goldencargo.model.data.PaymentStatus;
import com.goldencargo.model.entities.Invoice;
import com.goldencargo.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String getAllInvoices(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        model.addAttribute("invoices", invoices);
        return "invoices/main";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("invoiceTypes", InvoiceType.values());
        model.addAttribute("paymentStatuses", PaymentStatus.values());
        return "invoices/create";
    }

    @PostMapping("/create")
    public String createInvoice(@ModelAttribute Invoice invoice) {
        invoiceService.createInvoice(invoice);
        return "redirect:/invoices";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if (invoice.isPresent()) {
            model.addAttribute("invoice", invoice.get());
            model.addAttribute("invoiceTypes", InvoiceType.values());
            model.addAttribute("paymentStatuses", PaymentStatus.values());
            return "invoices/edit";
        }
        return "redirect:/invoices";
    }

    @PostMapping("/update/{id}")
    public String updateInvoice(@PathVariable Long id, @ModelAttribute Invoice invoiceDetails) {
        invoiceService.updateInvoice(id, invoiceDetails);
        return "redirect:/invoices";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        Optional<Invoice> invoice = invoiceService.getInvoiceById(id);
        if (invoice.isPresent()) {
            model.addAttribute("invoice", invoice.get());
            return "invoices/details";
        }
        return "redirect:/invoices";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        return invoiceService.deleteInvoice(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
