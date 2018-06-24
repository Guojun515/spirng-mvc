package com.jun.sys.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Description 系统信息
 * @author Guojun
 * @Date 2018年6月23日 下午2:48:46
 *
 */
public class SysVmInfo implements Serializable {
	private static final long serialVersionUID = 3147156666183922230L;

	/**
	 * 总内存大小
	 */
	private long memoryTotal;
	
	/**
	 * 已经使用了的总内存
	 */
	private long memoryUsed;
	
	/**
	 * 堆内存的大小
	 */
	private long memoryHeapTotal;
	
	/**
	 * 使用了的堆内存
	 */
	private long memoryHeapUsed;
	
	/**
	 * 非堆内存
	 */
	private long memoryNoHeapTotal;
	
	/**
	 * 被使用了的非堆内存
	 */
	private long memoryNoHeapUsed;
	
	/**
	 * 线程数
	 */
	private long threadCount;
	
	/**
	 * 守护线程数
	 */
	private long threadDeamonCount;
	
	/**
	 * 死锁的线程数
	 */
	private long deadlockCount;
	
	/**
	 * 死锁的线程
	 */
	private List<String> deadlocks;
	
	/**
	 * 类加载的数量
	 */
	private long classloaded;
	 
	/**
	 * 类销毁的数量
	 */
	private long classUnloaded;

	public long getMemoryTotal() {
		return memoryTotal;
	}

	public void setMemoryTotal(long memoryTotal) {
		this.memoryTotal = memoryTotal;
	}

	public long getMemoryUsed() {
		return memoryUsed;
	}

	public void setMemoryUsed(long memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	public long getMemoryHeapTotal() {
		return memoryHeapTotal;
	}

	public void setMemoryHeapTotal(long memoryHeapTotal) {
		this.memoryHeapTotal = memoryHeapTotal;
	}

	public long getMemoryHeapUsed() {
		return memoryHeapUsed;
	}

	public void setMemoryHeapUsed(long memoryHeapUsed) {
		this.memoryHeapUsed = memoryHeapUsed;
	}

	public long getMemoryNoHeapTotal() {
		return memoryNoHeapTotal;
	}

	public void setMemoryNoHeapTotal(long memoryNoHeapTotal) {
		this.memoryNoHeapTotal = memoryNoHeapTotal;
	}

	public long getMemoryNoHeapUsed() {
		return memoryNoHeapUsed;
	}

	public void setMemoryNoHeapUsed(long memoryNoHeapUsed) {
		this.memoryNoHeapUsed = memoryNoHeapUsed;
	}

	public long getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(long threadCount) {
		this.threadCount = threadCount;
	}

	public long getThreadDeamonCount() {
		return threadDeamonCount;
	}

	public void setThreadDeamonCount(long threadDeamonCount) {
		this.threadDeamonCount = threadDeamonCount;
	}

	public long getDeadlockCount() {
		return deadlockCount;
	}

	public void setDeadlockCount(long deadlockCount) {
		this.deadlockCount = deadlockCount;
	}

	public List<String> getDeadlocks() {
		return deadlocks;
	}

	public void setDeadlocks(List<String> deadlocks) {
		this.deadlocks = deadlocks;
	}

	public long getClassloaded() {
		return classloaded;
	}

	public void setClassloaded(long classloaded) {
		this.classloaded = classloaded;
	}

	public long getClassUnloaded() {
		return classUnloaded;
	}

	public void setClassUnloaded(long classUnloaded) {
		this.classUnloaded = classUnloaded;
	}
}
