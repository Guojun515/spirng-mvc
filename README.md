# spirng-mvc项目整合 #
## 一、spring MVC环境搭建 ##
**web.xml的配置**
>
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:config/spring/application-*.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
>
