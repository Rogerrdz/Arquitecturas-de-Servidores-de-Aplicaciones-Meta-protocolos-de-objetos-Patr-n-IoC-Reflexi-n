# Arquitecturas de Servidores de Aplicaciones Meta protocolos de objetos Patron IoC Reflexion

A lightweight web server built in Java from scratch, capable of serving static HTML and PNG files, and providing an IoC framework for building web applications from POJOs using reflection and annotations.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need to have installed:

- Java 17
- Maven 3.8+
```
java -version
mvn -version
```

### Installing

Clone the repository
```
git clone https://github.com/Rogerrdz/Arquitecturas-de-Servidores-de-Aplicaciones-Meta-protocolos-de-objetos-Patr-n-IoC-Reflexi-n.git
```

Navigate to the project folder
```
cd Arquitecturas-de-Servidores-de-Aplicaciones-Meta-protocolos-de-objetos-Patr-n-IoC-Reflexi-n
```

Compile the project
```
mvn compile
```

Run the server
```
java -cp target/classes edu.escuelaing.arep.MicroSpringBoot2
```

Open your browser and go to
```
http://localhost:8082
```

To go to the home page go to :
```
http://localhost:8082/index.html
```

## Running the tests
```
mvn test
```

The project includes 12 automated tests covering all controller endpoints:

![Test Execution](/AppWebServe/src/images/test_execution.png)

### Break down into end to end tests

The test verifies the basic functionality of the application context.
```
mvn test -Dtest=AppTest
```

Results: **Tests run: 16, Failures: 0, Errors: 0, Skipped: 0**

### And coding style tests

Verifies that the project compiles correctly with Java 17 standards.
```
mvn compile
```

## Deployment

Build the JAR file
```
mvn package
```

Copy the JAR to your AWS EC2 instance
```
scp -i your-key.pem target/SpringBoot-1.0-SNAPSHOT.jar ec2-user@<your-ec2-dns>:~
```

Connect to the instance and run the server
```
ssh -i your-key.pem ec2-user@<your-ec2-dns>
nohup java -jar SpringBoot-1.0-SNAPSHOT.jar &
```

Open port 8082 in your EC2 Security Group and access the server at
```
http://<your-ec2-dns>:8082
```

### Evidence

Server running on AWS EC2:

![AWS Server Running](/AppWebServe/src/images/created_instance.png)

Local Folder to connection :

![.ssh and Conecction with the server](/AppWebServe/src/images/folder_with_keys_and_classes.png)

Installation of Corretto 21 :

![Corretto 21 ](/AppWebServe/src/images/Install_the_JDK_for_Amazon_Corretto_21_server.png)

![Corretto 21 ](/AppWebServe/src/images/Install_the_JDK_for_Amazon_Corretto_21_server_complete%20.png)


Conecction to put compiled classes in to the server :

![First Conecction](/AppWebServe/src/images/sftp_connection.png)

Unzip classes:

![Conecction ](/AppWebServe/src/images/unzip_calsses.zip.png)

Correct execution from the server :

![Conecction ](/AppWebServe/src/images/execution_MicroSpringBoot2..png)

Configuration of non-consolidated Rules in the security group:

![Conecction ](/AppWebServe/src/images/unboundes_rules_security_group_server.png)

Application accessed via public DNS :

![Application on AWS](/AppWebServe/src/images/correct_execution_with_public_DNS_server.png)

- Home page:

![Home](/AppWebServe/src/images/correct_execution_with_public_DNS_server_index.png)

- Royte /pi:

![Application on AWS](/AppWebServe/src/images/correct_execution_with_public_DNS_server_pi.png)

- Route /euler:

![Application on AWS](/AppWebServe/src/images/correct_execution_with_public_DNS_server_e.png)

- Route /hello:

![Application on AWS](/AppWebServe/src/images/correct_execution_with_public_DNS_server_hello.png)

- Route /hello?name=Roger:
  
![Application on AWS](/AppWebServe/src/images/correct_execution_with_public_DNS_server_hello_name.png)

To this part you can change de name in the path to get personalized greeting 

- Route /greeting?name=Juan

![Application on AWS](/AppWebServe/src/images/correct_execution_with_public_DNS_server_greeting_name.png)

- Route /greeting/bye?name=Juan

![Application on AWS](/AppWebServe/src/images/correct_execution_with_public_DNS_server_greeting_bye.png)

- Route /greeting/welcome?name=Roger

![Application on AWS](/AppWebServe/src/images/correct_execution_with_public_DNS_server_greeting_welcome.png)

## Built With

* [Java 17](https://www.oracle.com/java/) - Programming language
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Roger Rodriguez** - *Initial work* - [Rogerrdz](https://github.com/Rogerrdz)

## Acknowledgments

* Based on the Web Framework project by Luis Daniel Benavides Navarro
* Inspired by Spring Boot's IoC and annotation-driven architecture