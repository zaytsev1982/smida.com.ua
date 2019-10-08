package ua.com.smida.repository.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.smida.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u join fetch u.roleSet where u.id=:id")
    User findByLogin(@Param("id") String login);
}
