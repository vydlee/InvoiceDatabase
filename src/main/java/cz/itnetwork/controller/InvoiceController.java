package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.service.InvoiceService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }

    @GetMapping("/invoices")
    public List<InvoiceDTO> getInvoice() {
        return invoiceService.getAll();
    }

    @GetMapping("/identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getPurchases(@PathVariable String identificationNumber) {
        return invoiceService.getPurchases(identificationNumber);
    }

    @GetMapping("/identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getSales(@PathVariable String identificationNumber) {
        return invoiceService.getSales(identificationNumber);
    }

    @GetMapping("/invoices/{invoiceId}")
    public InvoiceDTO getInvoiceDetails(@PathVariable Long invoiceId) {
        return invoiceService.getInvoiceDetails(invoiceId);
    }

    @PutMapping({"/invoices/{id}", "/invoices/{id}"})
    public InvoiceDTO editInvoices(@PathVariable("id") Long invoiceId, @RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.editInvoices(invoiceId, invoiceDTO);
    }

    @DeleteMapping("/invoices/{invoiceId}")
    public ResponseEntity<Void> deleteInvoices(@PathVariable Long invoiceId) {
        // Logic for delete by invoiceId
        invoiceService.deleteInvoices(invoiceId);
        // Return status 204 NO_CONTENT
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invoices/statistics")
    public InvoiceStatisticsDTO getStatistics() {
        return invoiceService.getStatistics();
    }
}
