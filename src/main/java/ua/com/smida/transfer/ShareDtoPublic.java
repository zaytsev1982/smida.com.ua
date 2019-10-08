package ua.com.smida.transfer;

import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.smida.model.Share;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareDtoPublic {

    private Integer codeCompany;
    private Integer quantity;
    private Double amount;
    private Double price;
    private String createDate;

    public static ShareDtoPublic open(Share share) {
        return ShareDtoPublic.builder()
            .codeCompany(share.getCodeCompany())
            .quantity(share.getQuantity())
            .amount(share.getAmount())
            .price(share.getPrice())
            .createDate(
                share.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .build();
    }
}
