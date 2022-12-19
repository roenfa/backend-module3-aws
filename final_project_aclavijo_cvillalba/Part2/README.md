# AWS API Gateway demo

This api gateway handles a GET request with the following structure:
```sh
https://${apiGateway}.execute-api.${AWS::Region}.amazonaws.com/${apiGatewayStageName}/transactions/{params}
```
The actual URL is going to be revealed after api's deloyment. The params variable may be passed like this: "id=1" or "type=REFUND"

Below's commnad will deploy all necessary stack (profile must be provided):
```sh
./deploy.sh ${profile}
```
To undeploy run:
```sh
./undeploy.sh ${profile}
```