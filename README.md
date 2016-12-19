# demoTicket1

1.数据库连接信息在application.properties上 按需配置 MariaDB默认是3306 我改成3307
2.clone之后,跑一跑InitDatabaseTests 载入数据
3.弄了个异步小框架,抢购时候是对redis中修改数据,数据库的更新延迟到异步队列处理,减少压力,发送邮件也是放到异步处理中
4.拦截器进行token身份认证
