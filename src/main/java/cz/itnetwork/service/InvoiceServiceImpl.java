package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    public InvoiceDTO addInvoice(InvoiceDTO data) {

        InvoiceEntity invoiceEntity = invoiceMapper.toEntity(data);
        data.setBuyer(personMapper.toDTO(personRepository.getReferenceById(data.getBuyer().getId())));
        data.setSeller(personMapper.toDTO(personRepository.getReferenceById(data.getSeller().getId())));
        invoiceEntity = invoiceRepository.saveAndFlush(invoiceEntity);

        return data;
    }

    @Override
    public List<InvoiceDTO> getAll() {
       return invoiceRepository.findAll()
               .stream()
               .map(x -> invoiceMapper.toDTO(x))
               .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getPurchases(String identificationNumber) {
        List<PersonEntity> persons = personRepository.findByIdentificationNumber(identificationNumber);

        return persons
                .stream()
                .map(PersonEntity::getPurchases)
                .flatMap(Collection::stream)
                .map(y -> invoiceMapper.toDTO(y))
                .collect(Collectors.toList());
    }


    @Override
    public List<InvoiceDTO> getSales(String identificationNumber) {
        List<PersonEntity> persons = personRepository.findByIdentificationNumber(identificationNumber);

        return persons
                .stream()
                .map(PersonEntity::getSales)
                .flatMap(Collection::stream)
                .map(y -> invoiceMapper.toDTO(y))
                .collect(Collectors.toList());
    }
    @Override
    public InvoiceDTO getInvoiceDetails(Long invoiceId) {
        InvoiceEntity invoice = invoiceRepository.getReferenceById(invoiceId);
        return invoiceMapper.toDTO(invoice);
    }

    @Override
    public InvoiceDTO editInvoices(Long invoiceId, InvoiceDTO invoiceDTO) {
        if (!invoiceRepository.existsById(invoiceId)) {
            throw new EntityNotFoundException("Invoice with id " + invoiceId + " wasn´t found in database.");
        }
        InvoiceEntity entity = invoiceMapper.toEntity(invoiceDTO);
        entity.setId(invoiceId);
        InvoiceEntity saved = invoiceRepository.save(entity);
        return invoiceMapper.toDTO(saved);
    }

    @Override
    public InvoiceDTO deleteInvoices(Long invoiceId) {
        InvoiceEntity invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(EntityNotFoundException::new);
        InvoiceDTO model = invoiceMapper.toDTO(invoice);
        invoiceRepository.delete(invoice);
        return model;
    }

  @Override
  public InvoiceStatisticsDTO getStatistics() {
      InvoiceStatisticsDTO statistics = new InvoiceStatisticsDTO();
      Long currentYearSum = invoiceRepository.getCurrentYearSum();
      Long allTimeSum = invoiceRepository.getAllTimeSum();
      Long invoicesCount = invoiceRepository.getInvoices();

      statistics.setCurrentYearSum(currentYearSum != null ? currentYearSum : 0);
      statistics.setAllTimeSum(allTimeSum != null ? allTimeSum : 0);
      statistics.setInvoicesCount(invoicesCount != null ? invoicesCount : 0);

      return statistics;
  }

}