package org.gumiho.rest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.gumiho.rest.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Scope("prototype")
@CrossOrigin("*")
public class KafkaController {

    static Logger logger = Logger.getLogger(KafkaController.class);
    @Autowired
    private KafkaService kafkaService;

    @Value("${kafka.app.topic.name}")
    private String topic;

    //public Msgs msgs;

    /**
     *
     * @param
     * @param
     * @return success
     */
    @RequestMapping(value = "/kafka/v1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String v1(HttpServletRequest request) {

        //msgs = this.msgs;
        Map<String, String[]> hm = request.getParameterMap();
        String[] msgs = hm.get("msgs");
        if (msgs == null) {

            return "{\"msg\":\"NO DATASET FROM CLIENT END！\"}";
        }
        String json = JSON.toJSONString(msgs);

        try {
            kafkaService.send(json, topic);
            // System.out.println("生产者发送消息发送[SUCCESS] :" + json);

            return "{\"msg\":\"200\"}";

        } catch (Exception e) {

            //System.out.println(new Date() + "生产者发送消息[ERROR] :" + json);
            return "{\"msg\":\"500\"}";
        }

    }

    @CrossOrigin("*")
    @RequestMapping(value = "/kafka/v2", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> v2(@RequestBody String msgs, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (bindingResult.hasErrors()) {
            map.put("msg", "500");
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
        } else {

            try {
                JSONObject object = JSON.parseObject(msgs);
                //logger.error(msgs);
                kafkaService.send(object.toString(), topic);
                map.put("msg", "200");
                return map;
            } catch (Exception e) {
                map.put("msg", "500");
                return map;
            }
        }
        return map;
    }
}
