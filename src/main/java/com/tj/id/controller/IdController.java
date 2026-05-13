package com.tj.id.controller;

import com.tj.id.enums.BizTypeEnum;
import com.tj.id.common.R;
import com.tj.id.service.IdGeneratorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/id")
@AllArgsConstructor
@Slf4j
public class IdController {

    private final IdGeneratorService idGeneratorService;

    @GetMapping("nextId")
    public R<String> nextId(@RequestParam BizTypeEnum type,
                            @RequestParam(defaultValue = "true") boolean prefix) {
        return R.ok(idGeneratorService.nextId(type, prefix));
    }
}
