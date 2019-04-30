# ByteCodeTechnology

Android侧字节码技术。编译时字节码修改以及运行时字节码生成(动态代理，cglib等等)是我们平常过程中经常需要的技术。

开这个repo的目的是为了介绍下，编译时字节码修改技术，主要有下面三种。

* javaassit修改字节码
* asm修改字节码
* AspectJ aop 技术

### javaassit

[javaassit的github地址](https://github.com/jboss-javassist/javassist)

[javaassit的tutorial](http://www.javassist.org/tutorial/tutorial.html)

当然，也有一些翻译版本的tutorial，但是在这里就不写了。

### asm

[asm使用指南](https://asm.ow2.io/asm4-guide.pdf)

### AspectJ
[AspectJ 文档](https://www.eclipse.org/aspectj/doc/released/progguide/index.html)

### 一些应用场景
技术是需要应用在实践当中的，那么我们可以利用这些字节码技术做哪些事情呢。下面是我所了解的一些场景

* AOP参数校验，
* AOP打点
* AOP性能监控
* 编译时BugFix
* 编译时检查

