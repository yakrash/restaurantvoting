package su.bzz.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "date", "restaurant_id"},
        name = "dish_unique_idx")})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@Slf4j
public class Dish extends BaseEntity {
    public Dish(Integer id, String name, Long priceInDollars, LocalDate date, Restaurant restaurant) {
        this(name, priceInDollars, date, restaurant);
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 3, max = 128)
    private String name;

    @Column(name = "price_in_dollars", nullable = false)
    @NotNull
    @Range(min = 1, max = 500)
    private Long priceInDollars;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    @ToString.Exclude
    private Restaurant restaurant;
}
