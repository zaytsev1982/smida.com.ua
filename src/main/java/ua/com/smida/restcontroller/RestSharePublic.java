package ua.com.smida.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "/api/v1/public/")
@Slf4j
public class RestSharePublic {

    private final ShareService shareService;

    @Autowired
    public RestSharePublic(ShareService shareService) {
        this.shareService = shareService;
    }


    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ShareDtoPublic> getOne(@PathVariable("id") Long id) {

        Share candidate = shareService.findOne(id);
        if (candidate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ShareDtoPublic shareDtoPublic = ShareDtoPublic.open(candidate);
        return new ResponseEntity<>(shareDtoPublic, HttpStatus.OK);
    }

    @GetMapping(path = "company", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ShareDtoPublic>> getAll(@RequestParam("codeCompany") Integer code) {
        List<Share> list = shareService.findAllByCodeCompany(code);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ShareDtoPublic> publicList = get(list);

        return new ResponseEntity<>(publicList, HttpStatus.OK);
    }

    @GetMapping(path = "all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ShareDtoPublic>> getAll() {
        List<Share> list = shareService.findAll();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(get(list), HttpStatus.OK);
    }


    @GetMapping(path = "pages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ShareDtoPublic>> pages(
        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = "version") String[] orderBy,
        @RequestParam(value = "secondBy", defaultValue = "version") String[] secondBy) {
        List<Share> shares = shareService
            .findAll(RestControllerUtils.getPageable(pageNo, pageSize, orderBy, secondBy))
            .stream()
            .collect(Collectors.toList());
        log.info(
            "in pages, list shares with filter number pages -{}, page size- {}, sort by- {} found successfully ",
            pageNo,
            pageSize, orderBy);
        return new ResponseEntity<>(get(shares), HttpStatus.OK);
    }

    private List<ShareDtoPublic> get(List<Share> list) {
        List<ShareDtoPublic> publicList = new ArrayList<>();
        list.forEach(l -> {
            publicList.add(ShareDtoPublic.open(l));
        });
        return publicList;
    }
}
