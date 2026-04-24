package com.example.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import com.example.common.Result;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.service.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @Resource
    UserService userService;

    @Resource
    HeritageService heritageService;

    @Resource
    EnrollService enrollService;

    @Resource
    ActivityService activityService;

    @Resource
    ClassifyService classifyService;

    /**
     * 默认请求接口
     */
    @GetMapping("/")
    public Result hello () {
        return Result.success();
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account loginAccount = null;
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            loginAccount = adminService.login(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            loginAccount = userService.login(account);
        }
        return Result.success(loginAccount);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        userService.add(user);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.updatePassword(account);
        }
        return Result.success();
    }

    @GetMapping("/dataCount")
    public Result dataCount () {
        List<Heritage> heritageList = heritageService.selectAll(null);
        List<User> userList = userService.selectAll(null);
        List<Activity> activityList = activityService.selectAll(null);
        long count = enrollService.selectAll(null).stream().filter(e -> e.getStatus().equals("审核通过")).count();

        Dict dict = Dict.create()
                .set("heritage", heritageList.size())
                .set("user", userList.size())
                .set("activity", activityList.size())
                .set("enroll", count);
        return Result.success(dict);
    }

    @GetMapping("/selectLine")
    public Result selectLine() {
        Date date = new Date();
        // 获取7天之前的日期
        DateTime start = DateUtil.offsetDay(date, -7);
        // ["2025-10-18", "2025-10-19", ... , "2025-10-22"]
        List<String> dateList = DateUtil.rangeToList(start, date, DateField.DAY_OF_YEAR).stream().map(DateUtil::formatDate).sorted()
                .map(d -> d = d.substring(5)).toList();
        //  ["10-18", "10-19", ... , "10-22"]
        List<Long> countList = new ArrayList<>();
        List<Enroll> enrollList = enrollService.selectAll(null).stream().filter(e -> e.getStatus().equals("审核通过") || e.getStatus().equals("待审核")).toList();
        for (String day : dateList) {
            long count = enrollList.stream().filter(e -> e.getTime().contains(day)).count();
            countList.add(count);
        }
        Dict dict = Dict.create().set("date", dateList).set("count", countList);
        return Result.success(dict);
    }

    @GetMapping("/selectPie")
    public Result selectPie() {
        List<Classify> classifyList = classifyService.selectAll(null);
        List<Heritage> heritageList = heritageService.selectAll(null);
        List<Dict> dictList = new ArrayList<>();
        for (Classify classify : classifyList) {
            long count = heritageList.stream().filter(h -> h.getClassifyId().equals(classify.getId())).count();
            Dict dict = Dict.create().set("name", classify.getName()).set("value", count);
            dictList.add(dict);
        }
        return Result.success(dictList);
    }


}
