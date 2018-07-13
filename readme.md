## 					Feliane框架

### 一. 框架简介

- Feliane：英国短毛蓝猫的拉丁文学名，因为最近家里养了一只蓝猫，故以此命名。
- 框架功能：主要基于SpringBoot，对日常开发中的一些常用逻辑、公共模块等进行抽取，并增加一些工具简化开发流程，也作为日常开发的总结。框架内容较杂，有待于系统整理。



### 二. 主要模块介绍

- Feliane：框架的父工程，主要用于管理配置和依赖，这里基于SpringBoot；
- Felinae-Druid：Druid数据源模块，进行了Druid数据源的自动配置，并注册了后台监控的Servlet和Listener；
- Felinae-MyBatis：MyBatis扩展模块，主要针对MyBatis框架进行一些扩展，目前支持通用SQL语句的生成，ResultMap的自动配置，避免每次都手动输入SQL语句；
- Felinae-Json：基于Jackson框架封装了常用的Json序列化/反序列化操作；
- Felinae-Rest：基于RestTemplate，封装了发送HTTP请求并获取响应的操作；