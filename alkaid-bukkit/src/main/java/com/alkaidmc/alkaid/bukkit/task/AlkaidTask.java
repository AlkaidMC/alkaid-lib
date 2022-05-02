package com.alkaidmc.alkaid.bukkit.task;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
@SuppressWarnings("unused")
public class AlkaidTask {
    final JavaPlugin plugin;

    public SimpleTaskRegister simple() {
        return new SimpleTaskRegister(plugin);
    }
}
