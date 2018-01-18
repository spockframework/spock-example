/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import spock.lang.Specification

class StackWithThreeElementsSpec extends Specification {
  def stack = new Stack()

  def setup() {
    ["elem1", "elem2", "elem3"].each { stack.push(it) }
  }

  def "size"() {
    expect: stack.size() == 3
  }

  def "pop"() {
    expect:
    stack.pop() == "elem3"
    stack.pop() == "elem2"
    stack.pop() == "elem1"
    stack.size() == 0
  }

  def "peek"() {
    expect:
    stack.peek() == "elem3"
    stack.peek() == "elem3"
    stack.peek() == "elem3"
    stack.size() == 3
  }

  def "push"() {
    when:
    stack.push("elem4")

    then:
    stack.size() == 4
    stack.peek() == "elem4"
  }
}
