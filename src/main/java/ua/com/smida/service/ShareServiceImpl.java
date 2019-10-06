package ua.com.smida.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.smida.model.Share;
import ua.com.smida.repository.ShareRepository;

@Service
@Transactional
@Slf4j
public class ShareServiceImpl implements ShareService {

    private final ShareRepository repository;

    @Autowired
    public ShareServiceImpl(ShareRepository repository) {
        this.repository = repository;
    }

    @Override
    public Share create(Share share) {
        if (share.getId() != null) {
           return repository.save(share);
        }
        share.setAmount(share.getPrice() * share.getQuantity());
        log.info("share created successfully, details : {}", share);
        return repository.save(share);
    }

    @Override
    public Share update(Long id) {
        return null;
    }

    @Override
    public Share findOne(Long id) {
        return repository
            .findById(id).orElseThrow(
                () -> new IllegalArgumentException("not found"));
    }
}
