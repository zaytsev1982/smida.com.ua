package ua.com.smida.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.smida.model.Share;
import ua.com.smida.service.ShareService;

@RestController
@RequestMapping(path = "/api/v1/public")
public class RestSharePublic {

    private final ShareService shareService;

    @Autowired
    public RestSharePublic(ShareService shareService) {
        this.shareService = shareService;
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Share> create(@RequestBody Share share) {
        Share candidate = shareService.create(share);
        if (candidate == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(share, HttpStatus.CREATED);
    }


    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Share> update(@PathVariable("id") Long id, @RequestBody Share share) {
        shareService.findOne(id);
        Share build = Share
            .builder()
            .capital(share.getCapital())
            .comments(share.getComments())
            .codeCompany(share.getCodeCompany())
            .duty(share.getDuty())
            .quantity(share.getQuantity())
            .price(share.getPrice())
            .build();
        Share candidate = shareService.create(build);
        if (candidate == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(candidate, HttpStatus.CREATED);
    }
}
