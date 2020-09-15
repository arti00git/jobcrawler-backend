package nl.ordina.jobcrawler.repo;

import nl.ordina.jobcrawler.model.Vacancy;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class VacancySpecifications {

    private static final String LIKE_QUERY_FORMAT = "%%%s%%";

    private VacancySpecifications() {
    }

    /**
     * Vacancy specification to query the database with JpaSpecificationExecutor.
     *
     * @param skills List of skill to filter the about attribute
     * @return predicate which can contain more predicates.
     */
    public static Specification<Vacancy> findBySkill(final Set<String> skills) {
        return (root, query, cb) ->
          cb.and(skills.stream().map(s -> cb
                    .like(root.get("about"), String
                            .format(LIKE_QUERY_FORMAT, s))).toArray(Predicate[]::new));
    }

    /**
     * This query will filter the vacancies by any search value that is entered in the search field.
     * @param value - search value
     * @return - filtered vacancies
     */
    public static Specification<Vacancy> findByValue(final String value) {
        return (root, query, cb) -> {
            List<Predicate> allPredicates = new ArrayList<>();
            allPredicates.add(cb.like(cb.lower(root.get("about")), String.format(LIKE_QUERY_FORMAT, value.toLowerCase())));
            allPredicates.add(cb.like(cb.lower(root.get("title")), String.format(LIKE_QUERY_FORMAT, value.toLowerCase())));

            return cb.or(allPredicates.toArray(new Predicate[0]));
        };
    }

}
