package pl.cinema.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cinema.model.UserTicket;

import java.util.List;

@Repository("UserTicketRepository")
public interface UserTicketRepository extends CrudRepository<UserTicket,Integer>{
    @Query("select ticketid from UserTicket u where user_id=:id")
    List<Integer> findTicketsId(@Param("id")int id);
}
