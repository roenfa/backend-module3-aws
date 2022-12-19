This api gateway handles a GET request with the following structure:

https://${apiGateway}.execute-api.${AWS::Region}.amazonaws.com/${apiGatewayStageName}/transactions/{id}

The actual URL is going to be revealed after api's deloyment.

Bellow's commnad will deploy the api gateway stack (profile must be provided):

./deploy.sh ${profile}

To undeploy run:

./undeploy.sh ${profile}

