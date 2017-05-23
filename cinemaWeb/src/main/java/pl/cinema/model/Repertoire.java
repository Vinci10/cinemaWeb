package pl.cinema.model;

import javax.persistence.*;

@Entity
@Table(name = "repertoire")
public class Repertoire {

    @EmbeddedId
    private RepertoireId id;

    @Column(name = "room_id")
    private int room;

    public RepertoireId getId() {
        return id;
    }

    public void setId(RepertoireId id) {
        this.id = id;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
