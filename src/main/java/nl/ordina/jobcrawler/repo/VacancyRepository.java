package nl.ordina.jobcrawler.repo;

import nl.ordina.jobcrawler.model.Location;
import nl.ordina.jobcrawler.model.Vacancy;
import nl.ordina.jobcrawler.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, UUID>, JpaSpecificationExecutor<Vacancy> {

    @Transactional
    Optional<Vacancy> findByVacancyURLEquals(String url);

    @Transactional
    List<Vacancy> findByLocation_Id(UUID id);

}
