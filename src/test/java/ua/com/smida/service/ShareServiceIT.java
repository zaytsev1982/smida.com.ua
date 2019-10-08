package ua.com.smida.service;


import static org.hamcrest.Matchers.is;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.smida.model.Share;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource(value = "classpath:database/schema.sql")
public class ShareServiceIT {

    @Autowired
    private ShareService shareService;


    @Test
    public void shouldBeCreate() {
        Share share = Share
            .builder()
            .comments("company 10")
            .capital(5000050)
            .codeCompany(36540)
            .quantity(250000)
            .price(125.2)
            .duty(19.3)
            .build();
        Share candidate = shareService.create(share);
        Share maybeShare = shareService.findOne(candidate.getId());
        Assert.assertNotNull(maybeShare);
        Assert.assertThat(maybeShare.getCapital(), is(5000050));
        Assert.assertThat(maybeShare.getQuantity(), is(250000));
    }

    @Test
    public void shouldBeUpdate() {
        Share share = shareService.findOne(1L);
        share.setPrice(1.1);
        Share candidate = shareService.update(1L, share);
        Assert.assertThat(candidate.getPrice(), is(1.1));
    }

    @Test
    public void shouldBeFindById() {
        Long id = 1L;
        Share one = shareService.findOne(id);
        Assert.assertNotNull(one);
        Assert.assertEquals(id, one.getId());
    }

    @Test
    public void shouldBeFindAllByCodeCompany() {
        Integer code = 1245;
        List<Share> codeCompany = shareService.findAllByCodeCompany(code);
        long count = codeCompany.stream().filter(c -> c.getCodeCompany().equals(code)).count();
        Assert.assertEquals(count, 2);

    }

    @Test
    public void shouldBeFindAll() {
        List<Share> shares = shareService.findAll();
        Assert.assertNotNull(shares);
        Assert.assertEquals(shares.size(), 6);
    }

}