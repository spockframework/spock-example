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

class StackWithOneElementSpec extends Specification {
  def stack = new Stack()

  def setup() {
    stack.push("elem")
  }

  def "size"() {
    expect: stack.size() == 1
  }

  def "pop"() {
    when:
    def x = stack.pop()

    then:
    x == "elem"
    stack.size() == 0
  }

  def "peek"() {
    when:
    def x = stack.peek()

    then:
    x == "elem"
    stack.size() == 1
  }

  def "push"() {
    when:
    stack.push("elem2")

    then:
    stack.size() == 2
    stack.peek() == "elem2"
  }
}
