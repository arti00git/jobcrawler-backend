package nl.ordina.jobcrawler.service;

import nl.ordina.jobcrawler.model.Vacancy;
import nl.ordina.jobcrawler.repo.VacancyRepository;
import nl.ordina.jobcrawler.service.exception.DuplicateRecordFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VacancyService {

    private VacancyRepository repository;

    @Autowired
    public VacancyService(VacancyRepository repository) {
        this.repository = repository;
    }

    /* Add */
    public Vacancy add(Vacancy vacancy) {
        return repository.save(vacancy);
    }

    /* Get */
    public List<Vacancy> getAllJobs() {
        return repository.findAll();
    }

    public List<Vacancy> getJobsWithSkill(String skill) {
        return repository.findAll()
                .stream()
                .filter(a -> a.getSkillSet().toString().toLowerCase().contains(skill.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Optional<Vacancy> getByID(UUID id) {
        return repository.findById(id);
    }

    public Vacancy doesRecordExist(String url) {
        List<Vacancy> record = repository.findByVacancyURLEquals(url);
        if(record.size() == 0)
            return null;
        else if(record.size() > 1)
            throw new DuplicateRecordFoundException(String.format("Duplicate entry found in database for url %s", url));
        else
            return record.get(0);
    }

    public List<Vacancy> getJobsByBroker(String broker) {
        return repository.findByBrokerEquals(broker);
    }

    /* Delete */
    public void delete(UUID id) {
        repository.deleteById(id);
    }


}
