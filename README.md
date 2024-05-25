# My Project

这是一个使用 Spring Boot 实现的 Google Gemini API 服务器端的实现版本。项目使用 Kotlin 语言编写，并使用 Gradle 作为构建工具。

## 功能

以下是该项目已有的功能：

1. **远程API访问**：通过 `APIRemoteAccessImpl` 类，我们可以访问远程的 Google Gemini API。这个类提供了 `visitRemoteAPI`
   方法，该方法接收远程主机地址、远程路径、API 密钥和请求体作为参数，并返回 API 的响应。

2. **API响应处理**：通过 `APIRemoteResponseHandlerImpl` 类，我们可以处理从 Google Gemini API
   返回的响应。这个类提供了 `handleResponse` 和 `handleString` 方法，这两个方法都接收一个 `Authentication`
   对象和一个文本字符串作为参数。`handleResponse` 方法会将 API 的响应转换为一个 JSON 对象，然后返回该对象的 "text"
   字段的值。`handleString` 方法会根据 `requestApi` 参数的值调用不同的处理方法。

3. **API远程控制器**：通过 `ApiRemoteController` 类，我们可以处理来自客户端的请求。这个类提供了 `googleApi`
   方法，该方法接收一个 `Authentication` 对象、一个模型名称、一个模式名称和一个文本字符串作为参数，并返回处理后的 API 响应。

## 如何使用

1. 克隆此项目到你的本地机器上。
2. 在项目的根目录下运行 `gradle build` 命令来构建项目。
3. 运行 `gradle bootRun` 命令来启动项目。
4. 你现在可以通过 `http://localhost:8080` 来访问项目。

## 贡献

如果你有任何问题或者建议，欢迎提交 issue 或者 pull request。
