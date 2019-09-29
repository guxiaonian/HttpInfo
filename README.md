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

# Dependencies

```gradle
implementation 'fairy.easy:httpmodel:{latest-version}'

```

## [WIKI](https://github.com/guxiaonian/HttpInfo/wiki)

## ChangeLog

版本|更新内容|更新日期
-----|-----|-----
**1.0.3** |1、修改MtuScan设置,改为多线程并发,并设置超时时间为1s| 2019-09-29
**1.0.2** |1、增加Net相关选项| 2019-08-13


# Precautions

* 如果要使用第三方网络库或自己的封装库可以继承`ModelLoader`来实现。demo中已经写入了OkHttp的库`OkHttpUrlLoader`,使用的时候直接`setModelLoader(new OkHttpUrlLoader())`即可。
* `src`文件下有完整的JSON数据格式，方便查看。