package pl.cinema.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cinema.model.GuestTicket;

@Repository("GuestTicketRepository")
public interface GuestTicketRepository extends CrudRepository<GuestTicket,Integer>{
}
