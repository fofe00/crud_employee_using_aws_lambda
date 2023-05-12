package ainix.cm.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@DynamoDBTable(tableName = "employee")
public class Employe {
    @DynamoDBHashKey(attributeName = "employeId")
    private String employeId;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "email")
    private String email;
}
