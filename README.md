# spirng-mvc项目整合
## 一、spring MVC环境搭建
### web.xml的配置
* 设置spring的监听文件
>
	<!-- 设置spring的监听文件 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:config/application-config.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

* 配置spring 请求的DispatcherServlet
>
	<!-- spring mvc 请求的serverlet -->
	<servlet>
		<servlet-name>springMvc</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>classpath:config/spring-servlet/mvc-context.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>springMvc</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>

### Spring配置文件
* 在src/main/resources/config/spring的目录下建立application-config.xml的文件
>
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns="http://www.springframework.org/schema/beans" 
		xmlns:context="http://www.springframework.org/schema/context"
		xmlns:aop="http://www.springframework.org/schema/aop"
		xmlns:task="http://www.springframework.org/schema/task"
		xsi:schemaLocation="http://www.springframework.org/schema/beans
			http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
			http://www.springframework.org/schema/context 
		   	http://www.springframework.org/schema/context/spring-context-4.3.xsd
		   	http://www.springframework.org/schema/aop
	        http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
	        http://www.springframework.org/schema/task
	        http://www.springframework.org/schema/task/spring-task-4.3.xsd">
		...
	</beans>

### Spring MVC配置文件
* 在src/main/resources/config/spring-servlet的目录下建立mvc-context.xml的文件
>
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns="http://www.springframework.org/schema/beans" 
		xmlns:context="http://www.springframework.org/schema/context"
		xmlns:mvc="http://www.springframework.org/schema/mvc"
		xmlns:aop="http://www.springframework.org/schema/aop"
		xsi:schemaLocation="http://www.springframework.org/schema/beans
			http://www.springframework.org/schema/beans/spring-beans-4.3.xsd
			http://www.springframework.org/schema/context 
		   	http://www.springframework.org/schema/context/spring-context-4.3.xsd
		   	http://www.springframework.org/schema/mvc
		   	http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd
		   	http://www.springframework.org/schema/aop
	        http://www.springframework.org/schema/aop/spring-aop-4.3.xsd">
		...
	</beans>
### 笔记
* **mvc:annotation-driven帮我们做了写什么**
	1. **<mvc:annotation-driven /> 是一种简写形式，完全可以手动配置替代这种简写形式，简写形式可以让初学都快速应用默认配置方案。**
	2. **<mvc:annotation-driven /> 会自动注册DefaultAnnotationHandlerMapping与AnnotationMethodHandlerAdapter 两个bean。**
	3. **是spring MVC为@Controllers分发请求所必须的。 并提供了：数据绑定支持，@NumberFormatannotation支持，@DateTimeFormat支持，@Valid支持，读写XML的支持（JAXB），读写JSON的支持（Jackson）。 后面，我们处理响应ajax请求时，就使用到了对json的支持。**
	4. **后面，对action写JUnit单元测试时，要从spring IOC容器中取DefaultAnnotationHandlerMapping与AnnotationMethodHandlerAdapter 两个bean，来完成测试，取的时候要知道是<mvc:annotation-driven />这一句注册的这两个bean。**
	5. **<context:annotation-config> declares support for general annotations such as @Required, @Autowired, @PostConstruct, and so on.**
	6. **<mvc:annotation-driven /> is actually rather pointless. It declares explicit support for annotation-driven MVC controllers (i.e.@RequestMapping, @Controller, etc), even though support for those is the default behaviour.**
	7. **My advice is to always declare <context:annotation-config>, but don't bother with <mvc:annotation-driven /> unless you want JSON support via Jackson.**
