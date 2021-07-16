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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static su.bzz.restaurantvoting.TestData.*;
import static su.bzz.restaurantvoting.VoteTestUtil.*;
import static su.bzz.restaurantvoting.util.VoteUtil.getVoteTo;
import static su.bzz.restaurantvoting.web.VoteController.URL_VOTE;

class VoteControllerTest extends AbstractControllerTest {

    @Autowired
    VoteRepository voteRepository;

    @WithUserDetails(value = USER_MAIL)
    @Test
    void voting() throws Exception {
        int restaurantId = restaurant1.id();
        Vote voteCreated = asVote(perform(MockMvcRequestBuilders.post(URL_VOTE + "/" + restaurantId))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(getVoteTo(vote1), VoteTestUtil::assertEquals)).andReturn());
        Vote voteDB = voteRepository.findById(voteCreated.id()).orElseThrow();
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
}