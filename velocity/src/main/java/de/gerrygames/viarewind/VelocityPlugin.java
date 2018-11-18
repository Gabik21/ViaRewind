package de.gerrygames.viarewind;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import de.gerrygames.viarewind.velocity.VersionInfo;
import us.myles.ViaVersion.sponge.util.LoggerWrapper;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(id = "viarewind",
		name = "ViaRewind",
		version = VersionInfo.VERSION,
		authors = {"Gerrygames"},
		dependencies = {
			@Dependency(id = "viaversion"),
			@Dependency(id = "viabackwards", optional = true)
		}
)
public class VelocityPlugin implements ViaRewindPlatform {

	private Logger logger;
	@Inject
	private org.slf4j.Logger loggerSlf4j;

	@Inject
	@DataDirectory
	private Path configDir;

	@Subscribe(order = PostOrder.LATE)
	public void onProxyStart(ProxyInitializeEvent e) {
		// Setup Logger
		this.logger = new LoggerWrapper(loggerSlf4j);
		// Init!
		File configFile = configDir.resolve("config.yml").toFile();

		ViaRewindConfigImpl conf = new ViaRewindConfigImpl(
				configFile,
				getClass().getClassLoader().getResource("assets/viarewind/config.yml")
		);
		conf.reloadConfig();
		this.init(conf);
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public void disable() {

	}

}
