package com.company.uiamp.app.controller;

import com.company.uiamp.app.entity.App;
import com.company.uiamp.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apps")
public class AppController {

    @Autowired
    private AppService appService;

    @GetMapping
    public List<App> getAllApps() {
        return appService.list();
    }

    @GetMapping("/{id}")
    public App getAppById(@PathVariable Long id) {
        return appService.getById(id);
    }

    @PostMapping
    public boolean createApp(@RequestBody App app) {
        return appService.save(app);
    }

    @PutMapping("/{id}")
    public boolean updateApp(@PathVariable Long id, @RequestBody App app) {
        app.setId(id);
        return appService.updateById(app);
    }

    @DeleteMapping("/{id}")
    public boolean deleteApp(@PathVariable Long id) {
        return appService.removeById(id);
    }
}