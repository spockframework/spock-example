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

class StoreApiSpec extends Specification {
    static String testURL = "http://localhost:3030"

    RESTClient restClient = new RESTClient(testURL)

    def 'User Should be able to perform Create a Store'() {

        given:
        def requestBody =
                [
                        "name": "string",
                        "type": "string",
                        "address": "string",
                        "address2": "string",
                        "city": "string",
                        "state": "string",
                        "zip": "string",
                        "lat": 0,
                        "lng": 0,
                        "hours": "string",
                        "services": {}
                ]

        when:
        def response = restClient.post(path: '/stores',
                body: requestBody,
                requestContentType: 'application/json')
        def testStoresId = response.responseData.id

        then:
        response.status == 201

        cleanup:
        deleteTestStores(testStoresId)
    }


    def 'User should be able to perform Read Request'() {
        setup:
        def testStoresId = createTestStores().responseData.id

        when:
        def response = restClient.get(path: '/stores/' + testStoresId)

        then:
        response.status == 200

        and:
        response.responseData.id
        response.responseData.type
        response.responseData.price == 0

        cleanup:
        deleteTestStores(testStoresId)

    }

    def 'User should be able to perform Update Request'() {
        setup:
        def testStoresId = createTestStores().responseData.id

        when:
        def updatedStores =
                [

                    "name": "string",
                    "type": "string",
                    "address": "string",
                    "address2": "string",
                    "city": "string",
                    "state": "string",
                    "zip": "string",
                    "lat": 0,
                    "lng": 0,
                    "hours": "string",
                    "services": {}

                ]
        def response = restClient.put(path: '/stores/' + testStoresId, body: updatedStores, requestContentType: 'application/json')

        then:
        response.status == 200

        cleanup:
        deleteTestStores(testStoresId)
    }

    def 'User should be able to perform Delete Request'() {
        setup:
        def testStoresId = createTestStores().responseData.id

        when:
        def response = restClient.delete(path: '/products/' + testStoresId)

        then:
        response.status == 200

    }


    def createTestStores() {
        def requestBody =
                [

                        "name": "string",
                        "type": "string",
                        "address": "string",
                        "address2": "string",
                        "city": "string",
                        "state": "string",
                        "zip": "string",
                        "lat": 0,
                        "lng": 0,
                        "hours": "string",
                        "services": {}

                ]

        return restClient.post(path: '/stores',
                body: requestBody,
                requestContentType: 'application/json')

    }

    def deleteTestStores(def storeId) {
        return restClient.delete(path: '/stores/' + storeId)

    }

}