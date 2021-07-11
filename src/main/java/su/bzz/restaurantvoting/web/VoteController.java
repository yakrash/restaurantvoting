package su.bzz.restaurantvoting.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.bzz.restaurantvoting.repository.VoteRepository;

@RestController
@RequestMapping(value = VoteController.URL_VOTE, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Validated
public class VoteController {
    public static final String URL_VOTE = "/api/vote";

    private final VoteRepository voteRepository;
}
