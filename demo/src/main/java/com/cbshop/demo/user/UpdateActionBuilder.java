package com.cbshop.demo.user;

import com.cbshop.demo.user.model.User;
import com.cbshop.demo.user.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@Component
public class UpdateActionBuilder {

    private final Map<Predicate<UserDTO>, BiConsumer<User, UserDTO>> updatesMap =
            Map.of(
                    currentUser -> currentUser.getFirstName() != null, (userById, userUpdates) ->
                            userById.setFirstName(userUpdates.getFirstName()),

                    currentUser -> currentUser.getLastName() != null, (userById, userUpdates) ->
                            userById.setLastName(userUpdates.getLastName())
            );

    public User buildUpdatesUser(UserDTO userUpdates, User currentUser) {
        updatesMap
                .forEach((userPredicate, userBiConsumer) -> {
                    if (userPredicate.test(userUpdates)) {
                        userBiConsumer.accept(currentUser, userUpdates);
                    }
                });
        return currentUser;
    }
}
