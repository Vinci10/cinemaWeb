package pl.cinema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Embeddable
public class RepertoireId implements Serializable {

    @Column(name = "room")
    private int room;
    @Column(name = "day")
    private Date day;
    @Column(name = "time")
    private Time time;

    public int getRoom() { return room;}

    public void setRoom(int room) { this.room = room;}

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

        if (room != ((RepertoireId) o).room) return false;
        if (!day.equals(myKey.day)) return false;
        return time.equals(myKey.time);
    }

    @Override
    public int hashCode() {
        int result = day.hashCode();
        result = 31 * result + room;
        result = 31 * result + time.hashCode();
        return result;
    }
}