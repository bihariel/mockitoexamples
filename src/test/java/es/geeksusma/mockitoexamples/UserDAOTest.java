package es.geeksusma.mockitoexamples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;

/**
 * More tests are missing but this class has enough tests to show how to apply the most basic mocks
 */
@ExtendWith(MockitoExtension.class)
class UserDAOTest {

    private UserDAO userDAO;

    @Mock
    private UserRepository userRepository;
    @Mock
    private Mapper<UserEntity, User> userMapper;

    //As a best practice, initialize at constructor level in a setUp method
    @BeforeEach
    void setUp() {
        userDAO = new UserDAO(userRepository, userMapper);
    }

    /**
     * Check this test to see how to make your mock return a value defined as part of your test.
     * In this case, we're going to mock our repository, in order to add to it the behaviour of an existing id (it must return an entity)
     * And in addition, we're going to mock our mapper, to ensure a value object is returned rather than just the entity
     */
    @Test
    void should_returnUser_when_exists() {
        //given
        final Long existingId = 123213L;
        final UserEntity expectedEntity = setUpBarbara(existingId);
        final User barbaraLiskov = User.UserBuilder.anUser()
                .name(expectedEntity.getName())
                .lastName(expectedEntity.getLastName())
                .build();

        given(userRepository.findById(existingId)).willReturn(Optional.of(expectedEntity));
        given(userMapper.toTarget(expectedEntity)).willReturn(
                barbaraLiskov);

        //when
        final User foundUser = userDAO.byId(existingId);

        //then
        assertThat(foundUser).isEqualToComparingFieldByField(barbaraLiskov);
    }

    /**
     * Check this test to see how to make your mock return an "empty" element, notice how this is the default behaviour and then we should not mock anything,
     * otherwise, the test, even it is green it will be marked as failed (this new feature was introduced as part of Mockito 2.x, to avoid unnecessary stubbing)
     * In addition, we can verify if the mapper was not called, since if we thrown an exception no mapping strategy must be applied
     */
    @Test
    void should_throwNotFound_when_idDoesNotExist() {
        //given
        final Long nonExistingId = 1231332423423L;

        //when
        Throwable raisedException = catchThrowable(() -> userDAO.byId(nonExistingId));

        //then
        assertThat(raisedException).isInstanceOf(UserNotFoundException.class);
        then(userMapper).shouldHaveNoInteractions();
    }

    /**
     * Check this example to see how to make your mock return a collection, and later how to do a basic assertion over the result using AssertJ
     */
    @Test
    void should_returnOnlyBarbara_when_getAll() {
        //given
        final UserEntity barbara = setUpBarbara(132L);

        given(userRepository.findAll()).willReturn(Collections.singletonList(barbara));
        given(userMapper.toTarget(barbara)).willReturn(User.UserBuilder.anUser().name(barbara.getName()).lastName(barbara.getLastName()).build());

        //when
        final List<User> users = userDAO.getAll();

        //then
        assertThat(users).extracting(User::getFullName).containsOnly("Barbara Liskov");
    }

    /**
     * Check this example to see how to verify if a mock was called with exactly the arguments you passed to the function.
     * Even in this test, the usage of Matchers (eq) is not really needed, sometimes you will need it if you deal with mocks
     * Something to remark here is, please avoid the usages of any() as the best practice to follow.
     */
    @Test
    void should_updateStatus_when_userExists() {
        //given
        final long existingId = 123123L;
        given(userRepository.findById(existingId)).willReturn(Optional.of(setUpBarbara(existingId)));

        //when
        userDAO.setStatus(existingId, false);

        //then
        then(userRepository).should().updateStatus(eq(existingId), eq(false));
    }

    /**
     * Check in this example, how to ensure your mock was called with an expected object, even if the object is created inside of the method to test.
     * Check how captor works.
     * I admit a persist method which is not returning anything (at least the given Id) is not the best one, but this is only to show how to "capturing" values in a mock
     */
    @Test
    void should_saveNewUser_when_persist() {
        //given
        final ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);

        //when
        userDAO.persist(setUpUnsavedGuybrush());

        //then
        then(userRepository).should().save(userCaptor.capture());
        assertThat(userCaptor.getValue()).isEqualToComparingFieldByField(UserEntity.UserEntityBuilder.anUserEntity()
                .name("Guybrush")
                .lastName("Threepwood")
                .enabled(true)
                .build());
    }

    /**
     * Check this example to let you know how to add to your mock the behavior of throwing an exception
     * Again even I'd encourage to don't use "any", if for some reason you have to use it, at least add an expected class
     * As a best practice, we need to check always if the exception contains also an expected message/attribute, to avoid false positives
     */
    @Test
    void should_throwException_when_saveFailed() {
        //given
        final User guybrush = setUpUnsavedGuybrush();
        given(userRepository.save(ArgumentMatchers.any(UserEntity.class))).willThrow(new PersistenceException("The database was corrupted"));

        //when
        Throwable raisedException = catchThrowable(() -> userDAO.persist(guybrush));

        //then
        assertThat(raisedException).isInstanceOf(UserDataException.class).hasMessage("The user could not be saved");

    }

    /**
     * Check this example about how to deal if you need to add a behaviour to your mock but this time is for a void method.
     * Notice how you have to do it "in the reverse way"
     */
    @Test
    void should_throwException_when_updateFailed() {
        //given
        final Long existingId = 12313L;

        given(userRepository.findById(existingId)).willReturn(Optional.of(setUpBarbara(existingId)));

        doThrow(new PersistenceException("The database was corrupted")).when(userRepository).updateStatus(existingId, true);

        //when
        Throwable raisedException = catchThrowable(() -> userDAO.setStatus(existingId, true));

        //then
        assertThat(raisedException).isInstanceOf(UserDataException.class).hasMessage("The user could not be updated");
    }

    private UserEntity setUpBarbara(Long existingId) {
        return new UserEntity(existingId, "Barbara", "Liskov", true);
    }

    private User setUpUnsavedGuybrush() {
        return User.UserBuilder.anUser()
                .name("Guybrush")
                .lastName("Threepwood")
                .enabled(true)
                .build();
    }


}