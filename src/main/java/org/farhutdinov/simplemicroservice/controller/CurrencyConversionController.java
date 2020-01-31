package org.farhutdinov.simplemicroservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.farhutdinov.simplemicroservice.model.CurrencyConversionBean;
import org.farhutdinov.simplemicroservice.service.CurrencyExchangeServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeServiceProxy serviceProxy;

//@Autowired
//    public CurrencyConversionController(CurrencyExchangeServiceProxy serviceProxy) {
//        this.serviceProxy = serviceProxy;
//    }

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable("from") String from,
                                                  @PathVariable("to") String to,
                                                  @PathVariable("quantity") BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("to",to);
        uriVariables.put("from",from);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class,
                uriVariables);

        CurrencyConversionBean response = responseEntity.getBody();

        return new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,
                quantity.multiply(response.getConversionMultiple()),response.getPort());

    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
                                                       @PathVariable BigDecimal quantity) {

        CurrencyConversionBean response = serviceProxy.retrieveExchangeValue(from, to);

        log.info("{}", response);

        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
    }
}

