package springframework.aop;

/**
 * 承担 Pointcut 和 Advice 组合
 * Pointcut：获取 JoinPoint
 * Advice：决定 JoinPoint 执行的操作
 * @author yancy0109
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();

}
