package com.tatechsoft.project;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class InitService {

    @Value("${config.prod:#{null}}")
    private Boolean configProd;

    @PostConstruct
    public void initProduct() {
        log.info("configProd: {}", configProd);
    }


}
