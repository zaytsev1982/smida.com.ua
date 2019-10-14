package ua.com.smida.restcontroller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

class RestControllerUtils {

    static Pageable getPageable(
        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = "version") String[] orderBy) {
        return PageRequest
            .of(pageNo - 1, pageSize,
                Sort.by(orderBy).descending().and(Sort.by(orderBy)).and(Sort.by(orderBy)));
    }
}
