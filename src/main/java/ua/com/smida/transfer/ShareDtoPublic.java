package ua.com.smida.transfer;

import java.time.LocalDateTime;
import lombok.Data;
import ua.com.smida.model.Share;

@Data
public class ShareDtoPublic {

    private Integer capital;
    private Integer codeCompany;
    private Integer quantity;
    private Double amount;
    private LocalDateTime createDate;

    public ShareDtoPublic(Share share) {
        this.capital = share.getCapital();
        this.codeCompany = share.getCodeCompany();
        this.quantity = share.getQuantity();
        this.amount = share.getAmount();
        this.createDate = share.getCreateDate();
    }

}
