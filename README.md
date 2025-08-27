## 
ID生成器 上手即用

```
基于内存和mysql数据库结合的号段 ID生成器, 可拓展成分布式（内存可用redis替换） + 分布式锁
尽可能少的依赖，依赖如下：mysql, SpringBoot3, mybatis-plus, mybatis, lombok
```

--- curl如下
curl --location --request GET 'http://localhost:51106/id/nextId'

### DDL file： create_table.sql
