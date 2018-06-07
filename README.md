# spirng-mvc项目整合 #
## 一、spring MVC环境搭建 ##
1. spring与springMVc相关的jar包引入
2. web.xml的配置
---
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:config/spring/application-*.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
---
3. 
