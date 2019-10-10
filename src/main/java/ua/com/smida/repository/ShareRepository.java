package ua.com.smida.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.smida.model.Share;

public interface ShareRepository extends JpaRepository<Share, Long> {


    List<Share> findAllByCodeCompany(Integer codeCompany);

    Page<Share> findAll(Pageable pageable);
}
