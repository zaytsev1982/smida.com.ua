package ua.com.smida.transfer;

import java.time.LocalDateTime;
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
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Integer version;

    public static ShareDtoPrivate hidden(Share share) {
        return ShareDtoPrivate.builder()
            .comments(share.getComments())
            .capital(share.getCapital())
            .codeCompany(share.getCodeCompany())
            .quantity(share.getQuantity())
            .amount(share.getAmount())
            .price(share.getPrice())
            .createDate(share.getCreateDate())
            .modifyDate(share.getModifyDate())
            .version(share.getVersion())
            .build();
    }
}
