package com.np.cardwizard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by eraindil on 02/08/14.
 */
@Controller
public class RoutesController {

    @RequestMapping({
            "/"
    })
    public String index(){
        return "forward:/index.html";
    }
}
