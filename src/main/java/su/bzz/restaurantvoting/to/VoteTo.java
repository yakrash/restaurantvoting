package su.bzz.restaurantvoting.to;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class VoteTo {
    private final int id;
    private final String restaurantName;
    private final int userId;
    private final LocalDate registeredDate;
}
