package agent;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.atg.openssp.common.exception.RequestException;
import com.atg.openssp.core.cache.type.SiteDataCache;
import com.atg.openssp.core.exchange.RequestSessionAgent;

import junit.framework.Assert;
import openrtb.bidrequest.model.Site;

/**
 * 
 * @author André Schmer
 *
 */
public class SessionAgentParamsTest {

	@BeforeClass
	public static void setUp() {

		final Site site = new Site();
		site.setId("1");
		SiteDataCache.instance.put("1", site);
		SiteDataCache.instance.switchCache();
	}

	/**
	 * ?zone={zone_id}&pub={publisher_id}&prot={protocol}&h={height}&w={width}&sd={startdelay}&mime={mime_type}&domain={domain}&page={page}&ad={adid}
	 */

	@Test
	public void testParamsComplete() {
		final MockHttpServletRequest request = new MockHttpServletRequest();
		final MockHttpServletResponse response = new MockHttpServletResponse();

		request.addParameter("site", "1");

		RequestSessionAgent agent = null;
		try {
			agent = new RequestSessionAgent(request, response);
		} catch (final RequestException e) {
			Assert.fail(e.getMessage());
		}

		Assert.assertEquals("1", agent.getParamValues().getSite().getId());
	}

	// @Test
	// public void testParamsAlternative() {
	// final MockHttpServletRequest request = new MockHttpServletRequest();
	// final MockHttpServletResponse response = new MockHttpServletResponse();
	//
	// request.addParameter("w", "1024");
	// request.addParameter("h", "768");
	// request.addParameter("prot", "3");
	// request.addParameter("domain", "atg.com");
	//
	// RequestSessionAgent agent = null;
	// try {
	// agent = new RequestSessionAgent(request, response);
	// } catch (final RequestException e) {
	// Assert.fail(e.getMessage());
	// }
	//
	// // Assert.assertEquals(zoneid, agent.getParamValues().getZone().getZoneId());
	// // Assert.assertEquals(websiteid, agent.getParamValues().getZone().getWebsiteId());
	// // Assert.assertEquals(zoneid, agent.getParamValues().getWebsite().getZones()[0].getZoneId());
	// // Assert.assertEquals(adid, agent.getParamValues().getVideoad().getVideoadId());
	// Assert.assertEquals(0, agent.getParamValues().getStartdelay());
	// Assert.assertEquals("atg.com", agent.getParamValues().getDomain());
	// Assert.assertEquals("768", agent.getParamValues().getH());
	// Assert.assertEquals("1024", agent.getParamValues().getW());
	//
	// // Assert.assertEquals(String.valueOf(websiteid), agent.getParamValues().getPublisherid());
	// Assert.assertEquals("video/mp4", agent.getParamValues().getMimes().get(0));
	// Assert.assertEquals(new Integer(3), agent.getParamValues().getProtocols().get(0));
	// Assert.assertEquals("atg.com", agent.getParamValues().getPage());
	// }

}
