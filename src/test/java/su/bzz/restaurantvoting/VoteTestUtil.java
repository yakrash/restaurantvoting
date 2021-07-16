package su.bzz.restaurantvoting;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import su.bzz.restaurantvoting.model.Vote;
import su.bzz.restaurantvoting.to.ResultVoting;
import su.bzz.restaurantvoting.to.VoteTo;
import su.bzz.restaurantvoting.util.JsonUtil;

import java.io.IOException;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestUtil {

    public static void assertEquals(VoteTo actual, VoteTo expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static VoteTo asVoteTo(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, VoteTo.class);
    }

    public static Vote asVote(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, Vote.class);
    }

    public static ResultVoting asResultVoting(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, ResultVoting.class);
    }

    public static ResultMatcher jsonMatcher(VoteTo expected, BiConsumer<VoteTo, VoteTo> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asVoteTo(mvcResult), expected);
    }

    public static ResultMatcher jsonMatcher(ResultVoting expected, BiConsumer<ResultVoting, ResultVoting> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asResultVoting(mvcResult), expected);
    }
}
