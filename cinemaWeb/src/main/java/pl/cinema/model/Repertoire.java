package pl.cinema.model;

import javax.persistence.*;

@Entity
@Table(name = "repertoire")
public class Repertoire {

    @EmbeddedId
    private RepertoireId id;

    @Column(name = "movie_name")
    private String moviename;

    public RepertoireId getId() {
        return id;
    }

    public void setId(RepertoireId id) {
        this.id = id;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

}
