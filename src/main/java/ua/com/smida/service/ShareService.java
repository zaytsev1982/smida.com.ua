package ua.com.smida.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.smida.model.Share;

public interface ShareService {

    Share create(Share share);

    Share update(Long id, Share share);

    Share findOne(Long id);

    List<Share> findAllByCodeCompany(Integer code);

    Page<Share> findAll(Pageable pageable);

    List<Share> findAll();
}
