[![Build Status](https://travis-ci.org/marc-/got.svg)](https://travis-ci.org/marc-/got)
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
##How to extend
###Creating own command
All command has to implement `org.github.got.Command` interface. Consider extending abstract class `org.github.got.commands.AbstractCommand`.
Every command has to implement `org.github.got.Command.issue(Context)`, wich is run when user enter specified (see CommandA annotation for details). For output to console following methods from `AbstractCommand` can be used:
 - message
 - messageRaw
 - system
 - systemRaw
 - game
 - gameRaw
 - oracle
 - player
 - playerRaw
 - emotion
 - combat
 - combatHit
 - combatMiss
 - hostile
Every method associated with color scheme. `*Raw` methods prints strings as isi, instead of using it as key for resource bundle (`java.util.ResourceBundle`).

For user input mthod `org.github.got.Context.getScanner()` provides instance of `java.util.Scanner`.

###Annotate command
New commands has to be annotated with `org.github.got.CommandA` annotation:
 - value - template (can be regexp) user will run from console
 - scope - phase when command can be executed (see `org.github.got.Context.Scope` for more details)
 - start - specifies scope which command supposed to start (for instance, current scope is `location` will be switched `combat` if start=Scope.COMBAT)
 - runOnce - if command has to be run only once
###Register command
Register command using static method `org.github.got.commands.Engine.registerCommand(Command)`. It can be done at `org.github.got.Game.Game()` constructor.

##License
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
