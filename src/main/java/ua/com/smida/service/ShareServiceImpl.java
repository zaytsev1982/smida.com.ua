package ua.com.smida.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.smida.exception.CompanyCodeNotFoundException;
import ua.com.smida.exception.ShareNotFoundException;
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
        share.setAmount(share.getPrice() * share.getQuantity());
        log.info("share created successfully, details : {}", share);
        return repository.save(share);
    }

    @Override
    public Share update(Long id, Share share) {
        return repository.findById(id)
            .map(candidate -> {
                candidate.setComments(share.getComments());
                candidate.setCodeCompany(share.getCodeCompany());
                candidate.setCapital(share.getCapital());
                candidate.setQuantity(share.getQuantity());
                candidate.setDuty(share.getDuty());
                candidate.setPrice(share.getPrice());
                candidate.setQuantity(share.getQuantity());
                candidate.setAmount(share.getPrice() * share.getQuantity());
                return repository.save(candidate);
            }).orElseThrow(() -> new IllegalArgumentException("share not found"));

    }

    @Override
    public Share findOne(Long id) {
        return repository
            .findById(id).orElseThrow(
                () -> new ShareNotFoundException("share with id: " + id + " not found"));
    }

    @Override
    public List<Share> findAllByCodeCompany(Integer code) {
        List<Share> shareList = repository.findAllByCodeCompany(code);
        if (shareList.isEmpty()) {
            log.info("in findAllByCodeCompany, code {} not found", code);
            throw new CompanyCodeNotFoundException("code :" + code + " not found");
        }
        log.info("in findAllByCodeCompany, size share - {}", shareList.size());
        return shareList;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Share> findAll(Pageable pageable) {

        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Share> findAll() {
        if (repository.findAll().isEmpty()) {
            log.info("in findAll, list is empty");
        }
        log.info("in findAllByCodeCompany, size share - {}", repository.findAll().size());
        return repository.findAll();
    }
}
