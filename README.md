使用说明：
#### 1.导入：/src/main/resources/db/mysql.sql到数据库<br/>
#### 2.安装redis 、ftp服务
#### 4.修改application-dev.yml配置文件，连接你的数据库、redis、mysql
#### 5.运行后台程序即可

#### 这是后台接口，前端项目请导航到：<a href='https://github.com/yueshengkeji/pm-view' title='前端项目'>前端项目地址</a>
项目支持mysql、sqlserver数据库，由于时间关系，源代码中目前只放了mysql数据库，如需sqlserver数据库文件，请下方扫码联系作者提供

项目管理软件平台，功能如下：
====================
支持企业微信推送审批消息，视图层自适应兼容移动端，支持的考勤硬件设备：商汤，汉王，企业微信打卡（软）
#### 1.销售管理：投标盖章申请、开标反馈、投标保证金管理、销售合同管理、开票申请、收款登记（提醒）、合同分布地图展示

#### 2.项目管理：事项日程提醒、材料计划、材料采购申请、报修码、辅材管理、成本科目、项目材料统计、售后管理

#### 3.采购管理：采购任务、采购订单、采购合同、采购合同付款、设备维修登记、询价管理、供应商采购/入库报表

#### 4.仓库管理：材料入库管理、出库管理、退库管理、仓库盘点、库存查询、入/出/退/盘点/记录报表查询/导出excel

#### 5.智慧办公：请假单、加班申请单、出差申请单、用车申请单、办公用品管理（入库/领用/报表）、私车公用登记（记录开始位置/终点位置，记录导航公里数）、考勤报表（基于考勤设备数据和企业微信考勤数据）、以上提到的功能模块中的各种报表

#### 6.**工作流引擎**：自研工作流引擎，不依赖任何第三方库，流程新增（绑定自定义表单/系统已有表单），过程新增（动态条件过程，角色/部门/上级领导动态人员，自由选人审批，会签，审批后置事件通知），审批通过，驳回，知会，补充意见，加签等功能，审批视图可以打印，系统默认打印模版/自定义打印组件，流程统计

#### 7.综合管理：组织架构管理、部门管理、角色管理、公司公告、证书管理、员工管理、考核打分管理、会议管理

#### 8.财务管理：报销管理、项目成本统计、用车成本统计、采购/仓库/财务对账单、销售合同对账单、员工饭卡（金额）账户管理

#### 9.系统管理：参数设置、定时任务

#### 10.招商管理：租赁合同登记管理、合同条款管理、保证金管理、合同账单生成、收款/开票状态管理、催款通知单

系统定时任务的模块，移植于若依框架的common包，系统架构如下（如有侵权，请及时联系作者，作者收到后将及时更换开源库）：
====
1.spring-boot、jackson、fastjson、alibaba-druid、cglib、pagehelper、jsoup、htmlunit、apache-poi、docx4j、xdocreport、freemarker、camel-ftp、commons-email、commons-io、xmlgraphics-fop
、google-zxing、thumbnailator、mina-core、swagger、google-guava、redis、apache-httpclient、pinyin4j、xhtmlrenderer、quartz、dom4j、tess4j、apache-pdfbox、

#### 开放所有源代码，支持个人/企业二次开发，也可以联系作者定制功能

![image text](https://github.com/yueshengkeji/pm/blob/main/src/main/resources/assets/img/img.png "扫码加作者微信")



#### apache license 2.0开源协议，可以商用，毕业设计，学习交流
