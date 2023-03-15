package com.yoshidainasaku.output.projavaculminationapp.entity.repository;

import com.yoshidainasaku.output.projavaculminationapp.entity.LoginUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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
        List<Map<String, Object>> result = jdbcTemplate.queryForList(SQL_FIND_BY_EMAIL, email);
        LoginUser loginUser = null;
        for (Map<String, Object> user : result) {
            loginUser = new LoginUser(
                    (String) user.get("userId"),
                    (String) user.get("userName"),
                    (String) user.get("email"),
                    (String) user.get("password"));
        }

        return Optional.ofNullable(loginUser);
    }
}
