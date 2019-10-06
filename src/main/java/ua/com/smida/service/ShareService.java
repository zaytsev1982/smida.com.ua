package ua.com.smida.service;

import ua.com.smida.model.Share;

public interface ShareService {

    Share create(Share share);

    Share update(Long id);

    Share findOne(Long id);




}
