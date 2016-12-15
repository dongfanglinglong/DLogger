# Dlogger 

这是一个Android日志输出并保存到终端存储空间的一个小工具；
支持输出长度大于4000的字符串，支持根据需求创建、保存、删除日志目录和日志文件。


## 使用方法
```java
ULog.v("verbose");
ULog.i("info");
ULog.d("debug");
ULog.w("warn");
ULog.e("error");
ULog.wtf("azzert");
```

## ULog 类说明
    1. ULog类实现了对日志输出到控制台，同时保存到手机SD卡的功能；
    2. ULog会自动生成 '当前类的当前行' 作为日志的tag；
    3. ULog支持输出 'Exception' 信息
    4. ULog是在DLog类的基础上而实现的


### DLOG类说明
    1. 依赖DefaultPrinter类来实现日志的输入和保存到SD卡
    2. 根据DSetting的配置，来实现日志的保存格式，文件名称，保存周期等设置；
    
    
#### DSetting属性说明
    1. isNeedStore  是否需要保存到本地，默认 'true'
    2. mLevel       需要保存的日志等级，更具DLevel来设置，默认为 '全部'
    3. mPatternStr  对日志tag的进行正则过滤的条件，默认为 '' (空)；
    4. mCharset     默认编码，默认 'UTF-8'
    5. mTimeFormat  日志每一行的时间戳格式，默认 'yyyy-MM-dd HH:mm:ss.SSS'
    6. mZoneOffset  日子中时间的时区，默认 '东八区'
    7. mLogDir      日志保存到SD卡的文件夹名称 ， 默认'dlogger'
    8. mLogPrefix   每一个日志文件的前缀，默认为 ''(空)
    9. mLogSuffix   每一个日志文件的前缀，默认为 '.log'
    10. mLogSegment 每个日志文件的时间切片，默认为 '一天'
    11. retainDays  日志文件保存的天数，默认为 '7天'


##  自定义使用方法
    根据自己项目的需求，新建DSetting类，赋值给DLog，使用DLog进行日志输出即可；