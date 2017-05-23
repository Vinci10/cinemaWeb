package pl.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.cinema.model.User;

import javax.transaction.Transactional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("Update User u set u.premium = u.premium where u.id = :y")
    @Transactional
    void updatePremium(@Param("y") int y );
}