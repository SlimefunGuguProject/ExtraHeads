package io.github.thebusybiscuit.extraheads;

import javax.annotation.Nonnull;

import net.guizhanss.guizhanlibplugin.updater.GuizhanUpdater;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.extraheads.listeners.HeadListener;
import io.github.thebusybiscuit.extraheads.setup.ItemSetup;
import io.github.thebusybiscuit.extraheads.setup.Registry;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;

import net.guizhanss.guizhanlibplugin.updater.GuizhanUpdater;

import lombok.Getter;

public class ExtraHeads extends JavaPlugin implements SlimefunAddon {

    @Getter
    private static ExtraHeads instance;

    private Registry registry;

    public static Registry getRegistry() {
        return getInstance().registry;
    }

    @Override
    public void onEnable() {
        instance = this;

        if (!getServer().getPluginManager().isPluginEnabled("GuizhanLibPlugin")) {
            getLogger().log(Level.SEVERE, "本插件需要 鬼斩前置库插件(GuizhanLibPlugin) 才能运行!");
            getLogger().log(Level.SEVERE, "从此处下载: https://50L.cc/gzlib");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // registry and config
        registry = new Registry(new Config(this));

        // Setting up bStats
        new Metrics(this, 5650);

        if (registry.getConfig().getBoolean("options.auto-update") && getPluginVersion().startsWith("Build")) {
            GuizhanUpdater.start(this, getFile(), "SlimefunGuguProject", "ExtraHeads", "master");
        }

        ItemSetup.setup();

        new HeadListener(this);
    }

    @Override
    @Nonnull
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    @Nonnull
    public String getBugTrackerURL() {
        return "https://github.com/SlimefunGuguProject/ExtraHeads/issues";
    }
}
