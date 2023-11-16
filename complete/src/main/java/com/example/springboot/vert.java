import com.google.cloud.aiplatform.v1beta1.*;
import com.google.protobuf.Value;
import com.google.protobuf.util.JsonFormat;

public class VertexAISample {

    public static void main(String[] args) throws Exception {
        // Replace these values with your project, location, and model information
        String projectId = "your-project-id";
        String location = "us-central1";
        String modelId = "your-model-id";
        String endpointId = "your-endpoint-id";
        String instanceContent = "{\"input_key\": \"input_value\"}";

        try (EndpointServiceClient endpointServiceClient = EndpointServiceClient.create()) {
            // Get the model resource name
            ModelName modelName = ModelName.of(projectId, location, modelId);

            // Get the endpoint resource name
            EndpointName endpointName = EndpointName.of(projectId, location, endpointId);

            // Prepare the instance for prediction
            Value.Builder inputValueBuilder = Value.newBuilder();
            JsonFormat.parser().merge(instanceContent, inputValueBuilder);

            // Build the online prediction request
            PredictRequest predictRequest =
                    PredictRequest.newBuilder()
                            .setName(endpointName.toString())
                            .setPayload(
                                    Value.newBuilder()
                                            .putFields("input_key", inputValueBuilder.build())
                                            .build())
                            .build();

            // Perform online prediction
            PredictResponse predictResponse = endpointServiceClient.predict(predictRequest);

            // Process the prediction result
            System.out.println("Prediction result:");
            System.out.println(predictResponse.getPayload());
        }
    }
}
