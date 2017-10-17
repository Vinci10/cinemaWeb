package pl.cinema.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "guest_ticket")
public class GuestTicket {

    @Id
    @Column(name = "ticket_id")
    private int ticketid;
    @Column(name = "email")
    @Email(message = "Niepoprawny adres e-mail")
    @NotEmpty(message = "Proszę wpisać adres e-mail")
    private String email;
    @Column(name = "name")
    @NotEmpty(message = "Proszę wpisać imię")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "Proszę wpisać nazwisko")
    private String lastName;
    @Column(name = "price")
    private double price;

    public int getTicketid() {
        return ticketid;
    }

    public void setTicketid(int ticketid) {
        this.ticketid = ticketid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
