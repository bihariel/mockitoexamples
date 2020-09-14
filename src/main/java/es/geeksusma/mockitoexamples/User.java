package es.geeksusma.mockitoexamples;

class User {

    private String name;
    private String lastName;
    private Boolean enabled;

    String getFullName() {
        return name + " " + lastName;
    }

    Boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public static final class UserBuilder {
        private String name;
        private String lastName;
        private Boolean enabled;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public User build() {
            User user = new User();
            user.lastName = this.lastName;
            user.enabled = this.enabled;
            user.name = this.name;
            return user;
        }
    }
}
