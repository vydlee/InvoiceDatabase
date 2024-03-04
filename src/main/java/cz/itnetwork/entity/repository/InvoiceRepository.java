package cz.itnetwork.entity.repository;


import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    @Query(value = "SELECT SUM(price) FROM invoice WHERE YEAR (issued) = YEAR(CURRENT_DATE)")
    Long getCurrentYearSum();

    @Query(value = "SELECT SUM(price) FROM invoice")
    Long getAllTimeSum();

    @Query(value = "SELECT COUNT(*) FROM invoice")
    Long getInvoices();

}
