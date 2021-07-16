package su.bzz.restaurantvoting.to;

import su.bzz.restaurantvoting.model.Restaurant;

//https://stackoverflow.com/a/46089708/15422633
public interface ResultVotingInt {
    Restaurant getRestaurant();

    int getVotes();
}
