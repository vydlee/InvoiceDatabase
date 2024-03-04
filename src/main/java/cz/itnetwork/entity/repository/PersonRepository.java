package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    List<PersonEntity> findByHidden(boolean hidden);

    List<PersonEntity> findByIdentificationNumber(String identificationNumber);

    @Query("SELECT new cz.itnetwork.dto.PersonStatisticsDTO(p.id as personId, p.name as personName, " +
            "COALESCE(SUM(pur.price), 0) + COALESCE(SUM(sal.price), 0) as revenue) " +
            "FROM person p " +
            "LEFT JOIN p.purchases pur " +
            "LEFT JOIN p.sales sal " +
            "GROUP BY p.id, p.name")
    List<PersonStatisticsDTO> getPersonStatistics();

}
