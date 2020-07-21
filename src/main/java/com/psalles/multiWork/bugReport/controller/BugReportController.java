package com.psalles.multiWork.bugReport.controller;

import com.psalles.multiWork.bugReport.model.BugReportBody;
import com.psalles.multiWork.bugReport.service.BugReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/bugReport")
public class BugReportController {

    BugReportService bugReportService;

    @Autowired
    public BugReportController(BugReportService bugReportService) {
        this.bugReportService = bugReportService;
    }

    @ApiOperation("Save screenshot")
    @PostMapping
    public void saveBugReport(@RequestBody BugReportBody image) {
        bugReportService.save(image);
    }

}
