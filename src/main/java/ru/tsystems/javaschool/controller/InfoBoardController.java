package ru.tsystems.javaschool.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.tsystems.javaschool.exceptions.TruckingServiceException;
import ru.tsystems.javaschool.service.InfoBoardService;

@Controller
public class InfoBoardController {
    private static final Logger LOGGER = LoggerFactory.getLogger(InfoBoardController.class);

    private InfoBoardService infoBoardService;

    @Autowired
    public void setInfoBoardService(InfoBoardService infoBoardService) {
        this.infoBoardService = infoBoardService;
    }

    @RequestMapping("/emit")
    @ResponseBody
    String infoBoardQueue() throws TruckingServiceException {
        LOGGER.info("Emit to infoBoardQueue");
        infoBoardService.sendInfoToQueue();
        return "Emit to queue";
    }
}
