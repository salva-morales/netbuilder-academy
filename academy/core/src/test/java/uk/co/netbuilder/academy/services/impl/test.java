package uk.co.ee.web.servicesimpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.io.InputStream;
import java.util.Dictionary;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.osgi.service.component.ComponentContext;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BitlyServiceImpl.class, DefaultHttpClient.class, HttpGet.class, IOUtils.class })
public class BitlyServiceImplTest {

    /**
     * Mocked Component Context
     */
    @Mock
    private ComponentContext componentContext;

    /**
     * Mocked Dictionary
     */
    @Mock
    private Dictionary<String, Object> dictionary;

    /**
     * Mocked Sling Repository
     */
    @Mock
    private SlingRepository repository;

    /**
     * Class to test
     */
    private BitlyServiceImpl bitlyService;

    @Before
    public void setUp() {
        bitlyService = new BitlyServiceImpl();

        when(componentContext.getProperties()).thenReturn(dictionary);
        when(dictionary.get("bitly.enabled")).thenReturn(true);
        when(dictionary.get("bitly.username")).thenReturn("EE");
        when(dictionary.get("bitly.apiKey")).thenReturn("Testing");

        bitlyService.activate(componentContext);

        Whitebox.setInternalState(bitlyService, "repository", repository);
    }

    @Test
    public void testShortenURL() throws Exception {
        DefaultHttpClient httpClient = PowerMockito.mock(DefaultHttpClient.class);
        HttpParams httpParams = Mockito.mock(HttpParams.class);
        HttpGet httpGet = PowerMockito.mock(HttpGet.class);
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        HttpEntity httpEntity = Mockito.mock(HttpEntity.class);
        InputStream inputStream = Mockito.mock(InputStream.class);
        PowerMockito.mockStatic(IOUtils.class);

        whenNew(DefaultHttpClient.class).withAnyArguments().thenReturn(httpClient);
        when(httpClient.getParams()).thenReturn(httpParams);
        whenNew(HttpGet.class).withAnyArguments().thenReturn(httpGet);
        when(httpClient.execute(httpGet)).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(inputStream);

        JSONObject response = new JSONObject();
        response.put("status_txt", "OK");
        response.put("status_code", 200);

        JSONObject data = new JSONObject();
        data.put("global_hash", "900913");
        data.put("hash", "ze6poY");
        data.put("long_url", "http://www.ee.co.uk/");
        data.put("new_hash", 0);
        data.put("url", "http://ee.co.uk/");
        response.put("data", data);

        when(statusLine.getStatusCode()).thenReturn(200);
        when(IOUtils.toString(inputStream)).thenReturn(response.toString());

        String expected = "http://ee.co.uk/";
        String actual = bitlyService.shorten("http://www.ee.co.uk/");

        assertEquals(expected, actual);
    }

    @Test
    public void testShortenURLInvalid() throws Exception {
        DefaultHttpClient httpClient = PowerMockito.mock(DefaultHttpClient.class);
        HttpParams httpParams = Mockito.mock(HttpParams.class);
        HttpGet httpGet = PowerMockito.mock(HttpGet.class);
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        HttpEntity httpEntity = Mockito.mock(HttpEntity.class);
        InputStream inputStream = Mockito.mock(InputStream.class);
        PowerMockito.mockStatic(IOUtils.class);

        whenNew(DefaultHttpClient.class).withAnyArguments().thenReturn(httpClient);
        when(httpClient.getParams()).thenReturn(httpParams);
        whenNew(HttpGet.class).withAnyArguments().thenReturn(httpGet);
        when(httpClient.execute(httpGet)).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(inputStream);

        JSONObject response = new JSONObject();
        response.put("status_txt", "BAD");
        response.put("status_code", 500);

        when(statusLine.getStatusCode()).thenReturn(200);
        when(IOUtils.toString(inputStream)).thenReturn(response.toString());

        String expected = "http://www.ee.co.uk/";
        String actual = bitlyService.shorten("http://www.ee.co.uk/");

        assertEquals(expected, actual);
    }

    @Test
    public void testShortenURLServerUnavailable() throws Exception {
        DefaultHttpClient httpClient = PowerMockito.mock(DefaultHttpClient.class);
        HttpParams httpParams = Mockito.mock(HttpParams.class);
        HttpGet httpGet = PowerMockito.mock(HttpGet.class);
        HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        HttpEntity httpEntity = Mockito.mock(HttpEntity.class);
        InputStream inputStream = Mockito.mock(InputStream.class);
        PowerMockito.mockStatic(IOUtils.class);

        whenNew(DefaultHttpClient.class).withAnyArguments().thenReturn(httpClient);
        when(httpClient.getParams()).thenReturn(httpParams);
        whenNew(HttpGet.class).withAnyArguments().thenReturn(httpGet);
        when(httpClient.execute(httpGet)).thenReturn(httpResponse);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(inputStream);

        when(statusLine.getStatusCode()).thenReturn(500);

        String expected = "http://www.ee.co.uk/";
        String actual = bitlyService.shorten("http://www.ee.co.uk/");

        assertEquals(expected, actual);
    }

    @Test
    public void testStore() throws Exception {
        Session session = Mockito.mock(Session.class);
        Node node = Mockito.mock(Node.class);

        when(repository.loginAdministrative(null)).thenReturn(session);
        when(session.getNode("/content/ee-web/testing/jcr:content")).thenReturn(node);

        bitlyService.store("/content/ee-web/testing", "http://ee.co.uk");

        verify(repository, times(1)).loginAdministrative(null);
        verify(node, times(1)).setProperty("bitly", "http://ee.co.uk");
        verify(session, times(1)).save();
        verify(session, times(1)).logout();

    }

    @Test
    public void testStoreNonExistantNode() throws Exception {
        Session session = Mockito.mock(Session.class);
        Node node = Mockito.mock(Node.class);

        when(repository.loginAdministrative(null)).thenReturn(session);
        when(session.getNode("/content/ee-web/testing")).thenReturn(null);

        bitlyService.store("/content/ee-web/testing", "http://ee.co.uk");

        verify(repository, times(1)).loginAdministrative(null);
        verify(node, never()).setProperty("bitly", "http://ee.co.uk");
        verify(session, never()).save();
        verify(session, times(1)).logout();

    }
}
