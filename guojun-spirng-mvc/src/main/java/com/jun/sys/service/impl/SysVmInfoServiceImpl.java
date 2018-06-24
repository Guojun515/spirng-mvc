package com.jun.sys.service.impl;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jun.sys.model.SysVmInfo;
import com.jun.sys.service.ISysVmInfoService;

@Service
public class SysVmInfoServiceImpl implements ISysVmInfoService {
	
	private  final ClassLoadingMXBean classMxBean = ManagementFactory.getClassLoadingMXBean();
	private  final ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
	private  final MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();

	@Override
	public SysVmInfo getSysVmConfig() {
		SysVmInfo sysVmInfo = new SysVmInfo();
		
		//堆内存（转换成MB单位：/1024/1204）
		MemoryUsage heapMenory =memoryMxBean.getHeapMemoryUsage();
		long heapTotal = heapMenory.getMax()/1024/1024;
		long heapUsed = heapMenory.getUsed()/1024/1024;
		
		//非堆内存（转换成MB单位：/1024/1204）
		MemoryUsage noHeapMonory = memoryMxBean.getNonHeapMemoryUsage();
		long noHeapTotal = noHeapMonory.getCommitted()/1024/1024;
		long noHeapUsed = noHeapMonory.getUsed()/1024/1024;
		
		//总内存
		long memoryTotal = heapTotal + noHeapTotal;
		long memoryUsed = heapUsed + noHeapUsed;
		
		//线程数
		long threadCount = threadMxBean.getThreadCount();
		long daemonCount = threadMxBean.getDaemonThreadCount();
		Set<String> deadLocks = this.getDeadlockedThreads(threadMxBean);
		long deadLockCount = deadLocks.size();
		
		//java类
		int classLoadCount = classMxBean.getLoadedClassCount();
		long classUnloadedCount = classMxBean.getUnloadedClassCount();
		
		sysVmInfo.setMemoryTotal(memoryTotal);
		sysVmInfo.setMemoryUsed(memoryUsed);
		sysVmInfo.setMemoryHeapTotal(heapTotal);
		sysVmInfo.setMemoryHeapUsed(heapUsed);
		sysVmInfo.setMemoryNoHeapTotal(noHeapTotal);
		sysVmInfo.setMemoryNoHeapUsed(noHeapUsed);
		sysVmInfo.setThreadCount(threadCount);
		sysVmInfo.setThreadDeamonCount(daemonCount);
		sysVmInfo.setDeadlockCount(deadLockCount);
		sysVmInfo.setDeadlocks(new ArrayList<>(deadLocks));
		sysVmInfo.setClassloaded(classLoadCount);
		sysVmInfo.setClassUnloaded(classUnloadedCount);
		
		return sysVmInfo;
	}
	
	/**
     * Returns a set of diagnostic stack traces for any deadlocked threads. If no threads are
     * deadlocked, returns an empty set.
     *
     * @return stack traces for deadlocked threads or an empty set
     */
    private Set<String> getDeadlockedThreads(ThreadMXBean threadMxBean) {
        final long[] ids = threadMxBean.findDeadlockedThreads();
        if (ids != null) {
            final Set<String> deadlocks = new HashSet<>();
            for (ThreadInfo info : threadMxBean.getThreadInfo(ids, 0)) {
                final StringBuilder stackTrace = new StringBuilder();
                for (StackTraceElement element : info.getStackTrace()) {
                    stackTrace.append("\t at ")
                            .append(element.toString())
                            .append(String.format("%n"));
                }

                deadlocks.add(
                        String.format("%s locked on %s (owned by %s):%n%s",
                                info.getThreadName(),
                                info.getLockName(),
                                info.getLockOwnerName(),
                                stackTrace.toString()
                        )
                );
            }
            return Collections.unmodifiableSet(deadlocks);
        }
        return Collections.emptySet();
    }
}
