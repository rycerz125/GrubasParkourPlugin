package aybici.parkourplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Markour {

    Logger logger = LoggerFactory.getLogger(Markour.class);

    public void setParkour(){
        logger.error("WOTDAFAK EVERYTHING WORKDS!");
    }
}
