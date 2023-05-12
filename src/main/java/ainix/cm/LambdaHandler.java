package ainix.cm;

import ainix.cm.service.EmployeService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        EmployeService employeService=new EmployeService();
        String httpMethod= apiGatewayProxyRequestEvent.getHttpMethod();
        switch (httpMethod){
            case "POST":
                //Todo : save employee
                return employeService.saveEmployee(apiGatewayProxyRequestEvent, context);
            case "GET":
                if (apiGatewayProxyRequestEvent.getPathParameters()!=null){
                    //Todo: get employe by id
                    return employeService.getEmployeId(apiGatewayProxyRequestEvent,context);
                }else {
                    // todo get all employees
                    return employeService.getEmployeAll(apiGatewayProxyRequestEvent, context);
                }
            case "DELETE":
                if (apiGatewayProxyRequestEvent.getPathParameters()!=null){
                    //Todo : delete employee
                    return employeService.deleteEmployee(apiGatewayProxyRequestEvent, context);
                }
            case "PUT":
                return employeService.updateEmploye(apiGatewayProxyRequestEvent, context);
            default:
                // todo: throw some errors
                throw new Error("Unsupoted Method ::::"+ apiGatewayProxyRequestEvent.getHttpMethod());
        }
    }
}
