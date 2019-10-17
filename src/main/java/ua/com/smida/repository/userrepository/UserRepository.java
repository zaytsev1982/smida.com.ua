package ua.com.smida.repository.userrepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.smida.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u join fetch u.roles where u.login=:name")
    Optional<User> findUserByLogin(@Param("name") String userName);
}
