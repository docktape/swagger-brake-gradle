# Swagger Brake Gradle plugin
Gradle plugin for [Swagger Brake](https://github.com/docktape/swagger-brake).

The plugin documentation can be found [here](https://docktape.github.io/swagger-brake/gradle/).

## License
```text
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Publishing
For snapshot:
`./gradlew clean publish`

For release:
`./gradlew clean publishPlugins jreleaserFullRelease`