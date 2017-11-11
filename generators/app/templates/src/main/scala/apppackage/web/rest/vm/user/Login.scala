package <%= PACKAGE %>.web.rest.vm.user

case class LoginVM(username: String, password: String)

case class LoginResponseVM(token: String)