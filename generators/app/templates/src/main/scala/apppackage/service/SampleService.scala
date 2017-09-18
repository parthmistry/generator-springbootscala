package <%= PACKAGE %>.service

import org.springframework.stereotype.Service

@Service
class SampleService {

  def sayHello(name: String) : String = {
    "Hello " + name;
  }
}
