package com.yoshidainasaku.output.projavaculminationapp.entity.repository;

import com.yoshidainasaku.output.projavaculminationapp.entity.LoginUser;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginUserRepository {
    private final static String SQL_FIND_BY_EMAIL = """
                SELECT 
                    user_id,
                    user_name,
                    email,
                    password
                    FROM users
                WHERE
                    users.email = ?
            """;
    private final JdbcTemplate jdbcTemplate;

    public LoginUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LoginUser> findByEmail(String email) {
        BeanPropertyRowMapper<LoginUser> rowMapper = new BeanPropertyRowMapper<>(LoginUser.class);
        LoginUser loginUser = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, rowMapper, email);

        return Optional.ofNullable(loginUser);
    }
}
