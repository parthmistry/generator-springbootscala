
var Generator = require('yeoman-generator');

module.exports = class extends Generator {

prompting() {
	return this.prompt([{
      type    : 'input',
      name    : 'name',
      message : 'Tell us your project name',
      default : this.appname,
      validate: input => /^([a-zA-Z0-9]*)$/.test(input)? true : "Special characters are not allowed"
    },
    {
      type    : 'input',
      name    : 'package',
      message : 'Let us know the package name you want for your project',
      default : 'com.' + this.appname,
      validate: input => /^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/.test(input)? true : "Please provide valid package name"
    },
    {
      type    : 'list',
      name    : 'rdbms',
      message : 'Which relational database you want to configure for your project?',
      choices : ['None', 'Mysql'],
      default : 'None'
    },
    {
      type    : 'confirm',
      name    : 'samplecontroller',
      message : 'Do you want us to create sample rest controller?',
      default : false
    }]).then((answers) => {
      this.userinput = answers;
      this.packagedir = answers.package.replace(".", "/"),
      this.capitalizedProjectName = this.userinput.name.charAt(0).toUpperCase() + this.userinput.name.slice(1)

      this.templateProperites = { NAME: this.userinput.name, CAPITALIZED_NAME: this.capitalizedProjectName, PACKAGE: this.userinput.package, RDBMS: this.userinput.rdbms };

    });
}

writing() {
  this.fs.copy(this.templatePath('gradlew'), this.destinationPath('gradlew'));
	this.fs.copy(this.templatePath('gradlew.bat'), this.destinationPath('gradlew.bat'));
	this.fs.copy(this.templatePath('gradle/wrapper/gradle-wrapper.jar'), this.destinationPath('gradle/wrapper/gradle-wrapper.jar'));
	this.fs.copy(this.templatePath('gradle/wrapper/gradle-wrapper.properties'), this.destinationPath('gradle/wrapper/gradle-wrapper.properties'));
  this.fs.copyTpl(
      this.templatePath('build.gradle'),
      this.destinationPath('build.gradle'),
      this.templateProperites
    );
  this.fs.copyTpl(
      this.templatePath('src/main/scala/apppackage/SpringbootscalaApplication.scala'),
      this.destinationPath('src/main/scala/' + this.packagedir + '/' + this.capitalizedProjectName + 'Application.scala'),
      this.templateProperites
    );
  if(this.userinput.samplecontroller) {
  this.fs.copyTpl(
      this.templatePath('src/main/scala/apppackage/web/rest/SampleController.scala'),
      this.destinationPath('src/main/scala/' + this.packagedir + '/web/rest/SampleController.scala'),
      this.templateProperites
    );
  this.fs.copyTpl(
      this.templatePath('src/main/scala/apppackage/service/SampleService.scala'),
      this.destinationPath('src/main/scala/' + this.packagedir + '/service/SampleService.scala'),
      this.templateProperites
    );
  }
  
  this.fs.copyTpl(
      this.templatePath('src/main/resources/application.yml'),
      this.destinationPath('src/main/resources/application.yml'),
      this.templateProperites
    );

  if(this.templateProperites.RDBMS !== 'None') {
    this.fs.copyTpl(
      this.templatePath('src/main/resources/liquibase'),
      this.destinationPath('src/main/resources/liquibase'),
      this.templateProperites
      );
  }
  }
  
};
