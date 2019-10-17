package ua.com.smida.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.smida.model.Share;
import ua.com.smida.service.ShareService;
import ua.com.smida.transfer.ShareDtoPrivate;

@RestController
@RequestMapping(path = "/api/v1/private")
@Slf4j
public class RestSharePrivate {

    private final ShareService shareService;

    @Autowired
    public RestSharePrivate(ShareService shareService) {
        this.shareService = shareService;
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Share> create(@Valid @RequestBody Share share) {
        Share candidate = shareService.create(share);
        if (candidate == null) {
            log.info("in create, share cannot be saved");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("in create, share saved successfully : {}", share);
        return new ResponseEntity<>(share, HttpStatus.CREATED);
    }


    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Share> update(@PathVariable("id") Long id,
        @Valid @RequestBody Share share) {
        Share update = shareService.update(id, share);
        if (update.getId() == null) {
            log.info("in update, share with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("in update, share change successfully : {}", share);
        return ResponseEntity.ok().body(update);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ShareDtoPrivate>> getAll() {
        List<Share> list = shareService.findAll();
        if (list.isEmpty()) {
            log.info("in all, list empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ShareDtoPrivate> publicList = get(list);
        log.info("in all, list have size : {}", publicList.size());
        return new ResponseEntity<>(publicList, HttpStatus.OK);
    }

    @GetMapping(path = "/pages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ShareDtoPrivate>> pages(
        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = "version") String... orderBy) {

        List<Share> collect = shareService
            .findAll(RestControllerUtils.getPageable(pageNo, pageSize, orderBy))
            .stream()
            .collect(Collectors.toList());
        log.info(
            "in pages, list shares with filter number pages -{}, page size- {}, sort by- {} found successfully ",
            pageNo,
            pageSize, orderBy);
        return new ResponseEntity<>(get(collect), HttpStatus.OK);
    }

    private List<ShareDtoPrivate> get(List<Share> list) {
        List<ShareDtoPrivate> publicList = new ArrayList<>();
        for (Share share : list) {
            ShareDtoPrivate aPublic = ShareDtoPrivate.hidden(share);
            publicList.add(aPublic);
        }
        return publicList;
    }

}
