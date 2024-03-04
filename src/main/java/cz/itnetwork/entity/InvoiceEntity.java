package cz.itnetwork.entity;

import cz.itnetwork.entity.PersonEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import cz.itnetwork.constant.Countries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity(name = "invoice")
@Getter
@Setter
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long invoiceNumber;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double vat;

    @Column(nullable = false)
    private String note;

    private Date issued;

    private Date dueDate;

    @ManyToOne
    private PersonEntity buyer;

    @ManyToOne
    private PersonEntity seller;
}
