# AWS Kinesis Analytics Flink Runtime
This library can be used to access externalised KDA Flink application properties at runtime. 
See the [documentation][aws-documentation] for a guide on how to configure properties in your KDA application. 

- [AWS Documentation][aws-documentation]
- [Issues][issues]

## Quickstart
Access properties configured via the AWS Console/SDK using:

```java
Properties properties = KinesisAnalyticsRuntime.getApplicationProperties().get("app-group");
String inputStreamName = properties.getProperty("inputStreamName");
String outputStreamName = properties.getProperty("outputStreamName");
```

## Building from Source
1. You will need to install Java 1.8+ and Maven
1. Clone the repository from Github
1. Build using Maven from the project root directory: 
    1. `$ mvn clean install`

## Flink Version Matrix

Connector Version | Flink Version | Release Date
----------------- | ------------- | ------------
1.1.0 | 1.8.3 | Dec, 2019
1.0.1 | 1.6.2 | Dec, 2018
1.0.0 | 1.5.0 | Dec, 2018 

## License

This library is licensed under the Apache 2.0 License. 

[aws-documentation]: https://docs.aws.amazon.com/kinesisanalytics/latest/java/how-properties.html
[issues]: https://github.com/aws/aws-kinesisanalytics-runtime/issues
