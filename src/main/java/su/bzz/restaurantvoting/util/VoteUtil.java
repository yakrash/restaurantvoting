package su.bzz.restaurantvoting.util;

import lombok.extern.slf4j.Slf4j;
import su.bzz.restaurantvoting.model.Vote;
import su.bzz.restaurantvoting.to.VoteTo;

@Slf4j
public class VoteUtil {

    public static VoteTo getVoteTo(Vote vote) {
        log.info("Get voteTo {}", vote.toString());
        return new VoteTo(vote.id(), vote.getRestaurant().getName(), vote.getUser().id(), vote.getDate());
    }
}
