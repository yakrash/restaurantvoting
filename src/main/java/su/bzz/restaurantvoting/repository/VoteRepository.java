package su.bzz.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import su.bzz.restaurantvoting.model.Vote;
import su.bzz.restaurantvoting.to.ResultVotingInt;

import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id =:userId AND v.date = CURRENT_DATE()")
    Vote findByUserIdAndToday(@Param("userId") Integer userId);

    //    https://stackoverflow.com/a/20056058/15422633
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE VOTES SET restaurant_id=?1 WHERE id=?2",
            nativeQuery = true)
    void updateVote(Integer restaurantId, Integer idVote);

    @Query("SELECT v.restaurant as restaurant, count (*) as votes FROM Vote v " +
            "WHERE v.date = CURRENT_DATE() group by v.restaurant having COUNT(*) > 0 ORDER BY votes DESC")
    List<ResultVotingInt> getRestaurantsWithVoteTodaySortVote();
}
