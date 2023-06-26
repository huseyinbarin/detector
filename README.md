# Company Mention Finder

The Company Mention Finder is a Java application that processes XML files and identifies companies mentioned in the title and text of each file. It uses a CSV file containing a list of company names to match against the XML content.

## Usage

To run the Company Mention Finder, follow these steps:

1. Make sure you have Java 17 installed on your system.
2. Open the terminal or command prompt.
3. In order to pack the project into jar please use `mvn clean install` command.
4. In order to run tests please use `mvn verify` command.
5. Navigate to the directory where the Company Mention Finder JAR file is located.
6. Run the following command: `java -jar detector-1.0.jar  csvfilepath  xmlfilespath`

The application will start processing the XML files and display the execution time and the number of mentioned companies found.

## Dependencies

The Company Mention Finder relies on the following libraries:

- OpenCSV (version 5.5.2): Used for parsing the CSV file that contains the list of company names.
- JUnit Jupiter API (version 5.8.1): Used for unit testing.
- JUnit Jupiter Engine (version 5.8.1): Used for unit testing.
- Mockito Core (version 3.12.4): Used for mocking dependencies in unit tests.

## Future Improvements

Here are some possible improvements that could be made to enhance the functionality and maintainability of the Company Mention Finder:

1. **Command-line Arguments**: +
2. **Error Handling**: Implement more robust error handling and reporting. Currently, errors are printed to the console, but a more user-friendly error message or logging mechanism could be added.
3. **Input Validation**: Validate the input data (CSV file, XML files) to ensure they conform to expected formats and handle edge cases gracefully.
4. **Multithreading Optimization**: Experiment with different thread pool sizes and optimization techniques to improve the performance of processing XML files concurrently.
5. **Logging**: Integrate a logging framework (e.g., Log4j, SLF4J) to provide detailed logs during the execution of the application, making it easier to debug and monitor.
6. **Configurable Properties**: Externalize configuration properties (e.g., thread pool size, logging level) into a separate configuration file (e.g., properties file, YAML file) for easier customization without modifying the source code.
7. **Unit Testing**: Add more unit tests to cover different scenarios, such as error handling, edge cases, and additional functionality. Consider using parameterized tests for testing with different input data.
8. **Integration Testing**: Develop integration tests to ensure the components of the application work correctly together and handle real-world scenarios.

