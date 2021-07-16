package su.bzz.restaurantvoting.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import su.bzz.restaurantvoting.AuthUser;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.model.Vote;
import su.bzz.restaurantvoting.repository.VoteRepository;
import su.bzz.restaurantvoting.service.RestaurantService;
import su.bzz.restaurantvoting.to.ResultVotingInt;
import su.bzz.restaurantvoting.to.VoteTo;
import su.bzz.restaurantvoting.util.exception.IllegalRequestDataException;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static su.bzz.restaurantvoting.util.DateUtil.isTimeVoting;
import static su.bzz.restaurantvoting.util.VoteUtil.getVoteTo;

@RestController
@RequestMapping(value = VoteController.URL_VOTE, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Validated
public class VoteController {
    public static final String URL_VOTE = "/api/vote";

    private final VoteRepository voteRepository;
    private final RestaurantService restaurantService;

    @PostMapping("/{restaurantId}")
    public ResponseEntity<VoteTo> voting(@PathVariable Integer restaurantId,
                                         @AuthenticationPrincipal AuthUser authUser) {
        log.info("Voting for restaurantId: {}", restaurantId);

        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);

        if (!isTimeVoting()) {
            throw new IllegalRequestDataException("Vote closed, we are waiting for you tomorrow until 11 o'clock.");
        }

        Vote voteCreated = voteRepository.save(new Vote(restaurant, authUser.getUser(), LocalDate.now()));

        VoteTo voteTo = getVoteTo(voteCreated);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL_VOTE + "/")
                .buildAndExpand(restaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(voteTo);
    }

    @PutMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@PathVariable Integer restaurantId,
                           @AuthenticationPrincipal AuthUser authUser) {
        log.info("Update vote for restaurantId: {}", restaurantId);
        restaurantService.getRestaurant(restaurantId);

        if (!isTimeVoting()) {
            throw new IllegalRequestDataException("Vote closed, we are waiting for you tomorrow until 11 o'clock.");
        }

        Vote voteOld = voteRepository.findByUserIdAndToday(authUser.getUser().getId());
        if (voteOld == null) {
            throw new IllegalRequestDataException("You haven't yet voted for the restaurant");
        }
        voteRepository.updateVote(restaurantId, voteOld.id());
    }

    @GetMapping("/all")
    public List<ResultVotingInt> getRestaurantsWithVoteTodaySortVote() {
        return voteRepository.getRestaurantsWithVoteTodaySortVote();
    }
}
