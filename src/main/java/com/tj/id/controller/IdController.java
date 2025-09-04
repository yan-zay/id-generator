package com.tj.id.controller;

import com.tj.id.enums.BizTypeEnum;
import com.tj.id.pojo.R;
import com.tj.id.service.IdGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: zay
 * @Date: 2024-03-03 16:36
 */
@Controller
@ResponseBody
@RequestMapping("/id")
@AllArgsConstructor
@Slf4j
public class IdController {

    private final IdGeneratorService idGeneratorService;

    @GetMapping("nextId")
    public R<String> nextId() {
        return R.ok(idGeneratorService.nextId(BizTypeEnum.GOODS, true));
    }
}
