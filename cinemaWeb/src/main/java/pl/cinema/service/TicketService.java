package pl.cinema.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class TicketService {
    private Date day;
    private Time time;
    private int room;
    private List<Integer> availablePlaces;

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
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

    public List<Integer> getAvailablePlaces() {
        return availablePlaces;
    }

    public void setAvailablePlaces(List<Integer> availablePlaces) {
        this.availablePlaces = availablePlaces;
    }
}
