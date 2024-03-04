package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface InvoiceService {

    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    List<InvoiceDTO> getAll();

    List<InvoiceDTO> getPurchases(String identificationNumber);

    List<InvoiceDTO> getSales (String identificationNumber);

    InvoiceDTO getInvoiceDetails(Long invoiceId);

    InvoiceDTO editInvoices(Long invoiceId, InvoiceDTO invoiceDTO);

    InvoiceDTO deleteInvoices(Long invoiceId);

    InvoiceStatisticsDTO getStatistics();

}
