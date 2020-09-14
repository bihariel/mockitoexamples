package es.geeksusma.mockitoexamples;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    void updateStatus(final Long id, final Boolean status);
}
