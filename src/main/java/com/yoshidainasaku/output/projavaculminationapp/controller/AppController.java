package com.yoshidainasaku.output.projavaculminationapp.controller;

import com.yoshidainasaku.output.projavaculminationapp.service.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class AppController {
    public record TaskItem(String id, String task, String deadline, boolean done) {
    }

    private final AppDao dao;

    @Autowired
    public AppController(AppDao dao) {
        this.dao = dao;
    }

    @GetMapping("/home")
    String home(Model model) {
        List<TaskItem> taskItems = dao.findAll();
        model.addAttribute("taskList", taskItems);

        return "home";
    }

    @GetMapping("/add")
    String addItem(@RequestParam("task") String task,
                   @RequestParam("deadline") String deadline) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, deadline, false);

        dao.add(item);

        return "redirect:/home";
    }

    @GetMapping("/delete")
    String deleteItem(@RequestParam("id") String id) {
        dao.delete(id);

        return "redirect:/home";
    }

    @GetMapping("/update")
    String updateItem(@RequestParam("id") String id,
                      @RequestParam("task") String task,
                      @RequestParam("deadline") String deadline,
                      @RequestParam("done") boolean done) {
        TaskItem item = new TaskItem(id, task, deadline, done);

        dao.update(item);

        return "redirect:/home";
    }
}
