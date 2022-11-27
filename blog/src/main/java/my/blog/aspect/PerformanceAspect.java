package my.blog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@Aspect
public class PerformanceAspect {

    @Pointcut("execution(* my.blog.board.controller.BoardController.boardListForm(..))")
    public void boardListForm() {
    }

    @Around("boardListForm()")
    public Object calculatePerformanceTime(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            result = proceedingJoinPoint.proceed();
            stopWatch.stop();
            log.info("AOP 성능측정대상 {}", proceedingJoinPoint.getSignature());
            log.info(stopWatch.prettyPrint());
        } catch (Throwable e) {
            log.error("exception 발생!! " + e.getMessage());
        }
        return result;
    }
}
