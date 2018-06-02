package com.jun.common.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;

/**
 * https://blog.csdn.net/blueheart20/article/details/44648667
 * @Description 自定义实现AsyncTaskExecutor的任务执行器，处理异常
 * @author Guojun
 * @Date 2018年5月12日 下午10:03:16
 * @deprecated Spring 4.1后不起作用，需要改为MyAsyncExceptionHandler这种方式处理自定义异常
 */
public class ExceptionHandlingAsyncTaskExecutor implements AsyncTaskExecutor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingAsyncTaskExecutor.class);
	
	private AsyncTaskExecutor asyncTaskExecutor;
	
	public ExceptionHandlingAsyncTaskExecutor(AsyncTaskExecutor asyncTaskExecutor) {
		this.asyncTaskExecutor = asyncTaskExecutor;
	}

	@Override
	public void execute(Runnable task) {
		asyncTaskExecutor.execute(this.createRunnable(task));
		
	}

	@Override
	public void execute(Runnable task, long startTimeout) {
		asyncTaskExecutor.execute(this.createRunnable(task), startTimeout);
	}

	@Override
	public Future<?> submit(Runnable task) {
		return asyncTaskExecutor.submit(this.createRunnable(task));
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return asyncTaskExecutor.submit(this.createCallable(task));
	}
	
	/**
	 * 实现Callable接口的任务线程能返回执行结果,Callable接口的call()方法允许抛出异常
	 * 注意：Callable接口支持返回执行结果，此时需要调用FutureTask.get()方法实现，此方法会阻塞主线程直到获取‘将来’结果；当不调用此方法时，主线程不会阻塞！
	 * @param task
	 * @return
	 */
	private <T> Callable<T> createCallable(final Callable<T> task) {
		return ()->{
			try {
				return task.call();
			} catch (Exception e) {
				this.handlerException(e);
				throw e;
			}
		};
	}
	
	/**
	 * 而实现Runnable接口的任务线程不能返回结果；而Runnable接口的run()方法的异常只能在内部消化，不能继续上抛；
	 * @param task
	 * @return
	 */
	private Runnable createRunnable(final Runnable task) {
		return ()->{
			try {
				task.run();
			} catch (Exception e) {
				this.handlerException(e);
			}
		};
	}
	
	/**
	 * 异常的处理方式
	 * @param e
	 */
	private void handlerException(Exception e) {
		LOGGER.error("ExceptionHandlingAsyncTaskExecutor:handlerException", e);
	}

}
