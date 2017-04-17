/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rallyTestExecutorPkg;

/**
 *
 * @author cpandit
 */
class ApplicationGlobals {

    public void setAPIKey (String strAPIKey)
    {
        this.strAPIKey = strAPIKey;
    }
    public String getAPIKey ()
    {
        return this.strAPIKey;
    }
    String[] buttons = { "Retry", "Cancel" };
    public int iButtonRetry = 0;
    public int iButtonCancel = 1;
/*
    int rc = JOptionPane.showOptionDialog(null, "Question ?", "Confirmation",
        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[2]);

    System.out.println(rc);*/
    public String host = "https://rally1.rallydev.com";
    public String strAPIKey = 
            //"_H1YAhp4RzCxWr2hZbOvOjXNU3CkHVk5JfMDo7KPM";
            "_oQn2Zs9IQMavLxZtz3F0j75piRIWk0IZrKDCDr1nCHE"; // Cisco Rally workspace
    // "_zHt27zWNTwedqD1OgiKH9q0LnypiTkAIgZnquizeg40"; //Personal Workspace
//"_oQn2Zs9IQMavLxZtz3F0j75piRIWk0IZrKDCDr1nCHE";//"_H1YAhp4RzCxWr2hZbOvOjXNU3CkHVk5JfMDo7KPM";
    //Use https://help.rallydev.com/rally-application-manager guide for generating API Keys
    //String strUser = "cpandit@cisco.com";
    //String username = "vkuruvan@cisco.com";
    //String password = "Nonveg!123";
    public String wsapiVersion = "v2.0";
    //String projectRef = "/project/2222";      
    //String workspaceRef = "/workspace/11111"; 
    public String applicationName = "_UpdateTCR";//This API Caller Application

}
