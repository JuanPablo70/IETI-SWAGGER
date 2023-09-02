package org.adaschool.api.controller.user;

import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserDto;
import org.adaschool.api.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users/")
public class UsersController {

    private final UsersService usersService;

    public UsersController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User userToSave = new User(userDto);
        User userSaved = usersService.save(userToSave);
        URI createdUserUri = URI.create(userSaved.toString());
        return ResponseEntity.created(createdUserUri).body(null);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = usersService.all();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        Optional<User> user = usersService.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return ResponseEntity.ok(user.get());
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable("id") String id) {
        Optional<User> userInDb = usersService.findById(id);
        if (userInDb.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        userInDb.get().update(userDto);
        usersService.save(userInDb.get());
        return ResponseEntity.ok(userInDb.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        Optional<User> userInDb = usersService.findById(id);
        if (userInDb.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        usersService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
