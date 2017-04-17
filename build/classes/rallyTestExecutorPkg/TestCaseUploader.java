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
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static rallyTestExecutorPkg.GetObject.*;
import static rallyTestExecutorPkg.Form_RallyTestExecutor.getCurrentDate;

/**
 *
 * @author cpandit
 */
public class TestCaseUploader {

    static RallyRestApi restApi = null;
    public static void main(String[] args) {
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
            boolean rc = false;

            System.out.println("Connecting..." + host + " ");

            restApi = new RallyRestApi(
                    new URI(host),
                    strAPIKey
            /*username, password*/);
            restApi.setWsapiVersion(wsapiVersion);
            restApi.setApplicationName(applicationName);

            //Read User ref
            QueryRequest userRequest = new QueryRequest("User");
            userRequest.setFetch(new Fetch("UserName", "Subscription", "DisplayName", "SubscriptionAdmin"));
            userRequest.setQueryFilter(new QueryFilter("UserName", "=", strUserAlias));
            QueryResponse userQueryResponse = restApi.query(userRequest);
            JsonArray userQueryResults = userQueryResponse.getResults();
            JsonElement userQueryElement = userQueryResults.get(0);
            JsonObject userQueryObject = userQueryElement.getAsJsonObject();
            String userRef = userQueryObject.get("_ref").getAsString();
            System.out.println("userRef: " + userRef);
            System.out.println("Connecting to rally... Please wait");

            String strWorkspaceName = "Cisco Information Technology";
            String strProjectName = "PSA-DPM";
            String strUserStoryNumber = "US89611";
            String strTestFolder = "TF15587";
            GetObject obj = new GetObject();
            ApplicationGlobals globals = new ApplicationGlobals();
            //Get workspace reference
            String strWorkspaceRef = obj.udsGetObjectProperty(restApi,globals, "Workspace", "Name", strWorkspaceName, "_ref");
            //TimeUnit.SECONDS.sleep(3);
            if (strWorkspaceRef == null || strWorkspaceRef.contains("http") == false) {
                System.out.println("Unable to fetch Workspace with Name : " + strWorkspaceName);
                return;
            } else {
                System.out.println("Found Workspace with Name : " + strWorkspaceName);
            }

            //Get project ref
            String strProjectRef = obj.udsGetObjectProperty(restApi, globals,"Project", "Name", strProjectName, "_ref");
            //TimeUnit.SECONDS.sleep(3);
            if (strProjectRef == null || strProjectRef.contains("http") == false) {
                System.out.println("Unable to fetch project with Name : " + strProjectName);
                return;
            } else {
                System.out.println("Found project with Name : " + strProjectName);
            }

            //Fetch the user story ref
            String strWorkProductRef = obj.udsGetObjectProperty(restApi, globals,"hierarchicalrequirement", "FormattedID", strUserStoryNumber, "_ref");
            //TimeUnit.SECONDS.sleep(3);
            if (strWorkProductRef == null || strWorkProductRef.contains("http") == false) {
                System.out.println("Unable to fetch user story with # : " + strUserStoryNumber);
                return;
            } else {
                System.out.println("Found user story with # : " + strUserStoryNumber);
            }

            //Fetching Test folder
            String strTestFolderRef = obj.udsGetObjectProperty(restApi, globals,"TestFolder", "FormattedID", strTestFolder, "_ref");
            //TimeUnit.SECONDS.sleep(3);
            if (strTestFolderRef == null || strTestFolderRef.contains("http") == false) {
                System.out.println("Unable to fetch Test folder with # : " + strTestFolder);
                return;
            } else {
                System.out.println("Found  Test folder with # : " + strTestFolder);
            }

            if (strTestFolderRef == null || strTestFolderRef.equalsIgnoreCase("null")) {
                System.out.print("Unable to find test folder...");
            } else {

                for (int i = 0; i < 2; i++) {
                    System.out.println("***************Adding Test************");
                    try {
                        //Add a Test Case Result    
                        System.out.println("Creating Test Case...");

                        JsonObject newTestObj = new JsonObject();
                        String strName = "Verify some serious Test " + i + " one more";
                        //newTestCaseResult.addProperty("Date", getCurrentDate("yyyy-M-d") + "T" + getCurrentDate("HH:mm:ss") + ".000Z");
                        newTestObj.addProperty("Workspace", "https://rally1.rallydev.com/slm/webservice/v2.0/workspace/23724738313");
                        newTestObj.addProperty("Project", "https://rally1.rallydev.com/slm/webservice/v2.0/project/54060543237");
                        newTestObj.addProperty("WorkProduct", "https://rally1.rallydev.com/slm/webservice/v2.0/hierarchicalrequirement/93718171368");
                        newTestObj.addProperty("TestFolder", strTestFolderRef);
                        newTestObj.addProperty("Owner", userRef);//Reference of user
                        newTestObj.addProperty("Priority", "Important");
                        newTestObj.addProperty("PreConditions", "NA");
                        newTestObj.addProperty("Method", "Manual");
                        newTestObj.addProperty("Name", strName);
                        newTestObj.addProperty("Description", "My Description");
                        //newTestObj.addProperty("Tester", userRef);
                        // newTestCaseResult.addProperty("TestCase", "testRef");
                        //newTestCaseResult.addProperty("Workspace", workspaceRef);
                        
                        //CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
                        CreateRequest createRequest = new CreateRequest("testcase", newTestObj);
                        CreateResponse createResponse = restApi.create(createRequest);
                        if (createResponse.wasSuccessful()) {
                            String strTestID = createResponse.getObject().get("FormattedID").getAsString();
                            String strTestReference = createResponse.getObject().get("_ref").getAsString();
                            System.out.println(String.format("PASSED : Created %s %s", strTestID, createResponse.getObject().get("_ref").getAsString()));
                            rc = true;
                           
                            boolean stepInserted = false;
                            for (int j = 0; j < 5; j++) {
                                stepInserted = udsInsertTestStep(
                                        strWorkspaceName, 
                                        strProjectName, 
                                        strTestID, 
                                        strTestReference,
                                        (j+1)+"", //Step Insex
                                        "Input "+j, 
                                        "Expected.."+j);
                                if (stepInserted == false)
                                {
                                    System.out.println("Error in inserting a step..");
                                    return ;
                                }
                            }
                        } else {
                            System.out.println("FAILED : Unable to upload testcase result for : " + strName);
                            return ;
                        }
                    } catch (Exception e) {
                        System.out.println("Error in uploading...");
                        e.printStackTrace();
                        return;
                    }
                }
            }

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
    
    public static boolean udsInsertTestCase ( 
            String strTestName,
            String strDescription,
            String strWorkspaceRef,
            String strProjectNameRef,
            String strUserStoryWorkProdRef,
            String strTestFolderRef,
            String strUserRef,
            String strPriority,
            String strMethod
    ){
     
                    System.out.println("***************Adding Test************");
                    try {
                        //Add a Test Case Result    
                        System.out.println("Creating Test Case...");

                        JsonObject newTestObj = new JsonObject();
                        //String strName = "Verify some serious Test " + i + " one more";
                        //newTestCaseResult.addProperty("Date", getCurrentDate("yyyy-M-d") + "T" + getCurrentDate("HH:mm:ss") + ".000Z");
                        newTestObj.addProperty("Workspace", strWorkspaceRef);//"https://rally1.rallydev.com/slm/webservice/v2.0/workspace/23724738313");
                        newTestObj.addProperty("Project", strProjectNameRef);//"https://rally1.rallydev.com/slm/webservice/v2.0/project/54060543237");
                        newTestObj.addProperty("WorkProduct", strUserStoryWorkProdRef);//"https://rally1.rallydev.com/slm/webservice/v2.0/hierarchicalrequirement/93718171368");
                        newTestObj.addProperty("TestFolder", strTestFolderRef);
                        newTestObj.addProperty("Owner", strUserRef);//Reference of user
                        newTestObj.addProperty("Priority", strPriority);//"Important");
                        newTestObj.addProperty("PreConditions", "NA");
                        newTestObj.addProperty("Method", strMethod);//"Manual");
                        newTestObj.addProperty("Name", strTestName);
                        newTestObj.addProperty("Description", strDescription);//"My Test Description");
                        //newTestObj.addProperty("Tester", userRef);
                        // newTestCaseResult.addProperty("TestCase", "testRef");
                        //newTestCaseResult.addProperty("Workspace", workspaceRef);
                        
                        //CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
                        CreateRequest createRequest = new CreateRequest("testcase", newTestObj);
                        CreateResponse createResponse = restApi.create(createRequest);
                        if (createResponse.wasSuccessful()) {
                            String strTestID = createResponse.getObject().get("FormattedID").getAsString();
                            String strTestReference = createResponse.getObject().get("_ref").getAsString();
                            System.out.println(String.format("PASSED : Created %s %s", strTestID, createResponse.getObject().get("_ref").getAsString()));
                            
                        } else {
                            System.out.println("FAILED : Unable to upload testcase result for : " + strTestName);
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("Error in uploading...");
                        e.printStackTrace();
                        return false;
                    }
                
        return true;
    }
    
    public static boolean udsInsertTestStep (
            String strWorkspaceName,
            String strProjectName,
            String strTestCaseID, 
            String strTestCaseRef,
            String strStepIndex,
            String strInput, 
            String strExpected
    )
    {
        try {
            boolean rc = false;
            //String strWorkspaceName = "Cisco Information Technology";
            //String strProjectName = "PSA-DPM";
            //String strTestCaseID = "TC277017";
            GetObject obj = new GetObject();
/*
            //Get workspace reference
            String strWorkspaceRef = obj.udsGetObjectProperty(restApi, "Workspace", "Name", strWorkspaceName, "_ref");
            //TimeUnit.SECONDS.sleep(3);
            if (strWorkspaceRef == null || strWorkspaceRef.contains("http") == false) {
                System.out.println("Unable to fetch Workspace with Name : " + strWorkspaceName);
                return false;
            } else {
                System.out.println("Found Workspace with Name : " + strWorkspaceName);
            }

            //Get project ref
            String strProjectRef = obj.udsGetObjectProperty(restApi, "Project", "Name", strProjectName, "_ref");
            //TimeUnit.SECONDS.sleep(3);
            if (strProjectRef == null || strProjectRef.contains("http") == false) {
                System.out.println("Unable to fetch project with Name : " + strProjectName);
                return false;
            } else {
                System.out.println("Found project with Name : " + strProjectName);
            }
*/
            //Fetching Test folder
            /*String strTestCaseRef = obj.udsGetObjectProperty(restApi, "TestCase", "FormattedID", strTestCaseID, "_ref");
            //TimeUnit.SECONDS.sleep(3);
            if (strTestCaseRef == null || strTestCaseRef.contains("http") == false) {
                System.out.println("Unable to fetch Test Case with # : " + strTestCaseID);
                return false;
            } else {
                System.out.println("Found Test Case with # : " + strTestCaseID);
            }*/

            if (strTestCaseRef == null || strTestCaseRef.contains("http") == false) {
                System.out.print("Unable to find test case...");
            } else {
                    try {
                        //Add a Test Case Result    

                        JsonObject newTestObj = new JsonObject();
                        //newTestCaseResult.addProperty("Date", getCurrentDate("yyyy-M-d") + "T" + getCurrentDate("HH:mm:ss") + ".000Z");
                        newTestObj.addProperty("Workspace", "https://rally1.rallydev.com/slm/webservice/v2.0/workspace/23724738313");
                        newTestObj.addProperty("WorkProduct", "https://rally1.rallydev.com/slm/webservice/v2.0/hierarchicalrequirement/93718171368");
                        newTestObj.addProperty("TestCase", strTestCaseRef);
                        newTestObj.addProperty("StepIndex", strStepIndex);
                        newTestObj.addProperty("Input", strInput);
                        newTestObj.addProperty("ExpectedResult", strExpected);//Reference of user
                        //newTestObj.addProperty("Tester", userRef);
                        // newTestCaseResult.addProperty("TestCase", "testRef");
                        //newTestCaseResult.addProperty("Workspace", workspaceRef);

                        //CreateRequest createRequest = new CreateRequest("testcaseresult", newTestCaseResult);
                        CreateRequest createRequest = new CreateRequest("TestCaseStep", newTestObj);
                        CreateResponse createResponse = restApi.create(createRequest);
                        if (createResponse.wasSuccessful()) {
                            System.out.println(String.format("PASSED : %s || %s || %s", createResponse.getObject().get("StepIndex").getAsString(), createResponse.getObject().get("Input").getAsString(), createResponse.getObject().get("ExpectedResult").getAsString()));
                            rc = true;
                        } else {
                            System.out.println("FAILED : Unable to upload testcase step for : " + strTestCaseID);
                            return false;
                        }
                    } catch (Exception e) {
                        System.out.println("Error in uploading...");
                        e.printStackTrace();
                        return false;
                    }
                
            }
            return rc;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
