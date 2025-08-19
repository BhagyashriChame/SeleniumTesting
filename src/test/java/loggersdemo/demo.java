package loggersdemo;
	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;
	public class demo {

	    private static final Logger logger = LogManager.getLogger(demo.class);

	    public static void main(String[] args) {
	        logger.debug("This is a DEBUG log");
	        logger.info("This is an INFO log");
	        logger.warn("This is a WARN log");
	        logger.error("This is an ERROR log");
	    }
	}


