package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Genre> findAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select id,name from genres",
                new GenreRowMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        var result = namedParameterJdbcOperations.query("select id,name from genres where id=:id",
                Map.of("id",id),new GenreRowMapper());
        return result.isEmpty() ? Optional.empty() : Optional.of(result.getFirst());
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            return insert(genre);
        }
        return update(genre);
    }


    private Genre insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name",genre.getName());
        KeyHolder kh = new GeneratedKeyHolder();
        var updatedGenres = namedParameterJdbcOperations.update("insert into genre (name) values (:name)",
                params,kh);
        if (updatedGenres == 0) {
            throw new EntityNotFoundException(String.format("Genre %d couldn't be updated",genre.getId()));
        }
        return findById(Objects.requireNonNull(kh.getKey()).longValue()).orElseThrow(() -> new EntityNotFoundException(
                String.format("Genre %d couldn't get",genre.getId())));

    }


    private Genre update(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name",genre.getName());
        params.addValue("id",genre.getId());
        var updatedGenre = namedParameterJdbcOperations.update("update genres set name = :name " +
                        "where id=:id",
                params);
        if (updatedGenre == 0) {
            throw new EntityNotFoundException(String.format("Genre %d couldn't be updated",genre.getId()));
        }
        return genre;
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int i) throws SQLException {
        return new Genre(rs.getLong("id"),rs.getString("name"));
    }
    }


}
