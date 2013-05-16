package com.sp3.mvc.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StopWatch;

@Aspect
public class LoggingAspect {
	
	private static Logger log = Logger.getLogger(LoggingAspect.class);
	
	@Around("controllerBean() && methodPointcut() ")
	public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("LoggingAspect::logTimeMethod start");
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Object retVal = joinPoint.proceed();

		stopWatch.stop();

		StringBuffer logMessage = new StringBuffer();
		logMessage.append(joinPoint.getTarget().getClass().getName());
		logMessage.append(".");
		logMessage.append(joinPoint.getSignature().getName());
		logMessage.append("(");
		// append args
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			logMessage.append(args[i]).append(",");
		}
		if (args.length > 0) {
			logMessage.deleteCharAt(logMessage.length() - 1);
		}

		logMessage.append(")");
		logMessage.append(" execution time: ");
		logMessage.append(stopWatch.getTotalTimeMillis());
		logMessage.append(" ms");
		log.info(logMessage.toString());
		log.debug("LoggingAspect::logTimeMethod end");
		return retVal;
	}
	
	@Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerBean() {}

    @Pointcut("execution(* *(..))")
    public void methodPointcut() {}

    /*@AfterReturning("controllerBean() && methodPointcut() ")
    public void afterMethodInControllerClass() {
    	log.debug("after advice..");
    }*/


}
