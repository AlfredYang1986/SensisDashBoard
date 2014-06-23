package util

import org.apache.log4j.Logger
import org.apache.log4j.xml.DOMConfigurator

object LoggingUtil {

    private var logger: Logger = null

    def getInstance(cls: Class[_]) = {
        if (logger == null)
            logger = Logger.getLogger(cls)
            DOMConfigurator.configure("conf\\loggingProperties.xml")

        logger
    }
}
