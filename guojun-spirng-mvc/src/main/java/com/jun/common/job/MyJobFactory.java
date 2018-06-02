package com.jun.common.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 
 * @Description quartz的job实现spring注入
 * @author Guojun
 * @Date 2018年4月21日 下午5:02:08
 *
 */
public class MyJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

	/**
	 * 这个对象Spring会帮我们自动注入进来,也属于Spring技术范畴.
	 */
	private transient AutowireCapableBeanFactory beanFactory;

	@Override
    public void setApplicationContext(final ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
    }

    @Override
    public Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
    	//调用父类的方法
    	final Object job = super.createJobInstance(bundle);
        //进行注入,这属于Spring的技术,不清楚的可以查看Spring的API.
    	beanFactory.autowireBean(job);
        return job;
    }
	
}
