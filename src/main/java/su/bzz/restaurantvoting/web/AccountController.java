package su.bzz.restaurantvoting.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import su.bzz.restaurantvoting.model.User;
import su.bzz.restaurantvoting.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    @ResponseBody
    public User get(@PathVariable Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
