package su.bzz.restaurantvoting.to;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VoteTo {
    private int id;
    private String restaurantName;
    private int userId;
    private LocalDate registeredDate;


}
