package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcAuthorRepository implements AuthorRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Author> findAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select id,full_name from authors",
                new AuthorRowMapper());

    }

    @Override
    public Optional<Author> findById(long id) {
        var result = namedParameterJdbcOperations.query("select id,full_name from authors where id=:id",
                Map.of("id",id),new AuthorRowMapper());
        return result.isEmpty() ? Optional.empty() : Optional.of(result.getFirst());
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            return insert(author);
        }
        return update(author);
    }


    private Author update(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("full_name",author.getFullName());
        params.addValue("id",author.getId());
        var updatedAuthors = namedParameterJdbcOperations.update("update authors set full_name = :full_name " +
                        "where id=:id",
                params);
        if (updatedAuthors == 0) {
            throw new EntityNotFoundException(String.format("Author %d couldn't be updated",author.getId()));
        }
        return author;
    }

    private Author insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("full_name",author.getFullName());
        KeyHolder kh = new GeneratedKeyHolder();
        var updatedAuthors = namedParameterJdbcOperations.update("insert into authors (full_name) values (:full_name)",
                params,kh);
        if (updatedAuthors == 0) {
            throw new EntityNotFoundException(String.format("Author %d couldn't be updated",author.getId()));
        }
        return findById(kh.getKey().longValue()).orElseThrow(() -> new EntityNotFoundException(
                String.format("Author %d couldn't get",author.getId())));

    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            return new Author(rs.getLong("id"),rs.getString("full_name"));
        }
    }
}
