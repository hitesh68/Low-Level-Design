#!/bin/bash

mvn clean install -DskipTests assembly:single -q -e
java -jar target/geektrust.jar sample_input3.txt