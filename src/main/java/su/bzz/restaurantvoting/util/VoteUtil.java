package su.bzz.restaurantvoting.util;

import su.bzz.restaurantvoting.model.Vote;
import su.bzz.restaurantvoting.to.VoteTo;

public class VoteUtil {

    public static VoteTo getVoteTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getRestaurant().getName(), vote.getUser().id(), vote.getDate());
    }
}
