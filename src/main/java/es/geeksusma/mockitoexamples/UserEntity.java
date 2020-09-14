package es.geeksusma.mockitoexamples;

import java.util.Objects;

public class UserEntity {

    private Long id;
    private String name;
    private String lastName;
    private Boolean enabled;

    public UserEntity(Long id, String name, String lastName, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.enabled = enabled;
    }

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public static final class UserEntityBuilder {
        private String name;
        private String lastName;
        private Boolean enabled;

        private UserEntityBuilder() {
        }

        public static UserEntityBuilder anUserEntity() {
            return new UserEntityBuilder();
        }

        public UserEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserEntityBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserEntityBuilder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserEntity build() {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(name);
            userEntity.setLastName(lastName);
            userEntity.setEnabled(enabled);
            return userEntity;
        }
    }
}
