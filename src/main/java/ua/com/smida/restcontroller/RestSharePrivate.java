package ua.com.smida.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping(path = "/api/v1/private/")
public class RestSharePrivate {

    private final ShareService shareService;

    @Autowired
    public RestSharePrivate(ShareService shareService) {
        this.shareService = shareService;
    }

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Share> create(@RequestBody Share share) {

        Share candidate = shareService.create(share);
        if (candidate == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(share, HttpStatus.CREATED);
    }


    @PutMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Share> update(@PathVariable("id") Long id, @RequestBody Share share) {
        Share update = shareService.update(id, share);

        if (update.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(update, HttpStatus.CREATED);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ShareDtoPrivate>> getAll() {
        List<Share> list = shareService.findAll();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ShareDtoPrivate> publicList = get(list);

        return new ResponseEntity<>(publicList, HttpStatus.OK);
    }

    @GetMapping(path = "/pages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Share>> pages(@RequestParam("pageNo") Integer pageNo,
        @RequestParam("pageSize") Integer pageSize, @RequestParam("sortBy") String... orderBy) {

        Pageable pageable = PageRequest
            .of(pageNo - 1, pageSize,
                Sort.by(orderBy).descending().and(Sort.by(orderBy)).and(Sort.by(orderBy)));
        List<Share> collect = shareService.findAll(pageable).stream().collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
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
