package KKCH.StoreEverything.Utils;

import KKCH.StoreEverything.Consts.GlobalConsts;
import KKCH.StoreEverything.Enums.LoggerEnum;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class KKCHLogger {
    private static FileHandler fh;
    private static Level basicLoggerLevel = Level.ALL;

    public static Logger getLogger(LoggerEnum loggerType){
        Logger logger = getSpecificLogger(loggerType, basicLoggerLevel);
        return logger;
    }

    public static Logger getLoggerWithSetLevel(LoggerEnum loggerType, Level level){
        Logger logger = getSpecificLogger(loggerType, level);
        return logger;
    }
    
    private static Logger getSpecificLogger(LoggerEnum loggerType, Level levelType){
        Logger log = null;
        try {
            log = Logger.getLogger(loggerType.toString());
            log.setLevel(levelType);
            fh = new FileHandler(GlobalConsts.loggerFullPath, 10000000 , 1, true);
            log.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return log;
    }
}