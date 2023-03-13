package com.yoshidainasaku.output.projavaculminationapp.controller;

import com.yoshidainasaku.output.projavaculminationapp.service.AppDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static com.yoshidainasaku.output.projavaculminationapp.controller.AppController.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppController.class)
class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppDao appDao;

    @Test
    void ホーム画面にアクセスできる() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void タスク追加後一覧画面にリダイレクトされる() throws Exception {
        String task = "テストのタスク";
        String deadline = "2023-03-01";
        String id = UUID.randomUUID().toString().substring(0, 8);

        mockMvc.perform(MockMvcRequestBuilders.get("/add")
                .param("task", task)
                .param("deadline", deadline))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/list"));

        Mockito.verify(appDao, Mockito.times(1)).add(Mockito.any(AppController.TaskItem.class));
    }

    @Test
    void タスク一覧画面が見れる() throws Exception {
        List<TaskItem> taskItems = List.of(
                new TaskItem(UUID.randomUUID().toString().substring(0, 8), "テスト1", "2023-03-13", false),
                new TaskItem(UUID.randomUUID().toString().substring(0, 8), "テスト2", "2023-03-14", false)
        );
        Mockito.doReturn(taskItems).when(appDao).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"))
                .andExpect(MockMvcResultMatchers.model().attribute("taskList", taskItems));
    }

    @Test
    void タスクが消去できる() throws Exception {
        String id = UUID.randomUUID().toString().substring(0, 8);

        mockMvc.perform(MockMvcRequestBuilders.get("/delete")
                .param("id", id))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/list"));

        Mockito.verify(appDao, Mockito.times(1)).delete(id);
    }

    @Test
    void タスクの情報を更新できる() throws Exception {
        String id = UUID.randomUUID().toString().substring(0, 8);
        String task = "サンプルアプリの完成";
        String deadline = "2023-03-21";

        mockMvc.perform(MockMvcRequestBuilders.get("/update")
                .param("id", id)
                .param("task", task)
                .param("deadline", deadline)
                .param("done", String.valueOf(true)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/list"));

        Mockito.verify(appDao, Mockito.times(1)).update(Mockito.any(AppController.TaskItem.class));
    }
}