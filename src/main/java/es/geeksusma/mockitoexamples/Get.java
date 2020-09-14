package es.geeksusma.mockitoexamples;

import java.util.List;

public interface Get {

    User byId(final Long id);

    List<User> getAll();
}
