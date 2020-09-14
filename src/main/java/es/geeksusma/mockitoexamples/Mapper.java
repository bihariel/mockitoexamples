package es.geeksusma.mockitoexamples;

public interface Mapper<SOURCE, TARGET> {

    TARGET toTarget(final SOURCE source);

}
