var Generator = require('yeoman-generator');

module.exports = class extends Generator {

	constructor(args, opts) {
		super(args, opts);

		this.copy = function(source, target) {
			this.fs.copy(
				this.templatePath(source),
				this.destinationPath(target)
			);
		}

		this.copyTpl = function(source, target) {
			this.fs.copyTpl(
				this.templatePath(source),
				this.destinationPath(target),
				this.templateProperites
			);
		}
	}

	prompting() {
		return this.prompt([ {
			type : 'input',
			name : 'name',
			message : 'Tell us your project name',
			default : this.appname,
			validate : input => /^([a-zA-Z0-9]*)$/.test(input) ? true : "Special characters are not allowed"
		},
			{
				type : 'input',
				name : 'package',
				message : 'Let us know the package name you want for your project',
				default : 'com.' + this.appname,
				validate : input => /^([a-z_]{1}[a-z0-9_]*(\.[a-z_]{1}[a-z0-9_]*)*)$/.test(input) ? true : "Please provide valid package name"
			},
			{
				type : 'list',
				name : 'rdbms',
				message : 'Which relational database you want to configure for your project?',
				choices : [ 'None', 'Mysql' ],
				default : 'None'
			},
			{
				type : 'list',
				name : 'auth',
				message : 'Which authentication mechanism you want to configure for your project?',
				choices : [ 'None', 'JWT' ],
				default : 'None'
			},
			{
				type : 'confirm',
				name : 'samplecontroller',
				message : 'Do you want us to create sample rest controller?',
				default : false
			} ]).then((answers) => {
			this.userinput = answers;
			this.packagedir = answers.package.replace(".", "/"),
			this.capitalizedProjectName = this.userinput.name.charAt(0).toUpperCase() + this.userinput.name.slice(1)

			this.templateProperites = {
				NAME : this.userinput.name,
				CAPITALIZED_NAME : this.capitalizedProjectName,
				PACKAGE : this.userinput.package,
				RDBMS : this.userinput.rdbms,
				AUTH : this.userinput.auth
			};

		});
	}

	writing() {
		this.copy('gradlew', 'gradlew');
		this.copy('gradlew.bat', 'gradlew.bat');
		this.copy('gradle/wrapper/gradle-wrapper.jar', 'gradle/wrapper/gradle-wrapper.jar');
		this.copy('gradle/wrapper/gradle-wrapper.properties', 'gradle/wrapper/gradle-wrapper.properties');
		this.copyTpl('build.gradle', 'build.gradle');
		this.copyTpl('src/main/scala/apppackage/SpringbootscalaApplication.scala',
			'src/main/scala/' + this.packagedir + '/' + this.capitalizedProjectName + 'Application.scala');
		this.copyTpl('src/main/scala/apppackage/config/ApplicationProperties.scala',
			'src/main/scala/' + this.packagedir + '/config/ApplicationProperties.scala');
		this.copyTpl('src/main/scala/apppackage/config/WebConfig.scala',
			'src/main/scala/' + this.packagedir + '/config/WebConfig.scala');

		if (this.userinput.samplecontroller) {
			this.copyTpl('src/main/scala/apppackage/web/rest/SampleController.scala',
				'src/main/scala/' + this.packagedir + '/web/rest/SampleController.scala');
			this.copyTpl('src/main/scala/apppackage/service/SampleService.scala',
				'src/main/scala/' + this.packagedir + '/service/SampleService.scala');
		}

		this.copyTpl('src/main/resources/application.yml',
			'src/main/resources/application.yml');

		if (this.templateProperites.RDBMS !== 'None') {
			this.copyTpl('src/main/resources/liquibase',
				'src/main/resources/liquibase');
		}

		if (this.templateProperites.AUTH !== 'None') {
			if (this.templateProperites.AUTH === 'JWT') {
				this.copyTpl('src/main/scala/apppackage/config/JWTSecurityConfig.scala',
					'src/main/scala/' + this.packagedir + '/config/SecurityConfig.scala');
				this.copyTpl('src/main/scala/apppackage/web/rest/vm/user/LoginVM.scala',
					'src/main/scala/' + this.packagedir + '/web/rest/vm/user/LoginVM.scala');
				this.copyTpl('src/main/scala/apppackage/web/rest/vm/user/LoginResponseVM.scala',
					'src/main/scala/' + this.packagedir + '/web/rest/vm/user/LoginResponseVM.scala');
				this.copyTpl('src/main/scala/apppackage/web/rest/UsersController.scala',
					'src/main/scala/' + this.packagedir + '/web/rest/UsersController.scala');
				this.copyTpl('src/main/scala/apppackage/security/jwt',
					'src/main/scala/' + this.packagedir + '/security/jwt');
			}
		}
	}

};