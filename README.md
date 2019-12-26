# 读写分离数据源切换示例
## 说明
* 这是一个通过判断 http 请求类型，进行数据源切换，进而实现读写分离的示例项目
* 本项目简单的通过判断http请求类型来进行数据库选择，我们可以通过更多的扩展来实现比较复杂的切换逻辑。
* 项目中有一个简单的实体User,仅有两个简单的字段 ，id和name，系统启动的时候，会自动初始化写入数据库的数据结构，需要手动的复制从库的结构。
* 关于读/写(主/从)数据库的同步不在这个项目的讨论范围也不做深入展开。
* 本项目为了简介，使用jpa，其他的orm实现逻辑一致
## 修改数据连接配置文件
项目的目的是为了了解数据切换的方式，因此，相关的数据连接配置并没有写在配置文件中。而是写在了com.xyyh.rds.config.DataSourceConfiguration配置类里面，实际项目中可以修改为配置文件或者其它模式
## 运行项目
```bash
git clone https://github.com/ldwqh0/spring-routing-datasource.git
cd spring-routing-datasource
mvn clean spring-boot:run
```

