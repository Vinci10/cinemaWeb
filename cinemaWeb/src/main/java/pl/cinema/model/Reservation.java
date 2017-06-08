package pl.cinema.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity
@Table(name="reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    private int id;

    @Column(name = "room")
    @Range(min=1, max=3)
    private int room;

    @Column(name = "place")
    @Range(min=1, max=30)
    private int place;

    @Column(name = "movie_name")
    private String moviename;

    @Column(name = "day")
    private Date day;

    @Column(name = "time")
    private Time time;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ticket_snack", joinColumns = @JoinColumn(name = "ticket_id"), inverseJoinColumns = @JoinColumn(name = "snack_id"))
    private Set<Snack> snacks;

    public Set<Snack> getSnacks() { return snacks; }

    public void setSnacks(Set<Snack> snacks) { this.snacks = snacks; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
