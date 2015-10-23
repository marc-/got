![travis-ci badge](https://travis-ci.org/marc-/got.svg)
#Game of Thorns
Simple console text based game written in java. 
##Requirements
* java 8
* xterm terminal emulator
* maven 3.3.3

##Run
In your terminal run:
```shell
java -cp target/got-0.0.1-SNAPSHOT.jar org.github.got.Game
```
In docker:
```shell
docker run --rm -ti eit8ei8n/got
```

##Build
To build the game run:
```shell
mvn clean install -DskipTests=true
```

## License
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
