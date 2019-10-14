package ua.com.smida.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "shares")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comments")
    @Length(min = 3, max = 20, message = " must be between 3 and 20 character")
    private String comments;
    @Column(name = "capital")
    @PositiveOrZero(message = " must be positive or zero")
    private Integer capital;
    @Column(name = "code_company")
    @Positive(message = " must be positive")
    private Integer codeCompany;
    @Column(name = "quantity")
    @PositiveOrZero(message = " must be positive or zero")
    private Integer quantity;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "price")
    @Positive(message = " must be positive")
    private Double price;
    @Column(name = "duty")
    @Positive(message = " must be positive")
    private Double duty;
    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @LastModifiedDate
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;
    @Version
    private Integer version;

}
