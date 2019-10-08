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
public class ShareDtoPrivate {

    private Long id;
    private String comments;
    private Integer capital;
    private Integer codeCompany;
    private Integer quantity;
    private Double amount;
    private Double price;
    private Double duty;
    private String createDate;
    private String modifyDate;
    private Integer version;

    public static ShareDtoPrivate hidden(Share share) {
        return ShareDtoPrivate.builder()
            .comments(share.getComments())
            .capital(share.getCapital())
            .codeCompany(share.getCodeCompany())
            .quantity(share.getQuantity())
            .amount(share.getAmount())
            .price(share.getPrice())
            .createDate(
                share.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .modifyDate(
                share.getModifyDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .version(share.getVersion())
            .build();
    }

}
