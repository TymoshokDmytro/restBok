package auchan.restBok.controllers;

import auchan.restBok.service.GetSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("get-bill-api/v1")
public class MainController {

    @Autowired
    private GetSQLService sqlService;

    @GetMapping(value = "{vte_date}/{store}/{check_num}", produces = "application/json")
    public Map<String, String> getBillPrice(@PathVariable String store,
                                            @PathVariable String check_num,
                                            @PathVariable String vte_date) {
        return sqlService.getPosData(vte_date,store,check_num);
    }
}
