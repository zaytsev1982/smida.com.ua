package ua.com.smida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.smida.model.Share;

public interface ShareRepository extends JpaRepository<Share, Long> {

}
