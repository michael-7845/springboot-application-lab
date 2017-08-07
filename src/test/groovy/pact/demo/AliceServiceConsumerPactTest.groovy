package pact.demo

import au.com.dius.pact.consumer.PactVerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import groovyx.net.http.RESTClient
import org.junit.Test

class AliceServiceConsumerPactTest {

    @Test
    void "A service consumer side of a pact goes a little something like this"() {

        def alice_service = new PactBuilder() // Create a new PactBuilder
        alice_service {
            serviceConsumer "Consumer" 	// Define the service consumer by name
            hasPactWith "Alice Service"   // Define the service provider that it has a pact with
            port 1234                       // The port number for the service. It is optional, leave it out to
            // to use a random one

            given('there is some good mallory') // defines a provider state. It is optional.
            uponReceiving('a retrieve Mallory request') // upon_receiving starts a new interaction
            withAttributes(method: 'get', path: '/mallory')		// define the request, a GET request to '/mallory'
            willRespondWith(						// define the response we want returned
                    status: 200,
                    headers: ['Content-Type': 'text/html'],
                    body: '"That is some good Mallory."'
            )
        }

        // Execute the run method to have the mock server run.
        // It takes a closure to execute your requests and returns a PactVerificationResult.
        PactVerificationResult result = alice_service.runTest {
            def client = new RESTClient('http://localhost:1234/')
            def alice_response = client.get(path: '/mallory')

            assert alice_response.status == 200
            assert alice_response.contentType == 'text/html'

            def data = alice_response.data.text()
            assert data == '"That is some good Mallory."'
        }
        assert result == PactVerificationResult.Ok.INSTANCE  // This means it is all good

    }
}
