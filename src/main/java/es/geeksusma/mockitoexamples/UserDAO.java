package es.geeksusma.mockitoexamples;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class UserDAO implements Save, Update, Delete, Get {

    private final UserRepository userRepository;
    private final Mapper<UserEntity, User> userMapper;

    UserDAO(final UserRepository userRepository, final Mapper<UserEntity, User> userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User byId(final Long id) {
        return getUserById(id).map(userMapper::toTarget).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll().stream().map(userMapper::toTarget).collect(Collectors.toList());
    }

    @Override
    public void persist(final User nonPersisted) {
        try {
            userRepository.save(UserEntity.UserEntityBuilder.anUserEntity()
                    .name(nonPersisted.getName())
                    .lastName(nonPersisted.getLastName())
                    .enabled(nonPersisted.isEnabled())
                    .build());
        } catch (PersistenceException e) {
            throw new UserDataException("The user could not be saved", e);
        }
    }

    @Override
    public void setStatus(final Long id, final boolean status) {
        try {
            Optional<UserEntity> existingUser = getUserById(id);
            if (existingUser.isPresent()) {
                userRepository.updateStatus(id, status);
            }
        } catch (PersistenceException e) {
            throw new UserDataException("The user could not be updated", e);
        }
    }

    @Override
    public void delete(final Long id) {
        try {
            Optional<UserEntity> existingUser = getUserById(id);
            if (existingUser.isPresent()) {
                userRepository.deleteById(id);
            }
        } catch (PersistenceException e) {
            throw new UserDataException("The user could not be deleted", e);
        }
    }

    private Optional<UserEntity> getUserById(Long id) {
        return this.userRepository.findById(id);
    }
}