package ainix.cm.service;

import ainix.cm.entity.Employe;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.apache.http.HttpStatus;

import java.io.File;
import java.util.List;
import java.util.Map;

public class EmployeService {
    private DynamoDBMapper dynamoDBMapper;
    private static  String jsonBody = null;
    private String BUCET_NAME="employee_bucket";
    private void iniDb(){
        AmazonDynamoDB client= AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper=new DynamoDBMapper(client);
    }
    public APIGatewayProxyResponseEvent saveEmployee(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context){
        iniDb();
        //Employe employe=Utility.convertStringToObj(apiGatewayProxyRequestEvent.getBody(),context);
        Employe employee = Util.convertStringToObj(apiGatewayProxyRequestEvent.getBody(),context);
        dynamoDBMapper.save(employee);
        jsonBody = Util.convertObjToString(employee,context) ;
        context.getLogger().log("data saved successfully to dynamodb:::" + jsonBody);
        return createAPIResponse(jsonBody,201,Util.createHeaders());
    }

    public APIGatewayProxyResponseEvent getEmployeId(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context){
        iniDb();
        String empId=apiGatewayProxyRequestEvent.getPathParameters().get("empId");
        Employe employe=dynamoDBMapper.load(Employe.class,empId);
        if (employe!=null){
            jsonBody=Util.convertObjToString(employe,context);
            return createAPIResponse(jsonBody,HttpStatus.SC_CREATED,Util.createHeaders());
        }else {
            jsonBody="Employe not found Exeption ::::"+empId;
            return createAPIResponse(jsonBody, HttpStatus.SC_NOT_FOUND,Util.createHeaders());
        }
    }

    public APIGatewayProxyResponseEvent getEmployeAll(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context){
        iniDb();
        List<Employe> employe=dynamoDBMapper.scan(Employe.class,new DynamoDBScanExpression());
        jsonBody=Util.convertListOfObjToString(employe,context);
        context.getLogger().log("get all data:::: "+ jsonBody);
        return createAPIResponse(jsonBody,HttpStatus.SC_OK,Util.createHeaders());
    }
    public APIGatewayProxyResponseEvent deleteEmployee(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,Context context){


        String empId= apiGatewayProxyRequestEvent.getPathParameters().get("empId");
        Employe employe=dynamoDBMapper.load(Employe.class,empId);
        if (employe!=null){
            dynamoDBMapper.delete(employe);
            context.getLogger().log("delete employer ok");
            return createAPIResponse("data deleted successfully." + empId,HttpStatus.SC_OK,Util.createHeaders());
        }else{
            return createAPIResponse("Employe not found Exeption" + empId,HttpStatus.SC_NOT_FOUND,Util.createHeaders());
        }
    }

    public APIGatewayProxyResponseEvent updateEmploye(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,Context context){
        String empId= apiGatewayProxyRequestEvent.getPathParameters().get("empId");
        Employe employe=dynamoDBMapper.load(Employe.class,empId);
        Employe employe1=Util.convertStringToObj(apiGatewayProxyRequestEvent.getBody(),context);
        if (employe!=null){
            employe.setName(employe1.getName());
            employe.setEmail(employe1.getEmail());
            dynamoDBMapper.save(employe);
            context.getLogger().log("update employer ok");
            return createAPIResponse("data update successfully." + empId,HttpStatus.SC_OK,Util.createHeaders());
        }else{
            return createAPIResponse("Employe not found Exeption" + empId,HttpStatus.SC_NOT_FOUND,Util.createHeaders());
        }
    }


    private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String,String> headers ){
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }
}
