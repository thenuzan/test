/**
 *  Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.integration.test.delicious;

import org.apache.axiom.om.OMElement;
import org.apache.commons.codec.binary.Base64;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.api.clients.proxy.admin.ProxyServiceAdminClient;
import org.wso2.carbon.connector.integration.test.common.ConnectorIntegrationUtil;
import org.wso2.carbon.mediation.library.stub.MediationLibraryAdminServiceStub;
import org.wso2.carbon.mediation.library.stub.upload.MediationLibraryUploaderStub;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;

import java.util.Properties;

public class DeliciousConnectoreIntegrationTest extends ConnectorIntegrationTestBase {

    protected static final String CONNECTOR_NAME = "delicious";

    protected MediationLibraryUploaderStub mediationLibUploadStub = null;

    protected MediationLibraryAdminServiceStub adminServiceStub = null;

    protected ProxyServiceAdminClient proxyAdmin;

    protected String repoLocation = null;

    protected String deliciousConnectorFileName = CONNECTOR_NAME + ".zip";

    protected Properties deliciousConnectorProperties = null;

    protected String pathToProxiesDirectory = null;

    //  protected String pathToRequestsDirectory = null;

    // Variables for store results of dependent methods
    protected String addCommentMethodCommentId;
    protected String addTagMethodTagId;
    private String username;
    private String password;
    private String Apiurl;
    private String validAuthorization;
    private String invalidAuthorization;


    @BeforeTest(alwaysRun = true)
    protected void init() throws Exception {
        super.init(CONNECTOR_NAME);

        String concatString = connectorProperties.getProperty("username")+":"+connectorProperties.getProperty("password");
        validAuthorization =new String(Base64.encodeBase64(concatString.getBytes()));
        String invalidConcatString = connectorProperties.getProperty("invalidusername")+":"+connectorProperties.getProperty("invalidpassword");
        invalidAuthorization =new String(Base64.encodeBase64(invalidConcatString.getBytes()));
    }


    @Override
    protected void cleanup() {
        axis2Client.destroy();
    }

    private String addCredentials(String jsonString){
        return String.format(
                jsonString,
                connectorProperties.getProperty("username"),
                connectorProperties.getProperty("password"),
                connectorProperties.getProperty("Apiurl"));
    }

    /**
     * Positive test case for postGetAll method with mandatory parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {postGetAll} integration test with mandatory parameters")
    public void testDeliciouspostGetAllWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "postGetAll.txt";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("postGetAll"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/all", "",validAuthorization);

            System.out.println("postGetAll"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

        }


    /**
     * Positive test case for addNewPost method with mandatory parameters.
     */
    @Test(priority = 1, groups = { "wso2.esb" }, description = "delicious {addNewPost} integration test with mandatory parameters")
    public void testDeliciousaddNewPostWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "addNewPost.txt";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("addNewPost connector"+omElementC.toString());

            String parameters="&url=www.example_direct.com&description=test_url2";
            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/add?"+parameters, "",validAuthorization);

            System.out.println("addNewPost direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));



    }


    /**
     * Positive test case for postAllHashes method with mandatory parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {postAllHashes} integration test with mandatory parameters")
    public void testDeliciouspostAllHashesWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "postAllHashes.txt";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("postAllHashes connector"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/all?hashes", "",validAuthorization);

            System.out.println("postAllHashes direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }




    /**
     * Positive test case for getDates method with mandatory parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {getDates} integration test with mandatory parameters")
    public void testDeliciousgetDatesWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "getDates.txt";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("getDates connector"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/dates", "",validAuthorization);

            System.out.println("getDates direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }



    /**
     * Positive test case for deletePost method with mandatory parameters.
     */
    @Test(priority = 3, groups = { "wso2.esb" }, description = "delicious {deletePost} integration test with mandatory parameters")
    public void testDeliciousdeletePostWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "deletePost.txt";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("deletePost connector"+omElementC.toString());

            String parameters="&url=www.example_direct.com";
            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/delete?"+parameters, "",validAuthorization);

            System.out.println("deletePost direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }

    /**
     * Positive test case for getPosts method with mandatory parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {getPosts} integration test with mandatory parameters")
    public void testDeliciousgetPostsWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "getPosts.txt";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("getPosts connector"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/get", "",validAuthorization);

            System.out.println("getPosts direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


    /**
     * Positive test case for getRecentPosts method with mandatory parameters.
     */

    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {getRecentPosts} integration test with mandatory parameters")
    public void testDeliciousgetRecentPostsWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "getRecentPosts.txt";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("getRecentPosts connector"+omElementC.toString());


            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/recent", "",validAuthorization);

            System.out.println("getRecentPosts direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


    /**
     * Positive test case for suggestPopularTags method with mandatory parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {suggestPopularTags} integration test with mandatory parameters")
    public void testDelicioussuggestPopularTagsWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "suggestPopularTags.txt";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("suggestPopularTags connector"+omElementC.toString());
            String parameters="&url=www.yahoo.com";
            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/suggest?"+parameters, "",validAuthorization);

            System.out.println("suggestPopularTags direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }



    /**
     * Positive test case for lastUpdatedTime method with mandatory parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {lastUpdatedTime} integration test with mandatory parameters")
    public void testDeliciouslastUpdatedTimeWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "lastUpdatedTime.txt";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("lastUpdatedTime connector"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/update","",validAuthorization);

            System.out.println("lastUpdatedTime direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }




    /**
     * Positive test case for getTags method with mandatory parameters.
     */

    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {getTags} integration test with mandatory parameters")
    public void testDeliciousgetTagsWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "getTags.txt";
       // String methodName = "getTags";
        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);


        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC = responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("getTags connector"+omElementC.toString());

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD = responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl") + "/v1/tags/get", "", validAuthorization);


        System.out.println("getTags direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }

    /**
     * Positive test case for allTagsBundles method with mandatory parameters.
     */

    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {allTagsBundles} integration test with mandatory parameters")
    public void testDeliciousallTagsBundlesWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "allTagsBundles.txt";
        String methodName = "allTagsBundles";

        final String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        final String jsonString = addCredentials(rawString);


            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("allTagsBundles connector"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/tags/bundles/all?", "",validAuthorization);

            System.out.println("allTagsBundles direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


    /**
     * Positive test case for setTagsBundles method with mandatory parameters.
     */


    @Test(priority = 1, groups = { "wso2.esb" }, description = "delicious {setTagsBundles} integration test with mandatory parameters")
    public void testDelicioussetTagsBundlesWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "setTagsBundles.txt";
        String methodName = "setTagsBundles";


        String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        rawString = rawString.replace("dummyBundle",connectorProperties.getProperty("bundle"));
        rawString = rawString.replace("dummyTags",connectorProperties.getProperty("tags"));
        final String jsonString = addCredentials(rawString);


            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC = responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("setTagsBundles connector"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD = responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl") + "/v1/tags/bundles/set?bundle=" +connectorProperties.getProperty("bundle")+"&tags="+connectorProperties.getProperty("tags"), "", validAuthorization);

            System.out.println("setTagsBundles Direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }

    /**
     * Positive test case for deleteTagsBundles method with mandatory parameters.
     */

     @Test(priority = 3, groups = { "wso2.esb" }, description = "delicious {deleteTagsBundles} integration test with mandatory parameters")

   // @Test(dependsOnMethods ={"testDelicioussetTagsBundlesWithMandatoryParameters"}, priority = 3, groups = { "wso2.esb" }, description = "delicious {deleteTagsBundles} integration test with mandatory parameters")
    public void testDeliciousdeleteTagsBundlesWithMandatoryParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "deleteTagsBundles.txt";
        String methodName = "deleteTagsBundles";

        String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        rawString = rawString.replace("dummyBundle",connectorProperties.getProperty("bundle"));
        final String jsonString = addCredentials(rawString);


        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC = responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("deleteTagsBundles connector :"+omElementC.toString());

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD = responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl") +"/v1/tags/bundles/delete?bundle=" +connectorProperties.getProperty("bundle"), "", validAuthorization);

        System.out.println("deleteTagsBundles Direct :"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));
      //  Assert.assertTrue(!omElementD.toString().contains(addTagMethodTagId));
    }



///******************************************************NegetiveTests*************************************************/


    /**
     * Negetive test case for postGetAll method with Negetive parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {postGetAll} integration test with Negetive parameters")
    public void testDeliciouspostGetAllWithNegetiveParameters() throws Exception {

        final String jsonString =
         "{"+'"'+"json_payload"+'"'+": {"+
                '"'+"username"+'"'+":"+'"'+"username"+'"'+","
                +'"'+"password"+'"'+":"+'"'+"password"+'"'+","
                +'"'+"Apiurl"+'"'+":"+'"'+"https://api.del.icio.us"+'"'+","
                +'"'+"method"+'"'+":"+'"'+"postGetAll"+'"'+" } }";

        System.out.println(jsonString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("postGetAll"+omElementC.toString());

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/all", "",invalidAuthorization);

        System.out.println("postGetAll"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


    /**
     * Negetive test case for addNewPost method with Negetive parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {addNewPost} integration test with Negetive parameters")
    public void testDeliciousaddNewPostWithNegetiveParameters() throws Exception {


        final String jsonString =
                "{"+'"'+"json_payload"+'"'+": {"+
                        '"'+"username"+'"'+":"+'"'+connectorProperties.getProperty("username")+'"'+","
                        +'"'+"password"+'"'+":"+'"'+connectorProperties.getProperty("password")+'"'+","
                        +'"'+"Apiurl"+'"'+":"+'"'+"https://api.del.icio.us"+'"'+","
                        +'"'+"method"+'"'+":"+'"'+"addNewPost"+'"'+","
                        +'"'+"inputUrl"+'"'+":"+'"'+""+'"'+","
                        +'"'+"inputDescription"+'"'+":"+'"'+""+'"'+"} }";

        System.out.println(jsonString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("addNewPost connector"+omElementC.toString());

        String parameters="&url=&description=";
        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/add?"+parameters, "",validAuthorization);

        System.out.println("addNewPost direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));



    }


    /**
     * Negetive test case for postAllHashes method with Negetive parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {postAllHashes} integration test with Negetive parameters")
    public void testDeliciouspostAllHashesWithNegetiveParameters() throws Exception {

        final String jsonString =
                "{"+'"'+"json_payload"+'"'+": {"+
                        '"'+"username"+'"'+":"+'"'+"username"+'"'+","
                        +'"'+"password"+'"'+":"+'"'+"password"+'"'+","
                        +'"'+"Apiurl"+'"'+":"+'"'+"https://api.del.icio.us"+'"'+","
                        +'"'+"method"+'"'+":"+'"'+"postAllHashes"+'"'+" } }";

        System.out.println(jsonString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("postAllHashes connector"+omElementC.toString());

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/all?hashes", "",invalidAuthorization);

        System.out.println("postAllHashes direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


    /**
     * Negetive test case for getDates method with Negetive parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {getDates} integration test with Negetive parameters")
    public void testDeliciousgetDatesWithNegetiveParameters() throws Exception {

        final String jsonString =
                "{"+'"'+"json_payload"+'"'+": {"+
                        '"'+"username"+'"'+":"+'"'+"username"+'"'+","
                        +'"'+"password"+'"'+":"+'"'+"password"+'"'+","
                        +'"'+"Apiurl"+'"'+":"+'"'+"https://api.del.icio.us"+'"'+","
                        +'"'+"method"+'"'+":"+'"'+"getDates"+'"'+" } }";
        System.out.println(jsonString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("getDates connector"+omElementC.toString());

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/dates", "",invalidAuthorization);

        System.out.println("getDates direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }

    /**
     * Negetive test case for deletePost method with Negetive parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {deletePost} integration test with Negetive parameters")
    public void testDeliciousdeletePostWithNegetiveParameters() throws Exception {

        final String jsonString =
                "{"+'"'+"json_payload"+'"'+": {"+
                        '"'+"username"+'"'+":"+'"'+connectorProperties.getProperty("username")+'"'+","
                        +'"'+"password"+'"'+":"+'"'+connectorProperties.getProperty("password")+'"'+","
                        +'"'+"Apiurl"+'"'+":"+'"'+"https://api.del.icio.us"+'"'+","
                        +'"'+"method"+'"'+":"+'"'+"deletePost"+'"'+","
                        +'"'+"delUrl"+'"'+":"+'"'+"www.dummyurl.com"+'"'+"} }";

        System.out.println(jsonString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("deletePost connector"+omElementC.toString());

        String parameters="&url=www.dummyurl.com";
        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/delete?"+parameters, "",validAuthorization);

        System.out.println("deletePost direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }

    /**
     * Negetive test case for getPosts method with Negetive parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {getPosts} integration test with Negetive parameters")
    public void testDeliciousgetPostsWithNegetiveParameters() throws Exception {

        final String jsonString =
                "{"+'"'+"json_payload"+'"'+": {"+
                        '"'+"username"+'"'+":"+'"'+"username"+'"'+","
                        +'"'+"password"+'"'+":"+'"'+"password"+'"'+","
                        +'"'+"Apiurl"+'"'+":"+'"'+"https://api.del.icio.us"+'"'+","
                        +'"'+"method"+'"'+":"+'"'+"getPosts"+'"'+" } }";
        System.out.println(jsonString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("getPosts connector"+omElementC.toString());

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/get", "",invalidAuthorization);

        System.out.println("getPosts direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


    /**
     * Negetive test case for getRecentPosts method with Negetive parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {getRecentPosts} integration test with Negetive parameters")
    public void testDeliciousgetRecentPostsWithNegetiveParameters() throws Exception {

        final String jsonString =
                "{"+'"'+"json_payload"+'"'+": {"+
                        '"'+"username"+'"'+":"+'"'+"username"+'"'+","
                        +'"'+"password"+'"'+":"+'"'+"password"+'"'+","
                        +'"'+"Apiurl"+'"'+":"+'"'+"https://api.del.icio.us"+'"'+","
                        +'"'+"method"+'"'+":"+'"'+"getRecentPosts"+'"'+" } }";

        System.out.println(jsonString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("getRecentPosts connector"+omElementC.toString());


        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/recent", "",invalidAuthorization);

        System.out.println("getRecentPosts direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


    /**
     * Negetive test case for suggestPopularTags method with Negetive parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {suggestPopularTags} integration test with Negetive parameters")
    public void testDelicioussuggestPopularTagsWithNegetiveParameters() throws Exception {

        final String jsonString =
                "{"+'"'+"json_payload"+'"'+": {"+
                        '"'+"username"+'"'+":"+'"'+connectorProperties.getProperty("username")+'"'+","
                        +'"'+"password"+'"'+":"+'"'+connectorProperties.getProperty("password")+'"'+","
                        +'"'+"Apiurl"+'"'+":"+'"'+"https://api.del.icio.us"+'"'+","
                        +'"'+"method"+'"'+":"+'"'+"suggestPopularTags"+'"'+","
                        +'"'+"url"+'"'+":"+'"'+""+'"'+"} }";
        System.out.println(jsonString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("suggestPopularTags connector"+omElementC.toString());
        String parameters="";
        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/suggest?"+parameters, "",validAuthorization);

        System.out.println("suggestPopularTags direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }



    /**
     * Negetive test case for lastUpdatedTime method with Negetive parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {lastUpdatedTime} integration test with Negetive parameters")
    public void testDeliciouslastUpdatedTimeWithNegetiveParameters() throws Exception {

        final String jsonString =
                "{"+'"'+"json_payload"+'"'+": {"+
                        '"'+"username"+'"'+":"+'"'+"username"+'"'+","
                        +'"'+"password"+'"'+":"+'"'+"password"+'"'+","
                        +'"'+"Apiurl"+'"'+":"+'"'+"https://api.del.icio.us"+'"'+","
                        +'"'+"method"+'"'+":"+'"'+"lastUpdatedTime"+'"'+" } }";
        System.out.println(jsonString);

        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("lastUpdatedTime connector"+omElementC.toString());

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/posts/update","",invalidAuthorization);

        System.out.println("lastUpdatedTime direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


    /**
     * Negative test case for getTags method with negative parameters.
     */
    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {getTags} integration test with negative parameters")
    public void testDeliciousgetTagsWithNegativeParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "negetive/getTags.txt";

         String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);

         rawString = rawString.replace("dummyUsername",connectorProperties.getProperty("invalidusername"));
         rawString = rawString.replace("dummyPassword",connectorProperties.getProperty("invalidpassword"));
         rawString = rawString.replace("dummyApi",connectorProperties.getProperty("Apiurl"));
         final String jsonString = addCredentials(rawString);
          System.out.println(jsonString);


        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC = responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("negativeGetTags connector"+omElementC.toString());

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD = responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl") + "/v1/tags/get", "", invalidAuthorization);


        System.out.println("negativeGetTags direct"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }

       /**
       * Negative test case for allTagsBundles method with negative parameters.
       */

    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {allTagsBundles} integration test with negative parameters")
    public void testDeliciousallTagsBundlesWithNegativeParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "negetive/allTagsBundles.txt";

        String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);

        rawString = rawString.replace("dummyUsername",connectorProperties.getProperty("invalidusername"));
        rawString = rawString.replace("dummyPassword",connectorProperties.getProperty("invalidpassword"));
        rawString = rawString.replace("dummyApi",connectorProperties.getProperty("Apiurl"));
        final String jsonString = addCredentials(rawString);
        System.out.println(jsonString);


            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("allTagsBundles connector"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/tags/bundles/all?", "",invalidAuthorization);

            System.out.println("allTagsBundles direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


     /**
     * Negative test case for setTagsBundles method with negative parameters.
     */


    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {setTagsBundles} integration test with negative parameters")
    public void testDelicioussetTagsBundlesWithNegativeParameters() throws Exception {

         String jsonRequestFilePath = pathToResourcesDirectory + "negetive/setTagsBundles.txt";

     String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);

    rawString = rawString.replace("dummyUsername",connectorProperties.getProperty("invalidusername"));
    rawString = rawString.replace("dummyPassword",connectorProperties.getProperty("invalidpassword"));
    rawString = rawString.replace("dummyApi",connectorProperties.getProperty("Apiurl"));
    rawString = rawString.replace("dummyBundle",connectorProperties.getProperty("bundle"));
        rawString = rawString.replace("dummyTags",connectorProperties.getProperty("tags"));
    final String jsonString = addCredentials(rawString);
    System.out.println(jsonString);


            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC = responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("setTagsBundles connector"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD = responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl") + "/v1/tags/bundles/set?bundle=" +connectorProperties.getProperty("bundle")+"&tags="+connectorProperties.getProperty("tags"), "", invalidAuthorization);

            System.out.println("setTagsBundles Direct"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }


    /**
     * Negative test case for deleteTagsBundles method with negative parameters.
     */

    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {deleteTagsBundles} integration test with negative parameters")

    public void testDeliciousdeleteTagsBundlesWithNegativeParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "negetive/deleteTagsBundles.txt";

        String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);

        rawString = rawString.replace("dummyBundle",connectorProperties.getProperty("invalidBundle"));

        final String jsonString = addCredentials(rawString);
        System.out.println(jsonString);


        ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
        OMElement omElementC = responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

        System.out.println("deleteTagsBundles connector :"+omElementC.toString());

        ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
        OMElement omElementD = responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl") +"/v1/tags/bundles/delete?bundle=" +connectorProperties.getProperty("invalidBundle"), "", validAuthorization);

        System.out.println("deleteTagsBundles Direct :"+omElementD.toString());

        Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }

    /***************************************************OPTIONAL PARAMETERS***********************************************/

    /**
     * Positive test case for allTagsBundles method with optional parameters.
     */

    @Test(priority = 2, groups = { "wso2.esb" }, description = "delicious {allTagsBundles} integration test with optional parameters")
    public void testDeliciousallTagsBundlesWithOptionalParameters() throws Exception {

        String jsonRequestFilePath = pathToResourcesDirectory + "optional/allTagsBundles.txt";

        String rawString = ConnectorIntegrationUtil.getFileContent(jsonRequestFilePath);
        rawString = rawString.replace("dummyBundle",connectorProperties.getProperty("bundle"));
        final String jsonString = addCredentials(rawString);

        System.out.println(jsonString);

            ConnectorIntegrationUtil responseConnector = new ConnectorIntegrationUtil();
            OMElement omElementC= responseConnector.getXmlResponse("POST", getProxyServiceURL("delicious"), jsonString);

            System.out.println("Optional allTagsBundles connector:"+omElementC.toString());

            ConnectorIntegrationUtil responseDirect = new ConnectorIntegrationUtil();
            OMElement omElementD=responseDirect.sendXMLRequestWithBasic(connectorProperties.getProperty("Apiurl")+"/v1/tags/bundles/all?bundle="+connectorProperties.getProperty("bundle"), "",validAuthorization);

            System.out.println("Optional allTagsBundles Direct :"+omElementD.toString());

            Assert.assertTrue(omElementC.toString().equals(omElementD.toString()));

    }

}
