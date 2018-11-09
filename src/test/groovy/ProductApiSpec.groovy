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


import groovyx.net.http.RESTClient
import spock.lang.Specification

class ProductApiSpec extends Specification {
    static String testURL = "http://localhost:3030"

    RESTClient restClient = new RESTClient(testURL)

    def 'User Should be able to perform Create a Product'() {

        given:
        def requestBody =
                [
                        "name"        : "string",
                        "type"        : "string",
                        "price"       : 0,
                        "shipping"    : 0,
                        "upc"         : "string",
                        "description" : "string",
                        "manufacturer": "string",
                        "model"       : "string",
                        "url"         : "string",
                        "image"       : "string"
                ]

        when:
        def response = restClient.post(path: '/products',
                body: requestBody,
                requestContentType: 'application/json')
        def testUserId = response.responseData.id

        then:
        response.status == 201

        cleanup:
        deleteTestUser(testUserId)
    }


    def 'User should be able to perform Read Request'() {
        setup:
        def testUserId = createTestUser().responseData.id

        when:
        def response = restClient.get(path: '/products/' + testUserId)

        then:
        response.status == 200

        and:
        response.responseData.id
        response.responseData.type
        response.responseData.price == 0

        cleanup:
        deleteTestUser(testUserId)

    }

    def 'User should be able to perform Update Request'() {
        setup:
        def testUserId = createTestUser().responseData.id

        when:
        def updatedUser =
                [
                        "name"        : "string2",
                        "type"        : "string3",
                        "price"       : 0,
                        "shipping"    : 0,
                        "upc"         : "string",
                        "description" : "string",
                        "manufacturer": "string",
                        "model"       : "string",
                        "url"         : "string",
                        "image"       : "string"
                ]
        def response = restClient.put(path: '/products/' + testUserId, body: updatedUser, requestContentType: 'application/json')

        then:
        response.status == 200

        cleanup:
        deleteTestUser(testUserId)
    }

    def 'User should be able to perform Delete Request'() {
        setup:
        def testUserId = createTestUser().responseData.id

        when:
        def response = restClient.delete(path: '/products/' + testUserId)

        then:
        response.status == 200

    }


    def createTestUser() {
        def requestBody =
                [
                        "name"        : "string",
                        "type"        : "string",
                        "price"       : 0,
                        "shipping"    : 0,
                        "upc"         : "string",
                        "description" : "string",
                        "manufacturer": "string",
                        "model"       : "string",
                        "url"         : "string",
                        "image"       : "string"
                ]

        return restClient.post(path: '/products',
                body: requestBody,
                requestContentType: 'application/json')

    }

    def deleteTestUser(def userId) {
        return restClient.delete(path: '/products/' + userId)

    }

}