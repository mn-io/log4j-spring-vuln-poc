package net.mnio.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VulnController {

    Logger logger = LogManager.getLogger(VulnController.class);

    @GetMapping("/vuln")
    public String index() {
        logger.info("${jndi:ldap://127.0.0.1:3030/}");
        return "{'hello': 'world1'}";
    }

    @GetMapping("/vuln2")
    public String test(@RequestParam("input") String input) {
        logger.info(input);
        return "{'hello': 'world2'}";
    }
}