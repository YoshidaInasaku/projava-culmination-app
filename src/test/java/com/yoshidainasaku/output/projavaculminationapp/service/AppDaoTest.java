package com.yoshidainasaku.output.projavaculminationapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static com.yoshidainasaku.output.projavaculminationapp.controller.AppController.*;
import static org.assertj.core.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppDaoTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private AppDao appDao;

    @BeforeEach
    void setUp() {
        appDao = new AppDao(jdbcTemplate);
    }

    @Test
    void データベースにタスクを登録する() {
        TaskItem item = new TaskItem("1", "タスク1", "2023-03-01", false);
        appDao.add(item);

        List<TaskItem> taskItems = appDao.findAll();
        assertThat(taskItems).hasSize(1);
    }

    @Test
    void データベースに登録されているタスクを取得する() {
        TaskItem item1 = new TaskItem("1", "タスク1", "2023-03-01", false);
        TaskItem item2 = new TaskItem("2", "タスク2", "2023-03-02", true);

        appDao.add(item1);
        appDao.add(item2);

        List<TaskItem> taskItems = appDao.findAll();
        assertThat(taskItems).hasSize(2);
    }

    @Test
    void データベースに登録されているタスクを削除できる() {
        TaskItem item = new TaskItem("1", "タスク1", "2023-03-03", false);
        appDao.add(item);

        int deletedTaskNum = appDao.delete("1");
        assertThat(deletedTaskNum).isEqualTo(1);

        List<TaskItem> taskItems = appDao.findAll();
        assertThat(taskItems).isEmpty();
    }

    @Test
    void データベースに登録されているタスク情報を更新できる() {
        TaskItem item = new TaskItem("1", "更新前のタスク1", "2023-03-04", false);
        appDao.add(item);

        item = new TaskItem("1", "更新後のタスク1", "2023-03-09", true);
        int updatedTaskNum = appDao.update(item);

        assertThat(updatedTaskNum).isEqualTo(1);

        List<TaskItem> taskItems = appDao.findAll();

        assertThat(taskItems).hasSize(1);
        assertThat(taskItems.get(0).task()).isEqualTo("更新後のタスク1");
        assertThat(taskItems.get(0).deadline()).isEqualTo("2023-03-09");
        assertThat(taskItems.get(0).done()).isTrue();
    }
}