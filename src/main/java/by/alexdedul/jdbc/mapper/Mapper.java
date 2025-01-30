package by.alexdedul.jdbc.mapper;

public interface Mapper<T, F> {
    T mapFrom(F value);
}
