package pl.cinema.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name="user_ticket")
public class UserTicket {

    @Id
    @Column(name = "ticket_id")
    private int ticketid;

    @Column(name = "user_id")
    private int userid;

    @Column(name = "price")
    private double price;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private Reservation reservation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;*/

    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

 /*   public Reservation getReservation() {return reservation; }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/
}
