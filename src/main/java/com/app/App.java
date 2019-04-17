package com.hayes.app;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * Zachary Hayes
 * Mumo Systems
 */
 
public class App 
{
    private static final String BASEURL = ""; // ENTER JIRA BASE URL
    private static String issueUrl = BASEURL + "/rest/api/2/issue";
    private static final String USER = ""; // ENTER JIRA USERNAME
    private static final String PASSWORD = ""; // ENTER PASSWORD
    private static String auth = new String(Base64.encode(USER + ":" + PASSWORD));
    private static String projectKey = ""; // ENTER PROJECT KEY (example: "DEV")

    public static void main(String[] args )
    {
        System.out.println( "Running... " );

        getIssueList();

        System.out.print("Finished.");
    }

    @GET
    @Produces("application/json")
    public static void getIssueList()
    {
        final int MAX_ISSUE_ID = 17651;
        int id = 0;

        while(id < MAX_ISSUE_ID)
        {
            id++;
            ClientResponse response = buildClient(issueUrl + "/" + projectKey + "-" + id, auth).get(ClientResponse.class);

            if(checkResponseStatus(response))
            {
                System.out.println(createOutputString(id));
            }
        }
    }

    private static String createOutputString(int id)
    {
        String issueId = projectKey + "-" + id;
        return issueId + "," + issueId;
    }

    private static WebResource.Builder buildClient(String url, String auth)
    {
        Client client = Client.create();
        WebResource webResource = client.resource(url);

        return webResource.header("Authorization", "Basic " + auth)
                .type("application/json")
                .accept("application/json");
    }

    private static boolean checkResponseStatus(ClientResponse response)
    {
        int statusCode = response.getStatus();
        if (statusCode == 200) {
            return true;
        }
        else
        {
            return false;
        }
    }
}
