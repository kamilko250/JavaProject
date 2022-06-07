package KKCH.StoreEverything.Utils;

import KKCH.StoreEverything.Consts.GlobalConsts;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class KKCHLogger {
    private static Logger log;
    private static FileHandler fh;

    public static Logger getLogger(){

        try {
            log = Logger.getLogger("KKCHLog");

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