<div align="center">

## HttpInfo

**`Android`网络诊断工具**

[![Download](https://api.bintray.com/packages/guxiaonian/maven/http/images/download.svg) ](https://bintray.com/guxiaonian/maven/http/_latestVersion)
[![GitHub issues](https://img.shields.io/github/issues/guxiaonian/HttpInfo.svg)](https://github.com/guxiaonian/HttpInfo/issues)
[![GitHub forks](https://img.shields.io/github/forks/guxiaonian/HttpInfo.svg)](https://github.com/guxiaonian/HttpInfo/network)
[![GitHub stars](https://img.shields.io/github/stars/guxiaonian/HttpInfo.svg)](https://github.com/guxiaonian/HttpInfo/stargazers)
[![GitHub license](https://img.shields.io/github/license/guxiaonian/HttpInfo.svg)](http://www.apache.org/licenses/LICENSE-2.0)

## APP体验

![](./src/download.png)

</div>
<br>

Table of Contents
=================

   * [依赖](#依赖)
   * [调用方式](#调用方式)
   * [网络信息](#网络信息)
      * [Index信息](#Index信息)
      * [Net信息](#Net信息)
      * [Ping信息](#Ping信息)
      * [Http信息](#Http信息)
      * [Host信息](#Host信息)
      * [MtuScan信息](#MtuScan信息)
      * [PortScan信息](#PortScan信息)
      * [TraceRoute信息](#TraceRoute信息)
      * [NsLookup信息](#NsLookup信息)
   * [注意事项](#注意事项)

# 依赖

```gradle
implementation 'fairy.easy:httpmodel:{latest-version}'

```
      
# 调用方式

```java
  HttpModelHelper.getInstance()
                .init(getApplicationContext())
                .setChina(false)
                .setModelLoader(new HttpNormalUrlLoader())
                .setFactory()
                .addAll()
                .build()
                .startAsync(httpListener);

```

# 网络信息

## Index信息

>域名以及请求时间

### 中文键

```java
    "Index":{
        "测速地址":"http://www.baidu.com",
        "请求时间":"2019-08-13 11:04:26"
    }

```

### 英文键

```java
    "Index":{
        "address":"http://www.baidu.com",
        "time":"2019-08-13 11:17:46"
    }

```

## Net信息

>手机网络信息

### 中文键

```java
    "Net":{
        "网络状态":true,
        "网络类型":"WIFI",
        "网络制式":"4G_LTE",
        "WIFI状态":true,
        "WIFI信号强度":-55,
        "WIFI信号等级":4,
        "WIFI信号评定":"信号优",
        "本地IP":"192.168.1.199",
        "出口IP":"219.139.215.219",
        "出口IP归属地":"湖北省武汉市电信",
        "本地DNS":"192.168.1.201",
        "出口DNS":"112.90.73.100",
        "出口DNS归属地":"广东省深圳市联通",
        "漫游状态":false,
        "手机信号电平值":75,
        "手机信号强度":-65,
        "手机信号等级":4,
        "手机信号评定":"信号优",
        "总消耗时间":"1119ms"
    }

```

### 英文键

```java
    "Net":{
        "isNetworkAvailable":true,
        "netWorkType":"WIFI",
        "mobileType":"4G_LTE",
        "isWifiOpen":true,
        "wifiRssi":-56,
        "wifiLevel":3,
        "wifiLevelValue":"信号良",
        "ip":"192.168.1.199",
        "outputIp":"219.139.215.219",
        "outputIpCountry":"湖北省武汉市电信",
        "dns":"192.168.1.201",
        "outputDns":"220.249.245.92",
        "outputDnsCountry":"广东省深圳市联通",
        "isRoaming":false,
        "mobAsu":74,
        "mobDbm":-66,
        "mobLevel":4,
        "mobLevelValue":"信号优",
        "totalTime":"1062ms"
    }

```

## Ping信息

>相关ping信息

### 中文键

```java
    "Ping":{
        "网址":"http://www.baidu.com",
        "执行结果":200,
        "IP地址":"180.97.33.108",
        "生存时间":55,
        "发送包":10,
        "接收包":10,
        "丢包率":"0.0%",
        "最小RTT":"14.808ms",
        "平均RTT":"19.819ms",
        "最大RTT":"22.741ms",
        "算术平均偏差RTT":"2.683ms",
        "总消耗时间":"13121ms"
    }

```

### 英文键

```java
    "Ping":{
        "address":"http://www.baidu.com",
        "status":200,
        "ip":"180.97.33.107",
        "ttl":55,
        "transmitted":10,
        "receive":10,
        "lossRate":"0.0%",
        "rttMin":"14.848ms",
        "rttAvg":"17.8ms",
        "rttMax":"23.528ms",
        "rttMDev":"2.421ms",
        "allTime":"9017ms"
    }

```

## Http信息

>http请求信息

### 中文键

```java
   "Http":{
        "执行结果":200,
        "网址":"http://www.baidu.com",
        "用时":"667ms",
        "总消耗时间":"1058ms",
        "速度":"112kbps",
        "请求状态":200,
        "下载大小":"2.9KB",
        "服务器":"apache",
        "校验服务器":"bfe/1.0.8.18",
        "跳转":true,
        "返回header":[
            {
                "Server":"apache",
                "Connection":"Keep-Alive",
                "P3p":"CP=" OTI DSP COR IVA OUR IND COM "",
                "X-Android-Received-Millis":"1565665481199",
                "Date":"Tue, 13 Aug 2019 03:04:41 GMT",
                "X-Android-Selected-Protocol":"http/1.1",
                "null":"HTTP/1.1 302 Found",
                "Cache-Control":"no-cache",
                "Tracecode":"02812831040685689354081311, 02812609600843499786081311",
                "X-Android-Response-Source":"NETWORK 302"
            }
        ]
    }

```

### 英文键

```java
    "Http":{
        "status":200,
        "address":"http://www.baidu.com",
        "time":"586ms",
        "totalTime":"1374ms",
        "speed":"128kbps",
        "responseCode":200,
        "size":"2.9KB",
        "headerServer":"apache",
        "checkHeaderServer":"bfe/1.0.8.18",
        "isJump":true,
        "header":[
            {
                "Server":"apache",
                "Connection":"Keep-Alive",
                "P3p":"CP=" OTI DSP COR IVA OUR IND COM "",
                "X-Android-Received-Millis":"1565666277021",
                "Date":"Tue, 13 Aug 2019 03:17:57 GMT",
                "X-Android-Selected-Protocol":"http/1.1",
                "null":"HTTP/1.1 302 Found",
                "Cache-Control":"no-cache",
                "Tracecode":"10770919360300337674081311, 10770855150396544522081311",
                "X-Android-Response-Source":"NETWORK 302"
            }
        ]
    }

```

## Host信息

>本地host信息

### 中文键

```java
    "Host":{
        "执行结果":200,
        "详细信息":[
            "127.0.0.1 localhost",
            "::1 ip6-localhost"
        ],
        "总消耗时间":"3ms"
    }

```

### 英文键

```java
    "Host":{
        "status":200,
        "param":[
            "127.0.0.1 localhost",
            "::1 ip6-localhost"
        ],
        "totalTime":"3ms"
    }

```

## MtuScan信息

>传输单位的计算

### 中文键

```java
    "MtuScan":{
        "执行结果":200,
        "传输单元":"1492bytes",
        "总消耗时间":"4461ms"
    }

```

### 英文键

```java
    "MtuScan":{
        "status":200,
        "mtu":"1492bytes",
        "totalTime":"5437ms"
    }

```

## PortScan信息

>端口扫描

### 中文键

```java
    "PortScan":{
        "网址":"http://www.baidu.com",
        "执行结果":200,
        "总消耗时间":"30179ms",
        "具体信息":[
            {
                "扫描时间":"19ms",
                "是否开放":true,
                "端口号":80
            },
            {
                "扫描时间":"26ms",
                "是否开放":true,
                "端口号":443
            }
        ]
    }

```

### 英文键

```java
    "PortScan":{
        "address":"http://www.baidu.com",
        "status":200,
        "totalTime":"30205ms",
        "portNet":[
            {
                "delay":"22ms",
                "isConnected":true,
                "port":80
            },
            {
                "delay":"52ms",
                "isConnected":true,
                "port":443
            }
        ]
    }

```

## TraceRoute信息

>服务TraceRoute

### 中文键

```java
    "TraceRoute":{
        "执行结果":200,
        "总消耗时间":"66389ms",
        "扫描结果":[
            {
                "生存时间":1,
                "IP地址":"192.168.1.1",
                "扫描时间":"1.22ms",
                "IP归属地":"私网地址"
            }
        ]
    }

```

### 英文键

```java
    "TraceRoute":{
        "status":200,
        "totalTime":"81892ms",
        "traceRoute":[
            {
                "ttl":1,
                "ip":"192.168.1.1",
                "time":"3.17ms",
                "address":"私网地址"
            }
        ]
    }

```

## NsLookup信息

>服务NsLookup

### 中文键

```java
    "NsLookup":{
        "总消耗时间":"14024ms",
        "执行结果":200,
        "本地DNS服务器":[
            {
                "具体IP":"192.168.1.201",
                "归属地":"私网地址"
            },
            {
                "具体IP":"192.168.1.1",
                "归属地":"私网地址"
            }
        ],
        "解析策略":[
            {
                "策略内容":"默认策略",
                "域名":"www.baidu.com",
                "结果":200,
                "用时":"5489ms",
                "IP地址":[
                    {
                        "具体IP":"180.97.33.108",
                        "归属地":"中国江苏省中国电信"
                    },
                    {
                        "具体IP":"180.97.33.107",
                        "归属地":"未知"
                    }
                ]
            },
            {
                "策略内容":"指定DNS192.168.1.201",
                "域名":"www.baidu.com",
                "结果":200,
                "用时":"2760ms",
                "IP地址":[
                    {
                        "具体IP":"180.97.33.107",
                        "归属地":"中国江苏省中国电信"
                    },
                    {
                        "具体IP":"180.97.33.108",
                        "归属地":"中国江苏省中国电信"
                    }
                ]
            },
            {
                "策略内容":"指定DNS192.168.1.1",
                "域名":"www.baidu.com",
                "结果":200,
                "用时":"5530ms",
                "IP地址":[
                    {
                        "具体IP":"180.97.33.108",
                        "归属地":"中国江苏省中国电信"
                    },
                    {
                        "具体IP":"180.97.33.107",
                        "归属地":"未知"
                    }
                ]
            }
        ]
    }

```

### 英文键

```java
    "NsLookup":{
        "totalTime":"14007ms",
        "status":200,
        "localDns":[
            {
                "ip":"192.168.1.201",
                "param":"私网地址"
            },
            {
                "ip":"192.168.1.1",
                "param":"私网地址"
            }
        ],
        "strategy":[
            {
                "strategyParam":"默认策略",
                "strategyAddress":"www.baidu.com",
                "strategyStatus":200,
                "strategyTime":"2605ms",
                "strategyIp":[
                    {
                        "ip":"180.97.33.107",
                        "param":"中国江苏省中国电信"
                    },
                    {
                        "ip":"180.97.33.108",
                        "param":"中国江苏省中国电信"
                    }
                ]
            },
            {
                "strategyParam":"指定DNS192.168.1.201",
                "strategyAddress":"www.baidu.com",
                "strategyStatus":200,
                "strategyTime":"5171ms",
                "strategyIp":[
                    {
                        "ip":"180.97.33.108",
                        "param":"中国江苏省中国电信"
                    },
                    {
                        "ip":"180.97.33.107",
                        "param":"未知"
                    }
                ]
            },
            {
                "strategyParam":"指定DNS192.168.1.1",
                "strategyAddress":"www.baidu.com",
                "strategyStatus":200,
                "strategyTime":"6004ms",
                "strategyIp":[
                    {
                        "ip":"180.97.33.107",
                        "param":"中国江苏省中国电信"
                    },
                    {
                        "ip":"180.97.33.108",
                        "param":"未知"
                    }
                ]
            }
        ]
    }

```

# 注意事项

* 如果要使用第三方网络库或自己的封装库可以继承`ModelLoader`来实现。demo中已经写入了OkHttp的库`OkHttpUrlLoader`,使用的时候直接`setModelLoader(new OkHttpUrlLoader())`即可。
* `src`文件下有完整的JSON数据格式，方便查看。