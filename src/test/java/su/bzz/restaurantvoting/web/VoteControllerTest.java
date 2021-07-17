package su.bzz.restaurantvoting.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import su.bzz.restaurantvoting.VoteTestUtil;
import su.bzz.restaurantvoting.model.Vote;
import su.bzz.restaurantvoting.repository.VoteRepository;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static su.bzz.restaurantvoting.TestData.*;
import static su.bzz.restaurantvoting.VoteTestUtil.asVote;
import static su.bzz.restaurantvoting.VoteTestUtil.jsonMatcher;
import static su.bzz.restaurantvoting.web.VoteController.URL_VOTE;


class VoteControllerTest extends AbstractControllerTest {

    @Autowired
    VoteRepository voteRepository;

    @WithUserDetails(value = USER_MAIL)
    @Test
    void createVoting() throws Exception {
        int restaurantId = restaurant1.id();
        Vote voteCreated = asVote(perform(MockMvcRequestBuilders.post(URL_VOTE + "/" + restaurantId))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn());

        int id = voteCreated.id();
        vote1.setId(id);
        Vote voteDB = voteRepository.findById(id).orElseThrow();
        Assertions.assertEquals(vote1.getRestaurant(), voteDB.getRestaurant());
        Assertions.assertEquals(vote1.getUser().getId(), voteDB.getUser().getId());
    }

    @WithUserDetails(value = USER2_MAIL)
    @Test
    void updateVote() throws Exception {
        int restaurantId = restaurant2.id();
        perform(MockMvcRequestBuilders.put(URL_VOTE + "/" + restaurantId))
                .andExpect(status().isNoContent())
                .andDo(print());
        Vote voteDB = voteRepository.findById(voteUpdate.id()).orElseThrow();
        Assertions.assertEquals(voteUpdate.getRestaurant().getName(), voteDB.getRestaurant().getName());
        Assertions.assertEquals(voteUpdate.getRestaurant().id(), voteDB.getRestaurant().id());
        Assertions.assertEquals(voteUpdate.getUser().getId(), voteDB.getUser().getId());
    }

    @WithUserDetails(value = USER_MAIL)
    @Test
    void getRestaurantsWithVoteTodaySortVote() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_VOTE + "/all"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(List.of(resultVoting, resultVoting2), VoteTestUtil::assertEquals));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_VOTE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void postVoteWithInvalidRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.post(URL_VOTE + "/200"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.message").value("422 UNPROCESSABLE_ENTITY " +
                        "\"Not found restaurant with id 200\""));
    }

    @WithUserDetails(value = USER2_MAIL)
    @Test
    void invalidVotingAgain() throws Exception {
        perform(MockMvcRequestBuilders.post(URL_VOTE + "/1"))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(jsonPath("$.violations[0].message").value("You can vote only once " +
                        "a day, but you can update your decision before 11:00 "));
    }
}