## oauth2.0-api

1：appid & 域名  校验

2：用户信息录入

3：信息录入成功之后  返回客户端（页面将跳转至 redirect_uri/?code=CODE）

4：客户端拿到code之后，为了防止code被截取冒用，客户端需要再次进行验证
	User Authorization Url - 用户授权之后的令牌请求地址
	 参数：
	 	id 
	 	secret
	 	code  (code说明 ： code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。)

5：服务器校验成功之后，将用户信息返还客户端，其中还有 accesstoken --> 一个有时效的字符串

6：之后客户端所有的请求都需要带上 accesstoken 为了保证安全，请求必须是POST请求

------------------------------(参数正则，强制匹配)------------------------------------------

## JWT(JSON WEB TOKEN)
 实现JWT做api安全签名，从数据层面实现API权限控制
 
 
## 数据签名
  对传输数据进行签名，保证传输安全（参考：http://ai.qq.com/doc/auth.shtml）
