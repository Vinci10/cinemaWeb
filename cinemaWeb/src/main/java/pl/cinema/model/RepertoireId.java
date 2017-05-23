package pl.cinema.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

@Embeddable
public class RepertoireId implements Serializable {

    @Column(name = "movie_name")
    private String moviename;
    @Column(name = "day")
    @DateTimeFormat(pattern = "hh:mm")
    private Date day;
    @Column(name = "time")
    private Time time;

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Time getTime() { return time; }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepertoireId  myKey = (RepertoireId ) o;

        if (!moviename.equals(myKey.moviename)) return false;
        if (!day.equals(myKey.day)) return false;
        return time.equals(myKey.time);
    }

    @Override
    public int hashCode() {
        int result = moviename.hashCode();
        result = 31 * result + day.hashCode();
        result = 31 * result + time.hashCode();
        return result;
    }
}