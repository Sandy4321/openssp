package com.atg.openssp.core.cache.broker.json;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.PropertyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atg.openssp.common.cache.broker.DataBrokerObserver;
import com.atg.openssp.core.cache.broker.dto.SiteDto;
import com.atg.openssp.core.cache.type.SiteDataCache;
import com.google.gson.Gson;

import util.properties.ProjectProperty;

/**
 * @author André Schmer
 *
 */
public class SiteDataBrokerJson extends DataBrokerObserver {

	private static final Logger log = LoggerFactory.getLogger(SiteDataBrokerJson.class);

	public SiteDataBrokerJson() {}

	@Override
	protected boolean doCaching() {
		final Gson gson = new Gson();
		try {
			final String path = ProjectProperty.readFile("site_db.json").getAbsolutePath();
			final String content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
			final SiteDto dto = gson.fromJson(content, SiteDto.class);
			if (dto != null) {
				log.info("sizeof site data=" + dto.getSites().size());
				dto.getSites().forEach(site -> {
					SiteDataCache.instance.put(site.getId(), site);
				});
				return true;
			}
			log.error("no Site data");
			return false;
		} catch (final PropertyException | IOException e) {
			log.error(getClass() + ", " + e.getMessage());
		}

		return true;
	}

	@Override
	protected void finalWork() {
		SiteDataCache.instance.switchCache();

	}

}
