package com.cbshop.demo.user.service;

import com.cbshop.demo.exceptions.controlleradvice.ItemNotFoundException;
import com.cbshop.demo.user.UpdateActionBuilder;
import com.cbshop.demo.user.model.User;
import com.cbshop.demo.user.model.UserDTO;
import com.cbshop.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final UpdateActionBuilder updateActionBuilder;

    public Page<User> read(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User readById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("There is no such user"));
    }

    public void delete(long id) {
        userRepository
                .delete(readById(id));
    }

    public User update(long id, UserDTO userUpdates) {
        return updateActionBuilder
                .buildUpdatesUser(userUpdates, readById(id));
    }
}
