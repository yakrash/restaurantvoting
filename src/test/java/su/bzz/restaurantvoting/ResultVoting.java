package su.bzz.restaurantvoting;

import lombok.*;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.to.ResultVotingInt;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ResultVoting implements ResultVotingInt, Serializable {
    private final Restaurant restaurant;
    private final int votes;
}
