package pl.cinema.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cinema.model.UserTicket;

@Repository("UserTicketRepository")
public interface UserTicketRepository extends CrudRepository<UserTicket,Integer>{
}
