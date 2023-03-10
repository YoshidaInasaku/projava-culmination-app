package com.yoshidainasaku.output.projavaculminationapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.yoshidainasaku.output.projavaculminationapp.controller.AppController.*;

@Service
public class AppDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    AppDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(TaskItem taskItem) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(taskItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("tasklist");
        insert.execute(param);
    }

    public List<TaskItem> findAll() {
        String query = "SELECT * FROM tasklist";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<TaskItem> taskItems = result.stream()
                .map((Map<String, Object> row) -> new TaskItem(
                        row.get("id").toString(),
                        row.get("task").toString(),
                        row.get("deadline").toString(),
                        (Boolean) row.get("done")))
                .toList();

        return taskItems;
    }

    public int delete(String id) {
        int num = jdbcTemplate.update("DELETE FROM tasklist WHERE id = ?", id);
        return num;
    }

    public int update(TaskItem taskItem) {
        int num = jdbcTemplate.update(
                """
                        UPDATE tasklist SET
                        task = ?,
                        deadline = ?,
                        done = ?
                        WHERE id = ?
                        """,
                taskItem.task(),
                taskItem.deadline(),
                taskItem.done(),
                taskItem.id()
        );

        return num;
    }
}
