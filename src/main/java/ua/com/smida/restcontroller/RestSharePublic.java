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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.smida.model.Share;
import ua.com.smida.service.ShareService;
import ua.com.smida.transfer.ShareDtoPublic;

@RestController
@RequestMapping(path = "/api/v1/public")
public class RestSharePublic {

    private final ShareService shareService;

    @Autowired
    public RestSharePublic(ShareService shareService) {
        this.shareService = shareService;
    }


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ShareDtoPublic> getOne(@PathVariable("id") Long id) {

        Share candidate = shareService.findOne(id);
        if (candidate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ShareDtoPublic shareDtoPublic = ShareDtoPublic.open(candidate);
        return new ResponseEntity<>(shareDtoPublic, HttpStatus.OK);
    }

    @GetMapping(path = "/company", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ShareDtoPublic>> getAll(@RequestParam("codeCompany") Integer code) {
        List<Share> list = shareService.findAllByCodeCompany(code);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ShareDtoPublic> publicList = get(list);

        return new ResponseEntity<>(publicList, HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ShareDtoPublic>> getAll() {
        List<Share> list = shareService.findAll();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ShareDtoPublic> publicList = get(list);

        return new ResponseEntity<>(publicList, HttpStatus.OK);
    }


    @GetMapping(path = "/pages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ShareDtoPublic>> pages(
        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = "version") String... orderBy) {

        Pageable pageable = PageRequest
            .of(pageNo - 1, pageSize,
                Sort.by(orderBy).descending().and(Sort.by(orderBy)).and(Sort.by(orderBy)));
        List<Share> collect = shareService.findAll(pageable).stream().collect(Collectors.toList());
        List<ShareDtoPublic> publicList = get(collect);

        return new ResponseEntity<>(publicList, HttpStatus.OK);
    }

    private List<ShareDtoPublic> get(List<Share> list) {
        List<ShareDtoPublic> publicList = new ArrayList<>();
        for (Share share : list) {
            ShareDtoPublic aPublic = ShareDtoPublic.open(share);
            publicList.add(aPublic);
        }
        return publicList;
    }
}
