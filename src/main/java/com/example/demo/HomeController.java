package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    TODORepository taskRepository;

    @RequestMapping("/")
    public String listTasks(Model model){
        model.addAttribute("tasks", taskRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String taskForm(Model model){
        model.addAttribute("task", new TODO());
        return "taskform";
    }

    @PostMapping("/process")
    public String processForm(@Valid TODO task, BindingResult result, @RequestParam("dueDate") String date){
        Date date1 = new Date();
        try{
            date1 = new SimpleDateFormat("yyyy-MM-d").parse(date);
            task.setDueDate(date1);
        }
        catch(Exception e){
            e.printStackTrace();
            return "redirect:/taskform";
        }
        if (result.hasErrors()){
            return "taskform";
        }
        taskRepository.save(task);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("task", taskRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id")long id, Model model){
        model.addAttribute("task", taskRepository.findById(id).get());
        return "taskform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id")long id){
        taskRepository.deleteById(id);
        return "redirect:/";
    }
}
