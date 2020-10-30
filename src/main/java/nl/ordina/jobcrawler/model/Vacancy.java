package nl.ordina.jobcrawler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nl.ordina.jobcrawler.exception.VacancyURLMalformedException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vacancy {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;
    @NotNull
    private String vacancyURL;
    private String title;
    private String broker;
    private String vacancyNumber;
    private Integer hours;
    private String salary;
    @JsonFormat(timezone = "Europe/Amsterdam", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postingDate;
    @Column(columnDefinition = "TEXT")
    private String about;
    private String company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    Location location;

    public boolean hasLocation() {
        return this.location != null;
    }

}
