/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rallyTestExecutorPkg;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author cpandit
 */
public class GetObject {

    /**
     * 
     * @param restApi - Valid Rally Connection
     * @param strObjectName // Testcase, HierarchicalRequirement, Project, Workspace, TestFolder, TestCase
     * @param strUniqueFilterName //FormattedID, Name
     * @param strUniqueFilterValue // Value TC01, US12312, TF243
     * @param strValueToReturn // _ref, Name, FormattedID, etc
     * @return string
     */
    public String udsGetObjectProperty(
            RallyRestApi restApi,ApplicationGlobals globals,
            String strObjectName, 
            String strUniqueFilterName, 
            String strUniqueFilterValue,// Value TC01, US12312, TF243
            String strValueToReturn 
    ) {
        String strValue = null;
        try {

            //ApplicationGlobals globals = new ApplicationGlobals();
             // TODO Auto-generated method stub
            String host = globals.host;//"https://rally1.rallydev.com";
            String strAPIKey = globals.strAPIKey;//"_zHt27zWNTwedqD1OgiKH9q0LnypiTkAIgZnquizeg40";//"_oQn2Zs9IQMavLxZtz3F0j75piRIWk0IZrKDCDr1nCHE";//"_H1YAhp4RzCxWr2hZbOvOjXNU3CkHVk5JfMDo7KPM";
           
            restApi = new RallyRestApi(
                    new URI(host),
                    strAPIKey
            /*username, password*/);
            
            QueryRequest storyRequest = new QueryRequest(strObjectName);//"HierarchicalRequirement");
            //storyRequest.setQueryFilter(new QueryFilter("Name", "=", "PSA-DPM"));//US89611 // "Cisco Information Technology"));//strUserStoryNumber));
            storyRequest.setQueryFilter(new QueryFilter(
                    strUniqueFilterName, "=", strUniqueFilterValue));//strUserStoryNumber));
            QueryResponse rallyUserStoryQuery = restApi.query(storyRequest);
            if (rallyUserStoryQuery == null || rallyUserStoryQuery.getResults().size() == 0) {
                //popup - cannot query
                return ("No Object found ..");
            } else {
                //System.out.println("rallyUserStoryQuery.getResults(): " + rallyUserStoryQuery.getResults());
                for (int i = 0; i < 1//i < rallyUserStoryQuery.getResults().size()
                        ; i++) {
                    String strReturnProperty = rallyUserStoryQuery.getResults().get(i).getAsJsonObject().get(strValueToReturn).getAsString();
                    strValue = strReturnProperty;
                }
            }

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            try {
                restApi.close();
            } catch (IOException ex) {
                Logger.getLogger(Form_RallyTestExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return strValue;
    }

    public static void main(String[] args) {
        
       RallyRestApi restApi = null;
        try {

            String strUserAlias = "cpandit@cisco.com";

            // TODO Auto-generated method stub
            String host = "https://rally1.rallydev.com";
            String strAPIKey = "_oQn2Zs9IQMavLxZtz3F0j75piRIWk0IZrKDCDr1nCHE";//"_H1YAhp4RzCxWr2hZbOvOjXNU3CkHVk5JfMDo7KPM";
            //Use https://help.rallydev.com/rally-application-manager guide for generating API Keys
            //String strUser = "cpandit@cisco.com";
            //String username = "vkuruvan@cisco.com";
            //String password = "Nonveg!123";
            String wsapiVersion = "v2.0";
            //String projectRef = "/project/2222";      
            //String workspaceRef = "/workspace/11111"; 
            String applicationName = "_UpdateTCR";//This API Caller Application

            System.out.println("Connecting..." + host + " ");

            restApi = new RallyRestApi(
                    new URI(host),
                    strAPIKey
            /*username, password*/);
            restApi.setWsapiVersion(wsapiVersion);
            restApi.setApplicationName(applicationName);
            GetObject obj = new GetObject();
            //String strUserStoryRef = obj.udsGetObjectProperty(restApi, "TestFolder", "FormattedID", "TF15461", "_ref");
           // System.out.println("strUserStoryRef: "+strUserStoryRef);
            // https://rally1.rallydev.com/slm/webservice/v2.0/testcase/106620277848
            
          // String strUserStoryRef = udsGetObjectProperty(restApi, "TestCase", "FormattedID", "TC277022", "_ref");
           // System.out.println("strUserStoryRef: "+strUserStoryRef);
            //https://rally1.rallydev.com/slm/webservice/v2.0/testcase/106474355180
            //Read User
            /*
            QueryRequest userRequest = new QueryRequest("User");
            userRequest.setFetch(new Fetch("UserName", "Subscription", "DisplayName", "SubscriptionAdmin"));
            userRequest.setQueryFilter(new QueryFilter("UserName", "=", strUserAlias));

            QueryResponse userQueryResponse = restApi.query(userRequest);
            JsonArray userQueryResults = userQueryResponse.getResults();
            JsonElement userQueryElement = userQueryResults.get(0);
            JsonObject userQueryObject = userQueryElement.getAsJsonObject();
            String userRef = userQueryObject.get("_ref").getAsString();
            System.out.println("userRef: " + userRef);
            System.out.println("Connecting to rally for testcases... Please wait");

            //String projectRef = "/PSA-DPM";
            // Query for Test Case to which we want to add results
            QueryRequest storyRequest = new QueryRequest("Project");//"HierarchicalRequirement");
            storyRequest.setLimit(3);
            //storyRequest.setWorkspace("Cisco Information Technology");
            //storyRequest.setProject("PSA-DPM");
            //storyRequest.setProject(projectRef);
            //storyRequest.setFetch(new Fetch("FormattedID", "Name"));

            //storyRequest.setQueryFilter(new QueryFilter("Name", "=", "PSA-DPM"));//US89611 // "Cisco Information Technology"));//strUserStoryNumber));
            storyRequest.setQueryFilter(new QueryFilter("Name", "=", "	PSA-DPM"));//strUserStoryNumber));
            QueryResponse rallyUserStoryQuery = restApi.query(storyRequest);

            if (rallyUserStoryQuery == null || rallyUserStoryQuery.getResults().size() == 0) {
                //popup - cannot query
                System.out.println("No Object found ..");
            }

            boolean rc = false;
            System.out.println("rallyUserStoryQuery.getResults(): " + rallyUserStoryQuery.getResults());
            for (int i = 0; i < rallyUserStoryQuery.getResults().size(); i++) {

                JsonObject userStoryJsonObject = rallyUserStoryQuery.getResults().get(i).getAsJsonObject();

                //"FormattedID":"TC43163","Name":"Submit Cost Forecast"
                String strProjectName = rallyUserStoryQuery.getResults().get(i).getAsJsonObject().get("Name").getAsString();
                //String strName = rallyUserStoryQuery.getResults().get(i).getAsJsonObject().get("Name").getAsString();
                String objRef = rallyUserStoryQuery.getResults().get(i).getAsJsonObject().get("_ref").getAsString();

            }
             */
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                restApi.close();
            } catch (IOException ex) {
                Logger.getLogger(Form_RallyTestExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
